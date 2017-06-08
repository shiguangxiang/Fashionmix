package com.kuaimeizhuang.fashionmix.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.databinding.ActivityLikeVideoBinding;
import com.kuaimeizhuang.fashionmix.model.my.MyLikeVidepViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * <p>我的喜欢的</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class MyLikeVidepActivity extends BaseActivity {
    private ActivityLikeVideoBinding binding;
    private MyLikeVidepViewModel viewModel;

    @Override
    protected View initView() {
        binding = (ActivityLikeVideoBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_like_video);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        UiUtils.setMakeupHeader(binding.ptrLayout, mActivity);
        getBaseBinding().baseTitle.showHeader();
        getBaseBinding().baseTitle.setHeaderTitle("我喜欢的");
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new MyLikeVidepViewModel(getBaseBinding(), mPager, mActivity);
        viewModel.initData(binding, "");
        binding.recyclerView.setAdapter(viewModel.adapter);

    }

    @Override
    protected void reloadData() {
        viewModel.initData(binding, "");
    }

    @Override
    protected void initListener() {
        binding.ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, binding.recyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                viewModel.initData(binding, "down");
            }
        });

        binding.recyclerView.setLoadMoreListener(() ->
                viewModel.initData(binding, "up")
        );
    }
}
