package com.kuaimeizhuang.fashionmix.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kuaimeizhuang.fashionmix.base.App;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.view.MakeupHeaderView;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * <p>ui工具类</p>
 * Created on 17/4/1.
 *
 * @author Shi GuangXiang.
 */

public class UiUtils {

    private static long lastClickTime;

    private final static String SP_NAME = "Config";
    private static SharedPreferences mSp = App.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);


    private static SharedPreferences getSp() {
        return mSp;
    }

    public static void runInMainThread(Runnable runnable) {
        // 在主线程运行
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }



    public static boolean isNewVersioin() {
        return SPUtils.getBoolean(Constants.HAS_NEW_VERSION);
    }
    public static boolean isForceUpdate() {
        return SPUtils.getBoolean(Constants.NEED_FORCE_UPDATE);
    }

    // 判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    /**
     * 获取主线程id
     *
     * @return
     */
    public static long getMainThreadId() {
        return App.getMainThreadId();
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return App.getHandler();
    }

    /**
     * 显示长时间的toast
     */
    public static void showToastLong(String str) {
        Toast.makeText(App.getContext(), str, Toast.LENGTH_LONG).show();
    }

    /**
     * 获取用户token
     * @return token
     */
    public static String getToken() {
        return SPUtils.getString(Constants.KEY_IS_USER_TOKEN,"");
    }

    /**
     * 设置用户token
     * @param token token
     */
    public static void setToken(String token){
        SPUtils.setString(Constants.KEY_IS_USER_TOKEN,token);
    }


    /**
     * 获取DataBinding
     * @param inflater inflater
     * @param layoutId layoutId
     * @return ViewDataBinding
     */
    public static ViewDataBinding getDataBinding(LayoutInflater inflater,int layoutId){
        return DataBindingUtil.inflate(inflater,layoutId,null,false);
    }

    /**
     * 获取long类型的值,如果没有对应的值，默认值返回0
     * @param key     对应的键
     * @return
     */
    public static long getLong( String key) {
        return getLong(key, 0);
    }

    /**
     * 获取long类型的值
     * @param key      对应的键
     * @param defValue 如果没有对应的值，
     * @return
     */
    public static long getLong(String key, long defValue) {
        SharedPreferences sp = getSp();
        return sp.getLong(key, defValue);
    }

    /**
     * 设置Long类型的值
     * @param key
     * @param value
     */
    public static void setLong(String key, long value) {
        SharedPreferences sp = getSp();
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取DataBinding
     * @param inflater inflater
     * @param layoutId layoutId
     * @return ViewDataBinding
     */
    public static ViewDataBinding getDataBinding(LayoutInflater inflater, ViewGroup parent, int layoutId){
        return DataBindingUtil.inflate(inflater,layoutId,parent,false);
    }

    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }


    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }

    public static int dip2px(int dip) {
        DisplayMetrics metrics = App.getContext().getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dip * density + 0.5f);
    }

    public static int px2dip(int px) {
        DisplayMetrics metrics = App.getContext().getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (px / density + 0.5f);
    }

    public static void setMakeupHeader(PtrClassicFrameLayout ptrClassicFrameLayout, Activity activity){
        /* 创建自定义刷新头部view */
        MakeupHeaderView header = new MakeupHeaderView(activity);
        /* 设置刷新头部view */
        ptrClassicFrameLayout.setHeaderView(header);
        /* 设置回调 */
        ptrClassicFrameLayout.addPtrUIHandler(header);
        ptrClassicFrameLayout.setLoadingMinTime(2000);
    }

    /**
     * 保存用户ID
     *
     * @param usreId 用户ID
     */
    public static void setUserId(String usreId) {
        SPUtils.setString(Constants.KEY_IS_USER_ID, usreId);
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    public static String getUserId() {
        String userid = SPUtils.getString(Constants.KEY_IS_USER_ID);
        return userid;
    }

    /**
     * 是否有userId
     *
     * @return
     */
    public static boolean hasUserId() {
        if (getUserId() != null && !TextUtils.isEmpty(getUserId())) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户手机号码
     *
     * @return
     */
    public static String getUserPhone() {
        String phone = SPUtils.getString(Constants.KEY_IS_USER_PHONE);
        return phone;
    }

    /**
     * 设置用户手机号码
     *
     * @param phone
     */
    public static void setUserPhone(String phone) {
        SPUtils.setString(Constants.KEY_IS_USER_PHONE, phone);
    }

    /**
     * 设置用户性别
     *
     * @param gender
     */
    public static void setUserGender(int gender) {
        SPUtils.setInt(Constants.KEY_IS_USER_GENDER, gender);
    }

    /**
     * 获取用户性别
     */
    public static int getUserGender() {
        int gender = SPUtils.getInt(Constants.KEY_IS_USER_GENDER);
        return gender;
    }

    /**
     * 获取用户昵称
     *
     * @return
     */
    public static String getUserNickName() {
        String nickName = SPUtils.getString(Constants.KEY_IS_USER_NICKNAME);
        return nickName;
    }

    /**
     * 设置用户昵称
     *
     * @param nickName
     */
    public static void setUserNickName(String nickName) {
        SPUtils.setString(Constants.KEY_IS_USER_NICKNAME, nickName);
    }

    /**
     * 用户是否有绑定手机号码
     *
     * @return
     */
    public static boolean isBindPhone() {
        if (getUserPhone() != null && !TextUtils.isEmpty(getUserPhone())) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户的登陆状态
     *
     * @return 是否登录
     */
    public static boolean isLogin() {
        return SPUtils.getBoolean(Constants.KEY_IS_USER_LOGIN, false);
    }

    /**
     * 设置用户的登陆状态
     *
     * @param isLogin 是否登录
     */
    public static void setLogin(boolean isLogin) {
        SPUtils.setBoolean(Constants.KEY_IS_USER_LOGIN, isLogin);
    }

    /**
     * 判断是否是快速点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
