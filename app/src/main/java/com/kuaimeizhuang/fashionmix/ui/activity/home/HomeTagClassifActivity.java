package com.kuaimeizhuang.fashionmix.ui.activity.home;

import android.view.View;

import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.model.home.HomeTagClassifViewModel;

/**
 * <p>首页最新页面，标签分类列表页面</p>
 * Created on 17/5/24.
 *
 * @author Shi GuangXiang.
 */

public class HomeTagClassifActivity extends BaseActivity {
    private HomeTagClassifViewModel viewModel;
    @Override
    protected View initView() {
        return null;
    }

    @Override
    protected void initData() {
        viewModel = new HomeTagClassifViewModel(getBaseBinding(),mPager,this);
    }

    @Override
    protected void reloadData() {

    }
}
