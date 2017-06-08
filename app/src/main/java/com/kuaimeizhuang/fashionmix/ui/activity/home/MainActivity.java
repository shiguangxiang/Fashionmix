package com.kuaimeizhuang.fashionmix.ui.activity.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.HomeFragPageAdapter;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityMainBinding;
import com.kuaimeizhuang.fashionmix.databinding.NavigatorTabIconLayoutBinding;
import com.kuaimeizhuang.fashionmix.ui.activity.account.LoginActivity;
import com.kuaimeizhuang.fashionmix.ui.fragment.home.HomePagerFragment;
import com.kuaimeizhuang.fashionmix.ui.fragment.hotList.HotListPagerFragment;
import com.kuaimeizhuang.fashionmix.ui.fragment.my.MyPagerFragment;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private ActivityMainBinding binding;

    @Override
    protected View initView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);
        initTabLayout();
        return binding.getRoot();
    }

    public ActivityMainBinding getActivityMainBinding() {
        return binding;
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        TabLayout tabLayout = binding.tabLayout;

        //首页
        NavigatorTabIconLayoutBinding tabHomeBinding = (NavigatorTabIconLayoutBinding) getDataBinding();
        tabHomeBinding.icon.setBackgroundResource(R.drawable.selector_navigator_tab_home);
        TabLayout.Tab tabHome = tabLayout.newTab();
        tabHome.setCustomView(tabHomeBinding.getRoot());
        tabHome.select();

        //热榜
        NavigatorTabIconLayoutBinding tabHotListBinding = (NavigatorTabIconLayoutBinding) getDataBinding();
        tabHotListBinding.icon.setBackgroundResource(R.drawable.selector_navigator_tab_original);
        TabLayout.Tab tabHotList = tabLayout.newTab();
        tabHotListBinding.icon.setSelected(true);
        tabHotList.setCustomView(tabHotListBinding.getRoot());

        //热榜
        NavigatorTabIconLayoutBinding tabMyBinding = (NavigatorTabIconLayoutBinding) getDataBinding();
        tabMyBinding.icon.setBackgroundResource(R.drawable.selector_navigator_tab_my);
        TabLayout.Tab tabMy = tabLayout.newTab();
        tabMy.setCustomView(tabMyBinding.getRoot());

        //添加
        tabLayout.addTab(tabHome);
        tabLayout.addTab(tabHotList);
        tabLayout.addTab(tabMy);

        tabMyBinding.getRoot().setOnClickListener(view -> {
            if (UiUtils.isLogin()) {
                binding.viewPager.setCurrentItem(2, false);
                binding.tabLayout.getTabAt(2).select();
            } else {
                ActivitySwitchUtil.switchActivityForResult(LoginActivity.class, 100);
            }
        });

        tabLayout.addOnTabSelectedListener(this);

        HomePagerFragment homePager = new HomePagerFragment();
        HotListPagerFragment hotPager = new HotListPagerFragment();
        MyPagerFragment myPager = new MyPagerFragment();

        List<BaseFragment> baseFragments = new ArrayList<>();
        baseFragments.add(homePager);
        baseFragments.add(hotPager);
        baseFragments.add(myPager);

        HomeFragPageAdapter adapter = new HomeFragPageAdapter(getSupportFragmentManager());
        adapter.addData(baseFragments);
        binding.viewPager.setAdapter(adapter);


        binding.viewPager.setCurrentItem(0, false);
        binding.tabLayout.getTabAt(0).select();

    }

    /**
     * 获取DataBinding
     *
     * @return ViewDataBinding
     */
    private ViewDataBinding getDataBinding() {
        return DataBindingUtil.inflate(getLayoutInflater(), R.layout.navigator_tab_icon_layout, null, false);
    }


    @Override
    protected void initData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
    }

    @Override
    protected void reloadData() {
        initData();
    }

    @Override
    public void onNetWorkListener(boolean isNetWorkSuccess) {
        super.onNetWorkListener(isNetWorkSuccess);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        binding.viewPager.setCurrentItem(tab.getPosition(), false);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == 666) {
                binding.viewPager.setCurrentItem(0);
                binding.tabLayout.getTabAt(0).select();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
