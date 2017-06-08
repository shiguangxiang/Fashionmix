package com.kuaimeizhuang.fashionmix.config;

import android.app.Activity;
import android.content.Context;

import com.kuaimeizhuang.fashionmix.base.BaseActivity;

import java.util.Stack;

/**
 * <p>注释类</p>
 * Created on 17/4/1.
 *
 * @author Shi GuangXiang.
 */

public class PageManager {

    private static Stack<BaseActivity> pageStack = new Stack<>();
    /**
     * 添加新页面
     *
     * @param activity 页面对象
     */
    public static void addPage(BaseActivity activity) {
        if (!pageStack.contains(activity)) {
            pageStack.add(activity);
        }
    }

    public static BaseActivity getCurrentActivity() {
        return pageStack.lastElement();
    }


    /**
     * 退出应用程序
     */
    public static void AppExit(Context context) {
        try {
            clearPage();
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } catch (Exception e) {
        }
    }

    /**
     * 页面清理
     */
    public static void clearPage() {
        int size = pageStack.size();
        for (int i = 0; i < size; i++) {
            pageStack.get(i).finish();
        }
        pageStack.clear();
    }
    /**
     * 移除页面对象
     *
     * @param activity 页面对象
     */
    public static void removePage(Activity activity) {
        if (activity != null) {
            pageStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

}
