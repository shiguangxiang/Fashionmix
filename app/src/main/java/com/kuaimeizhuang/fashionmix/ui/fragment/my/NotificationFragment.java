package com.kuaimeizhuang.fashionmix.ui.fragment.my;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.FragmentNotificationBinding;
import com.kuaimeizhuang.fashionmix.model.my.MyMessageViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * <p>我的通知页面</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class NotificationFragment extends BaseFragment {
    private FragmentNotificationBinding binding;
    private MyMessageViewModel viewModel;

    @Override
    protected View initView() {
        binding = (FragmentNotificationBinding) UiUtils.getDataBinding(mInflater, R.layout.fragment_notification);
        binding.recyclerViewPost.setLayoutManager(new LinearLayoutManager(getActivity()));
        UiUtils.setMakeupHeader(binding.ptrFrameLayout, mActivity);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new MyMessageViewModel(getBaseBinding(), mPager, mActivity);
        binding.recyclerViewPost.setAdapter(viewModel.postAdapter);
        viewModel.getNotificationData(binding, true);
    }

    @Override
    protected void reloadData() {
        viewModel.getNotificationData(binding, true);
    }

    @Override
    protected void initListener() {
        binding.ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, binding.recyclerViewPost, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                viewModel.getNotificationData(binding, true);
            }
        });

        binding.recyclerViewPost.setLoadMoreListener(() ->
                viewModel.getNotificationData(binding, false)

        );
    }
}
