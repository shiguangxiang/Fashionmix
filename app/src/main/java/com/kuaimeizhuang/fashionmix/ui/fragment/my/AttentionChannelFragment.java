package com.kuaimeizhuang.fashionmix.ui.fragment.my;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.FragmentAttentionChannelBinding;
import com.kuaimeizhuang.fashionmix.model.my.FragmentAttentionTagViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * <p>我的关注：频道TAB</p>
 * Created on 17/5/25.
 *
 * @author Shi GuangXiang.
 */

public class AttentionChannelFragment extends BaseFragment {
    private FragmentAttentionChannelBinding binding;
    private FragmentAttentionTagViewModel viewModel;

    @Override
    protected View initView() {
        binding = (FragmentAttentionChannelBinding) UiUtils.getDataBinding(mInflater, R.layout.fragment_attention_channel);
        binding.recyclerViewChannal.setLayoutManager(new LinearLayoutManager(mActivity));
        UiUtils.setMakeupHeader(binding.ptrLayout, mActivity);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new FragmentAttentionTagViewModel(getBaseBinding(), mPager, mActivity);
        viewModel.getAttentionChannelData(binding, true);
    }

    @Override
    protected void reloadData() {
        viewModel.getAttentionChannelData(binding, true);
    }

    @Override
    protected void initListener() {
        binding.ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, binding.recyclerViewChannal, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                viewModel.getAttentionChannelData(binding, true);
            }
        });

        binding.recyclerViewChannal.setLoadMoreListener(() ->
                viewModel.getAttentionChannelData(binding, false)
        );
    }
}
