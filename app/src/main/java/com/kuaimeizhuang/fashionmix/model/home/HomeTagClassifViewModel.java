package com.kuaimeizhuang.fashionmix.model.home;

import android.app.Activity;

import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;

/**
 * <p>首页最新标签列表页ViewModel</p>
 * Created on 17/5/24.
 *
 * @author Shi GuangXiang.
 */

public class HomeTagClassifViewModel extends BaseViewModel {
    public HomeTagClassifViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
    }

    @Override
    public void onActivityDestroy() {

    }
}
