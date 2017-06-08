package com.kuaimeizhuang.fashionmix.ui.activity.my;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.HomeViewPageAdapter;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityMyMessageBinding;
import com.kuaimeizhuang.fashionmix.ui.fragment.my.AttentionChannelFragment;
import com.kuaimeizhuang.fashionmix.ui.fragment.my.AttentionTagFragment;
import com.kuaimeizhuang.fashionmix.ui.fragment.my.CommentFragment;
import com.kuaimeizhuang.fashionmix.ui.fragment.my.NotificationFragment;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>我的消息页面</p>
 * Created on 17/6/1.
 *
 * @author Shi GuangXiang.
 */

public class MyMessageActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private ActivityMyMessageBinding binding;
    private List<BaseFragment> fragments = new ArrayList<>();
    @Override
    protected View initView() {
        binding = (ActivityMyMessageBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_my_message);
        getBaseBinding().baseTitle.showHeader();
        getBaseBinding().baseTitle.setHeaderTitle("我的消息");
        initTabLayout();
        return binding.getRoot();
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        TabLayout tabLayout = binding.tbMessage;
        tabLayout.addTab(tabLayout.newTab().setText("评论"));
        tabLayout.addTab(tabLayout.newTab().setText("通知"));

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addOnTabSelectedListener(this);

        CommentFragment commentFragment = new CommentFragment();
        NotificationFragment notificationFragment = new NotificationFragment();

        fragments.add(commentFragment);
        fragments.add(notificationFragment);

        HomeViewPageAdapter adapter = new HomeViewPageAdapter(getSupportFragmentManager(), fragments.size());
        binding.viewPager.setAdapter(adapter);
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
        binding.viewPager.setCurrentItem(tab.getPosition(),false);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
