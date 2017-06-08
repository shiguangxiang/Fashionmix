package com.kuaimeizhuang.fashionmix.ui.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.databinding.FragmentHomeNewestBinding;
import com.kuaimeizhuang.fashionmix.model.home.HomeNewestViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * <p>首页最新Tab页面</p>
 * Created on 17/5/12.
 *
 * @author Shi GuangXiang.
 */

public class HomeNewestFragment extends BaseFragment {
    private FragmentHomeNewestBinding binding;
    private HomeNewestViewModel viewModel;
    @Override
    protected View initView() {
        binding = (FragmentHomeNewestBinding) UiUtils.getDataBinding(mInflater, R.layout.fragment_home_newest);
        binding.loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.loadMoreRecyclerView.setHasFixedSize(true);
        UiUtils.setMakeupHeader(binding.ptrLayout,getActivity());
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new HomeNewestViewModel(getBaseBinding(), mPager, mActivity);
        binding.loadMoreRecyclerView.setAdapter(viewModel.adapter);
        viewModel.initData(binding, "");
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
                // return ViewCompat.canScrollVertically(mRecyclerView, -1);
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, binding.loadMoreRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                viewModel.initData(binding, Constants.DOWN);
            }
        });

        binding.loadMoreRecyclerView.setLoadMoreListener(() ->
                viewModel.initData(binding, Constants.UP)
        );
    }
}
