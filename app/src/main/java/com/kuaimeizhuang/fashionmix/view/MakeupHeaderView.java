package com.kuaimeizhuang.fashionmix.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kuaimeizhuang.fashionmix.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * <p>美妆自定义下拉头</p>
 * Created on 17/5/18.
 *
 * @author Shi GuangXiang.
 */

public class MakeupHeaderView extends FrameLayout implements PtrUIHandler {

    private LayoutInflater inflater;

    // 下拉刷新视图（头部视图）
    private ViewGroup mHeadView;

    // 下拉图标
    private ProgressBar mProgressBar;

    //默认下拉图标
    private ImageView mDefProgressBar;

    public MakeupHeaderView(Context context) {
        this(context, null);
    }

    public MakeupHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MakeupHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {

        inflater = LayoutInflater.from(context);
        /**
         * 头部
         */
        mHeadView = (ViewGroup) inflater.inflate(R.layout.makeup_header, this, true);
        mProgressBar = (ProgressBar) mHeadView.findViewById(R.id.pb_progress_bar);
        mDefProgressBar = (ImageView) mHeadView.findViewById(R.id.iv_progress_bar);
        mDefProgressBar.setVisibility(VISIBLE);

    }

    @Override
    public void onUIReset(PtrFrameLayout ptrFrameLayout) {
        mDefProgressBar.setVisibility(VISIBLE);
        mProgressBar.setVisibility(INVISIBLE);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {
        mDefProgressBar.setVisibility(VISIBLE);
        mProgressBar.setVisibility(INVISIBLE);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        mDefProgressBar.setVisibility(INVISIBLE);
        mProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout) {
        mProgressBar.clearAnimation();
        mDefProgressBar.setVisibility(VISIBLE);
        mProgressBar.setVisibility(INVISIBLE);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {

            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {

            }
        }
    }
}

