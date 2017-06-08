package com.kuaimeizhuang.fashionmix.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;


import com.kuaimeizhuang.fashionmix.base.App;
import com.kuaimeizhuang.fashionmix.config.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <p>获取应用程序有关的数据</p>
 * Created on 17/4/7.
 *
 * @author Shi GuangXiang.
 */

public class AppSystemUtil {
    private static final String OSVERSION;
    private static final String DEVICE_NAME;
    private static final String PLATFORM = "Android";
    private static final String DEVICE_ID;
    private static String versionName;
    private static String packageName;
    private static int versionCode;
    private static int AppUserID;
    private static String UserToken;

    public AppSystemUtil() {
    }

    public static void initAppSystemData(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;

        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packInfo.versionName;
            versionCode = packInfo.versionCode;
            packageName = packInfo.packageName;
        } catch (PackageManager.NameNotFoundException var5) {
            var5.printStackTrace();
            versionName = "-1.0.0";
            versionCode = -1;
        }

    }

    public static String getOSVERSION() {
        return OSVERSION;
    }

    public static String getDeviceName() {
        return DEVICE_NAME;
    }

    public static String getPLATFORM() {
        return PLATFORM;
    }

    public static String getDeviceId() {
        String signStr = DEVICE_ID + DEVICE_NAME + PLATFORM + OSVERSION;
        String sign = EncryptUtil.SHA1(EncryptUtil.MD5(signStr) + Constants.SALT);
        LogUtils.d("设备ID签名原文：" + signStr);
        LogUtils.d("设备ID签名密文：" + sign);
        return sign;
    }

    public static String getVersionName() {
        return versionName;
    }

    public static int getVersionCode() {
        return versionCode;
    }

    public static int getAppUserID() {
        return AppUserID;
    }

    public static void setVersionName(String versionName) {
        versionName = versionName;
    }

    public static void setVersionCode(int versionCode) {
        versionCode = versionCode;
    }

    public static void setAppUserID(int appUserID) {
        AppUserID = appUserID;
    }

    public static String getUserToken() {
        return UserToken;
    }

    public static void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public static String getPackageName() {
        return packageName;
    }


    public static String getUmengChannel() {
        return getMetaData("UMENG_CHANNEL");
    }

    public static String getMetaData(String metaDataKey) {
        try {
            return App.getContext().getPackageManager().getApplicationInfo(App.getContext().getPackageName(), PackageManager.GET_META_DATA).metaData.getString(metaDataKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFromAssets(String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(App.getContext().getResources()
                    .getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                Result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }

    static {
        OSVERSION = Build.VERSION.RELEASE;
        DEVICE_NAME = Build.BRAND + Build.MODEL;
        DEVICE_ID = Build.SERIAL;
    }

    /**
     *
     * @return 获取手机AndroidId
     */
    public static String getAndroidId(){
        String android_id = Settings.System.getString(App.getContext().getContentResolver(),
                Settings.System.ANDROID_ID);
        LogUtils.d("android_id" + android_id);
        return android_id;
    }

    /**
     *
     * @return 获取手机IMEI
     */
    public static String getPhoneIMEI(){
        TelephonyManager service = (TelephonyManager) App.getContext().getSystemService(Context
                .TELEPHONY_SERVICE);
        LogUtils.d("IMEI" + service.getDeviceId());
        return service.getDeviceId();
    }

}

