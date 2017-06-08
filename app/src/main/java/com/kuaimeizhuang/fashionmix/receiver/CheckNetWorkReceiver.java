package com.kuaimeizhuang.fashionmix.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * <p>检查网络广播</p>
 * Created on 17/4/12.
 *
 * @author Shi GuangXiang.
 */

public class CheckNetWorkReceiver extends BroadcastReceiver {
    private OnNetWorkListener onNetWorkListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        //得到是否没有网络连接成功
        boolean isNotConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        if (!isNotConnected || judgeNetIsConnected(context)) {
            if (onNetWorkListener != null){
                onNetWorkListener.onNetWorkListener(true);
            }
        } else {
            if (onNetWorkListener != null){
                onNetWorkListener.onNetWorkListener(false);
            }
        }

    }

    /**
     * 判断网络连接是否成功
     *
     * @param context 上下文对象
     * @return 网络连接是否成功
     */
    public static boolean judgeNetIsConnected(Context context) {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnected();
    }

    public interface OnNetWorkListener{
        void onNetWorkListener(boolean isNetWorkSuccess);
    }
    public void setOnNetWorkListener(OnNetWorkListener onNetWorkListener){
        this.onNetWorkListener = onNetWorkListener;
    }
}
