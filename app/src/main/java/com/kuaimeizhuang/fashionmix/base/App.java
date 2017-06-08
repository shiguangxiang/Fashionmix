package com.kuaimeizhuang.fashionmix.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import cn.sharesdk.framework.ShareSDK;

/**
 * <p>APP 全局初始化类</p>
 * Created on 17/5/11.
 *
 * @author Shi GuangXiang.
 */

public class App extends Application {
    private static Handler mHandler;
    private static long mMainThreadId;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        mMainThreadId = android.os.Process.myTid();
        sContext = this;
        ShareSDK.initSDK(getApplicationContext());
    }

    public static Context getContext() {
        return sContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }


    public static long getMainThreadId() {
        return mMainThreadId;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}

