package com.kuaimeizhuang.fashionmix.ui.fragment.home;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.HomeViewPageAdapter;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.FragmentHomePagerBinding;
import com.kuaimeizhuang.fashionmix.ui.activity.account.LoginActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>首页</p>
 * Created on 17/5/11.
 *
 * @author Shi GuangXiang.
 */

public class HomePagerFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    private FragmentHomePagerBinding binding;
    private List<BaseFragment> baseFragments = new ArrayList<>();

    @Override
    protected View initView() {
        binding = (FragmentHomePagerBinding) UiUtils.getDataBinding(mInflater, R.layout.fragment_home_pager);
        initTabLayout();
        return binding.getRoot();
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        TabLayout tabLayout = binding.homeTabLayout;
        tabLayout.addTab(tabLayout.newTab().setText("最新"));
        tabLayout.addTab(tabLayout.newTab().setText("关注"));

        HomeNewestFragment newestFragment = new HomeNewestFragment();
        HomeAttentionFragment attentionFragment = new HomeAttentionFragment();

        tabLayout.addOnTabSelectedListener(this);
        baseFragments.add(newestFragment);
        baseFragments.add(attentionFragment);

        HomeViewPageAdapter pageAdapter = new HomeViewPageAdapter(getFragmentManager(), baseFragments.size());
        pageAdapter.addData(baseFragments);
        binding.homeViewPager.setAdapter(pageAdapter);

        binding.homeViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.homeTabLayout));
        binding.homeTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.homeViewPager));
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
        binding.homeViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
