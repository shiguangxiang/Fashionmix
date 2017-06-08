package com.kuaimeizhuang.fashionmix.ui.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseStartActivity;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.ActivitySplashBinding;
import com.kuaimeizhuang.fashionmix.ui.activity.home.MainActivity;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;
import com.kuaimeizhuang.fashionmix.view.LastPageJumpListener;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>引导页面</p>
 * Created on 17/6/7.
 *
 * @author Shi GuangXiang.
 */

public class SplashActivity extends BaseStartActivity {
    private ActivitySplashBinding binding;
    private int[] iconRes = new int[]{
            R.drawable.splashp_pic_a,
            R.drawable.splashp_pic_b,
            R.drawable.splashp_pic_c,
            R.drawable.splashp_pic_d
    };
    private List<ImageView> mDatas = new ArrayList<>();

    @Override
    protected View initView() {
        binding = (ActivitySplashBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_splash);

        for (int i = 0; i < iconRes.length; i++) {
            ImageView view = new ImageView(this);
            view.setImageResource(iconRes[i]);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            mDatas.add(view);
        }
        binding.vpSplash.setAdapter(new SplashAdapter());
        LastPageJumpListener lastPageJumpListener = new LastPageJumpListener(3, () -> {
            LogUtils.d("进入主页");
            goHome();
        });
        binding.vpSplash.addOnPageChangeListener(lastPageJumpListener);

        binding.vpSplash.setVisibility(View.VISIBLE);

        return binding.getRoot();
    }

    @Override
    protected void initData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
    }

    @Override
    protected void reloadData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
    }

    /**
     * splash的adapter
     */
    private class SplashAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mDatas != null) {
                return mDatas.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mDatas.get(position);
            container.addView(view);
            if (position == mDatas.size() - 1) {
                view.setOnClickListener(v -> goHome());
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 去主页
     */
    private void goHome() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}
