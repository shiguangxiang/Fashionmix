package com.kuaimeizhuang.fashionmix.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.App;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.config.PageManager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.ActivityMainBinding;
import com.kuaimeizhuang.fashionmix.ui.activity.home.MainActivity;

/**
 * <p>注释类</p>
 * Created on 17/4/10.
 *
 * @author Shi GuangXiang.
 */

public class SnackbarUtil {
    private static Snackbar snackbar;

    public static void showSnackBar(ActivityBaseBinding binding, String errMsg) {
        BaseActivity currentActivity = PageManager.getCurrentActivity();
        if (currentActivity instanceof Activity) {//MainActivity的SnackBar要放在TabLayout之上
            ActivityMainBinding activityMainBinding = ((MainActivity) currentActivity).getActivityMainBinding();
            snackbar = Snackbar.make(activityMainBinding.llMain, errMsg, Snackbar.LENGTH_LONG);
        } else {
            snackbar = Snackbar.make(binding.baseContainer, errMsg, Snackbar.LENGTH_LONG);
        }
        customSnackBar(snackbar, App.getContext().getResources().getColor(R.color.red_tao));
        snackbar.show();
    }

    public static void dismissSnackBar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    public static void customSnackBar(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
            TextView tvSnackMsg = ((TextView) view.findViewById(R.id.snackbar_text));
            //            tvSnackMsg.setTextColor(BeautyApplication.sContext.getResources().getColor(R.color.black));
            tvSnackMsg.setTextSize(13);
        }
    }
}
