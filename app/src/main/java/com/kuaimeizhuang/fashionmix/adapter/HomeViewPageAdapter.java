package com.kuaimeizhuang.fashionmix.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kuaimeizhuang.fashionmix.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>首页最新和关注的ViewPager Adapter</p>
 * Created on 17/5/12.
 *
 * @author Shi GuangXiang.
 */

public class HomeViewPageAdapter extends FragmentPagerAdapter {
    private int PAGE_COUNT;
    private List<BaseFragment> mFragmentList = new ArrayList<>(PAGE_COUNT);

    public void addData(List<BaseFragment> fragmentList) {
        if (fragmentList != null) {
            mFragmentList.addAll(fragmentList);
        }
        notifyDataSetChanged();
    }

    public HomeViewPageAdapter(FragmentManager fm, int count) {
        super(fm);
        this.PAGE_COUNT = count;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
