package com.kuaimeizhuang.fashionmix.ui.fragment.hotList;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.FragmentWeekListBinding;
import com.kuaimeizhuang.fashionmix.model.hotList.HotListViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

/**
 * <p>周榜</p>
 * Created on 17/5/23.
 *
 * @author Shi GuangXiang.
 */

public class WeekListFragment extends BaseFragment {
    private FragmentWeekListBinding binding;
    private HotListViewModel viewModel;

    @Override
    protected View initView() {
        binding = (FragmentWeekListBinding) UiUtils.getDataBinding(mInflater, R.layout.fragment_week_list);
        binding.recyclerWeek.setLayoutManager(new LinearLayoutManager(getActivity()));
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new HotListViewModel(getBaseBinding(),mPager,mActivity);
        viewModel.getWeekListDataa(binding);
    }

    @Override
    protected void reloadData() {
        viewModel.getWeekListDataa(binding);
    }
}
