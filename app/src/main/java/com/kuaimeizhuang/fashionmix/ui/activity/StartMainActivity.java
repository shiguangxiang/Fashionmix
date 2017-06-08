package com.kuaimeizhuang.fashionmix.ui.activity;

import android.content.Intent;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseStartActivity;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityStartBinding;
import com.kuaimeizhuang.fashionmix.ui.activity.home.MainActivity;
import com.kuaimeizhuang.fashionmix.utils.SPUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

/**
 * <p>启动页面</p>
 * Created on 17/6/7.
 *
 * @author Shi GuangXiang.
 */

public class StartMainActivity extends BaseStartActivity {
    private ActivityStartBinding binding;

    @Override
    protected View initView() {
        binding = (ActivityStartBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_start);
        return binding.getRoot();
    }


    @Override
    protected void initData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    toMainPager();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    /**
     * 到主页面
     */
    private void toMainPager() {
        boolean isFirst = SPUtils.getBoolean("isFirst", true);
        if (isFirst) {//第一次打开程序
            SPUtils.setBoolean("isFirst", false);
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }


    @Override
    protected void reloadData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);

    }
}
