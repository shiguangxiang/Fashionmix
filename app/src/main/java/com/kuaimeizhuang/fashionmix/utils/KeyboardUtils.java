package com.kuaimeizhuang.fashionmix.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * <p>隐藏键盘工具类</p>
 * Created on 17/6/1.
 *
 * @author Shi GuangXiang.
 */

public class KeyboardUtils {
    public static void hide(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    /**
     * 显示和隐藏软键盘 View ： EditText、TextView isShow : true = show , false = hide
     *
     * @param context
     * @param view
     * @param isShow
     */
    public static void popSoftKeyboard(Context context, View view, boolean isShow) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            view.requestFocus();
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
