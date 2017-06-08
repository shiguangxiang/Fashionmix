package com.kuaimeizhuang.fashionmix.ui.activity.my;

import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityPushSettingBinding;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

/**
 * <p>
 *     推送设置
 * </p>
 * Created on 17/5/23.
 *
 * @author Shi GuangXiang.
 */

public class PushSettingActivity extends BaseActivity {
    private ActivityPushSettingBinding binding;
    @Override
    protected View initView() {
        binding = (ActivityPushSettingBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_push_setting);
        getBaseBinding().baseTitle.showHeader();
        getBaseBinding().baseTitle.setHeaderTitle("消息推送设置");
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
}
