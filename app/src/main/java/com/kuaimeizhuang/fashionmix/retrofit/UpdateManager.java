package com.kuaimeizhuang.fashionmix.retrofit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.config.PageManager;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.SPUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>注释类</p>
 * Created on 17/6/5.
 *
 * @author Shi GuangXiang.
 */

public class UpdateManager {

    private static final OkHttpClient client = new OkHttpClient.Builder()
            //设置超时，不设置可能会报异常
            .connectTimeout(1000, TimeUnit.MINUTES)
            .readTimeout(1000, TimeUnit.MINUTES)
            .writeTimeout(1000, TimeUnit.MINUTES)
            .build();
    public static final String saveFileName = "Fashionmix.apk";
    // 文件存储
    private File updateFile = null;
    // 下载状态
    private final static int DOWNLOAD_COMPLETE = 0;
    private final static int DOWNLOAD_FAIL = 1;

    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_COMPLETE:
                    Uri uri = Uri.fromFile(updateFile);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    PageManager.getCurrentActivity().startActivity(intent);
                    break;
                case DOWNLOAD_FAIL:
                    UiUtils.showToastLong("下载失败，请重试!");
                    break;
                default:
                    break;
            }
        }
    };


    public void retrofitDownload() {
        String updateUrl = SPUtils.getString(Constants.UPDATE_ADDRESS);
        //监听下载进度
        final ProgressDialog dialog = new ProgressDialog(PageManager.getCurrentActivity());
        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        dialog.setTitle("下载");
        dialog.setMessage("正在下载，请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();


        //这个是ui线程回调，可直接操作UI
        final UIProgressListener uiProgressResponseListener = new UIProgressListener() {
            @Override
            public void onUIProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("TAG", "bytesRead:" + bytesRead);
                Log.e("TAG", "contentLength:" + contentLength);
                Log.e("TAG", "done:" + done);
                if (contentLength != -1) {
                    //长度未知的情况下回返回-1
                    Log.e("TAG", (100 * bytesRead) / contentLength + "% done");
                }
                Log.e("TAG", "================================");
                //ui层回调
                dialog.setMax((int) (contentLength / 1024));
                dialog.setProgress((int) (bytesRead / 1024));

                if (done) {
                    dialog.dismiss();
                }
            }
        };

        //构造请求
        final Request request1 = new Request.Builder()
                .url(updateUrl)
                .build();

        ProgressHelper.addProgressResponseListener(client, uiProgressResponseListener).newCall(request1).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(e.getMessage());
                UiUtils.showToastLong("下载失败，请重试!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = updateHandler.obtainMessage();
                try {
                    InputStream is = response.body().byteStream();
                    // 创建文件
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        updateFile = new File(Environment.getExternalStorageDirectory(), saveFileName);
                    }
                    FileOutputStream fos = new FileOutputStream(updateFile, false);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    is.close();
                    message.what = DOWNLOAD_COMPLETE;
                    updateHandler.sendMessage(message);
                } catch (IOException e) {
                    message.what = DOWNLOAD_FAIL;
                    // 下载失败
                    updateHandler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        });
    }
}
