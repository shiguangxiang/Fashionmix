package com.kuaimeizhuang.fashionmix.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kuaimeizhuang.fashionmix.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>首页ViewPager适配器</p>
 * Created on 17/5/11.
 *
 * @author Shi GuangXiang.
 */

public class HomeFragPageAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 3;
    private List<BaseFragment> mFragmentList = new ArrayList<>(PAGE_COUNT);

    public void addData(List<BaseFragment> fragmentList){
        if (fragmentList != null) {
            mFragmentList.addAll(fragmentList);
        }
        notifyDataSetChanged();
    }

    public HomeFragPageAdapter(FragmentManager fm) {
        super(fm);
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
