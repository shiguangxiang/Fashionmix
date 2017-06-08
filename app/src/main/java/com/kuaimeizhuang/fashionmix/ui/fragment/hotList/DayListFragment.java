package com.kuaimeizhuang.fashionmix.ui.fragment.hotList;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.databinding.FragmentDayListBinding;
import com.kuaimeizhuang.fashionmix.model.hotList.HotListViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

/**
 * <p>日榜</p>
 * Created on 17/5/23.
 *
 * @author Shi GuangXiang.
 */

public class DayListFragment extends BaseFragment {
    private FragmentDayListBinding binding;
    private HotListViewModel viewModel;

    @Override
    protected View initView() {
        binding = (FragmentDayListBinding) UiUtils.getDataBinding(mInflater, R.layout.fragment_day_list);
        binding.recyclerViewHot.setLayoutManager(new LinearLayoutManager(getActivity()));
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new HotListViewModel(getBaseBinding(), mPager, mActivity);
        viewModel.getDayListDataa(binding);
    }

    @Override
    protected void reloadData() {
    }
}
