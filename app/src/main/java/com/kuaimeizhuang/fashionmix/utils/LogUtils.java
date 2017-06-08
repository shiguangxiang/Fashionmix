package com.kuaimeizhuang.fashionmix.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kuaimeizhuang.fashionmix.BuildConfig;

/**
 * <p>日志工具，用所在类simpleName自动生成TAG</p>
 * Created on 17/4/1.
 *
 * @author Shi GuangXiang.
 */

public class LogUtils {
    private final static int NO_LOG = -1;
    private final static int LOG_V = Log.VERBOSE;
    private final static int LOG_D = Log.DEBUG;
    private final static int LOG_I = Log.INFO;
    private final static int LOG_W = Log.WARN;
    private final static int LOG_E = Log.ERROR;

    private static int logLevel = LOG_E;

    //对签名apk自动屏蔽日志
    static {
        logLevel = BuildConfig.DEBUG ? LOG_E : NO_LOG;
    }

    public static void v(@NonNull String msg) {
        if (msg == null) {
            return;
        }
        if (logLevel >= LOG_V) {
            Log.v(generateTAG(), msg);
        }
    }

    public static void d(@NonNull String msg) {
        if (msg == null) {
            return;
        }
        if (logLevel >= LOG_D) {
            Log.d(generateTAG(), msg);
        }
    }

    public static void i(@NonNull String msg) {
        if (msg == null) {
            return;
        }
        if (logLevel >= LOG_I) {
            Log.i(generateTAG(), msg);
        }
    }

    public static void w(@NonNull String msg) {
        if (msg == null) {
            return;
        }
        if (logLevel >= LOG_W) {
            Log.w(generateTAG(), msg);
        }
    }

    public static void e(@NonNull String msg) {
        if (msg == null) {
            return;
        }
        if (logLevel >= LOG_E) {
            Log.e(generateTAG(), msg);
        }
    }

    private static String generateTAG() {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[4];
        String fullClassName = stackTrace.getClassName();
        return fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    }
}