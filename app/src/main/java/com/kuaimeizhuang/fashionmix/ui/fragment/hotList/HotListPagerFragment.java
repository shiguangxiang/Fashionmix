package com.kuaimeizhuang.fashionmix.ui.fragment.hotList;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.HomeViewPageAdapter;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.FragmentHotListPagerBinding;
import com.kuaimeizhuang.fashionmix.utils.SPUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>首页 热榜</p>
 * Created on 17/5/11.
 *
 * @author Shi GuangXiang.
 */

public class HotListPagerFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    private FragmentHotListPagerBinding binding;
    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected View initView() {
        binding = (FragmentHotListPagerBinding) UiUtils.getDataBinding(mInflater, R.layout.fragment_hot_list_pager);
        getBaseBinding().baseTitle.showHeader();
        getBaseBinding().baseTitle.setHeaderTitle("热榜");
        getBaseBinding().baseTitle.hideLeftIcon();
        initTabLayout();
        return binding.getRoot();
    }

    //初始化TabLayout
    private void initTabLayout() {
        TabLayout tabLayout = binding.tbHot;
        tabLayout.addTab(tabLayout.newTab().setText("日榜"));
        tabLayout.addTab(tabLayout.newTab().setText("周榜"));

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(this);

        DayListFragment dayListFragment = new DayListFragment();
        WeekListFragment weekListFragment = new WeekListFragment();

        fragments.add(dayListFragment);
        fragments.add(weekListFragment);

        HomeViewPageAdapter adapter = new HomeViewPageAdapter(getFragmentManager(),fragments.size());
        binding.viewpager.setAdapter(adapter);
        adapter.addData(fragments);

    }

    @Override
    protected void initData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
    }

    @Override
    protected void reloadData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        binding.viewpager.setCurrentItem(tab.getPosition(),false);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPause() {
        super.onPause();
        boolean isHide = SPUtils.getBoolean("isHide", true);
        if (isHide){
            getBaseBinding().baseTitle.hideHeader();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getBaseBinding().baseTitle.showHeader();
    }
}
