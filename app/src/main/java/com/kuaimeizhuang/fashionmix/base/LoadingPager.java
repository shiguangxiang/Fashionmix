package com.kuaimeizhuang.fashionmix.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.databinding.PagerLaodingBinding;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

/**
 * <p>页面加载状态</p>
 * Created on 17/4/1.
 *
 * @author Shi GuangXiang.
 */

public abstract class LoadingPager extends FrameLayout implements DataLoadListener{
    private LayoutInflater inflater;
    private PagerLaodingBinding laodingBinding;
    /**
     * 成功的view
     */
    private View mSuccessView;
    /**
     * 无状态
     */
    private static final int STATE_NONE = -1;

    /**
     * 加载中的状态
     */
    public static final int STATE_LOADING = 0;

    /**
     * 成功的状态
     */
    public static final int STATE_SUCCESS = 3;
    /**
     * 当前view的状态
     */
    private int mCurrentState = STATE_NONE;

    public LoadingPager(@NonNull Context context) {
        this(context,null);
    }

    public LoadingPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        /**
         * 加载中
         */
        if (laodingBinding == null) {
            laodingBinding = DataBindingUtil.inflate(inflater, R.layout.pager_laoding, null, false);
            laodingBinding.swip.setRefreshing(true);
            addView(laodingBinding.getRoot());
        }

        /**
         * 4. 成功
         */
        if (mSuccessView == null) {
            mSuccessView = onCreateSuccessView();
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView(mSuccessView, params);
        }
        safeUpdateUIStyle();
    }

    /**
     * 更新UI
     */
    private void safeUpdateUIStyle() {
        UiUtils.runInMainThread(()->
                updateUIStyle()
        );
    }

    protected abstract View onCreateSuccessView();
    protected abstract void onStartLoadData();
    protected void reloadData() {
    }
    private void updateUIStyle() {
        /**
         * 加载中
         */
        if (laodingBinding != null) {
            if ((mCurrentState == STATE_LOADING) || (mCurrentState == STATE_NONE)) {
                laodingBinding.swip.setRefreshing(true);
                laodingBinding.swip.setColorSchemeResources(R.color.red_tao);
                laodingBinding.getRoot().setVisibility(VISIBLE);
            } else {
                laodingBinding.swip.setRefreshing(false);
                laodingBinding.getRoot().setVisibility(GONE);
            }

            if (mCurrentState == STATE_LOADING) {
                bringChildToFront(laodingBinding.getRoot());
                return;
            }
        }

        /**
         * 4. success
         */
        if (mSuccessView != null) {
            laodingBinding.swip.setRefreshing(false);
            if (mCurrentState == STATE_SUCCESS) {
                mSuccessView.setVisibility(VISIBLE);
            } else {
                mSuccessView.setVisibility(GONE);
            }
        }
    }

    public enum LoadedResult {
        LOADING(STATE_LOADING), SUCCESS(STATE_SUCCESS);
        int state;

        LoadedResult(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

    /**
     * 加载数据的方法
     */
    public void loadData() {
        if (mCurrentState == STATE_NONE) {
            mCurrentState = STATE_LOADING;
            onStartLoadData();
        }
        safeUpdateUIStyle();
    }

    @Override
    public void onDataLoading(LoadedResult result) {
        mCurrentState = result.getState();
        safeUpdateUIStyle();
    }
}
