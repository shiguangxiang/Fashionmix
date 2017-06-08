package com.kuaimeizhuang.fashionmix.ui.activity.my;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.HomeViewPageAdapter;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityMyAttentionBinding;
import com.kuaimeizhuang.fashionmix.ui.fragment.hotList.DayListFragment;
import com.kuaimeizhuang.fashionmix.ui.fragment.hotList.WeekListFragment;
import com.kuaimeizhuang.fashionmix.ui.fragment.my.AttentionChannelFragment;
import com.kuaimeizhuang.fashionmix.ui.fragment.my.AttentionTagFragment;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/5/25.
 *
 * @author Shi GuangXiang.
 */

public class MyAttentionActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private ActivityMyAttentionBinding binding;
    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected View initView() {
        binding = (ActivityMyAttentionBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_my_attention);
        getBaseBinding().baseTitle.showHeader();
        getBaseBinding().baseTitle.setHeaderTitle("我的关注");
        initTabLayout();
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
    }

    @Override
    protected void reloadData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
    }

    //初始化TabLayout
    private void initTabLayout() {
        TabLayout tabLayout = binding.tbHot;
        tabLayout.addTab(tabLayout.newTab().setText("标签"));
        tabLayout.addTab(tabLayout.newTab().setText("频道"));

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(this);

        AttentionTagFragment attentionTagFragment = new AttentionTagFragment();
        AttentionChannelFragment attentionChannelFragment = new AttentionChannelFragment();

        fragments.add(attentionTagFragment);
        fragments.add(attentionChannelFragment);

        HomeViewPageAdapter adapter = new HomeViewPageAdapter(getSupportFragmentManager(), fragments.size());
        binding.viewpager.setAdapter(adapter);
        adapter.addData(fragments);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        binding.viewpager.setCurrentItem(tab.getPosition(), false);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
