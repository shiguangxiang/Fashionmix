package com.kuaimeizhuang.fashionmix.ui.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.databinding.ActivityRecommendTagBinding;
import com.kuaimeizhuang.fashionmix.model.home.RecommendTagViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

/**
 * <p>推荐标签页面</p>
 * Created on 17/6/5.
 *
 * @author Shi GuangXiang.
 */

public class RecommendTagActivity extends BaseActivity {
    private RecommendTagViewModel viewModel;
    private ActivityRecommendTagBinding binding;

    @Override
    protected View initView() {
        binding = (ActivityRecommendTagBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_recommend_tag);
        getBaseBinding().baseTitle.showHeader();
        getBaseBinding().baseTitle.setHeaderTitle("推荐标签");
        binding.recomedRecycler.setLayoutManager(new LinearLayoutManager(this));
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new RecommendTagViewModel(getBaseBinding(),mPager,mActivity);
        binding.recomedRecycler.setAdapter(viewModel.adapter);
        viewModel.getTagListData(binding);
    }

    @Override
    protected void reloadData() {
        viewModel.getTagListData(binding);
    }
}
