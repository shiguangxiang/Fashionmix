package com.kuaimeizhuang.fashionmix.ui.fragment.my;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.databinding.FragmentCommentBinding;
import com.kuaimeizhuang.fashionmix.model.my.MyMessageViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * <p>我的评论页面</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class CommentFragment extends BaseFragment {
    private FragmentCommentBinding binding;
    private MyMessageViewModel viewModel;

    @Override
    protected View initView() {
        binding = (FragmentCommentBinding) UiUtils.getDataBinding(mInflater, R.layout.fragment_comment);
        binding.recyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
        UiUtils.setMakeupHeader(binding.ptrFrameLayout, mActivity);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new MyMessageViewModel(getBaseBinding(), mPager, mActivity);
        binding.recyclerViewList.setAdapter(viewModel.adapter);

        viewModel.getCommentListData(binding, true);
    }

    @Override
    protected void reloadData() {
        viewModel.getCommentListData(binding, true);
    }

    @Override
    protected void initListener() {

        binding.ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, binding.recyclerViewList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                viewModel.getCommentListData(binding, true);
            }
        });

        binding.recyclerViewList.setLoadMoreListener(() ->
                viewModel.getCommentListData(binding, false)
        );
    }
}
