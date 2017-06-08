package com.kuaimeizhuang.fashionmix.retrofit.interceptor;

import android.text.TextUtils;

import com.kuaimeizhuang.fashionmix.utils.AppSystemUtil;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>自定义请求头拦截器</p>
 * Created on 17/4/7.
 *
 * @author Shi GuangXiang.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //首先拿到请求对象
        Request request = chain.request();
        LogUtils.d("登录状态");
        String token = UiUtils.getToken();
        if (!TextUtils.equals(token, "")) {
            //用户token不为空就传token
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .addHeader("X-App-Vercode", AppSystemUtil.getVersionCode() + "")
                    .addHeader("X-Device-Id", AppSystemUtil.getDeviceId())
                    .addHeader("X-Channel", AppSystemUtil.getUmengChannel())
                    .addHeader("X-Platform", AppSystemUtil.getPLATFORM())
                    .addHeader("X-App-Key","FashionMix")
                    .build();

        }else {
            //用户token为空就不传token
            request = request.newBuilder()
                    .addHeader("X-Device-Id", AppSystemUtil.getDeviceId())
                    .addHeader("X-App-Vercode", AppSystemUtil.getVersionCode() + "")
                    .addHeader("X-Channel", AppSystemUtil.getUmengChannel())
                    .addHeader("X-Platform", AppSystemUtil.getPLATFORM())
                    .addHeader("X-App-Key","FashionMix")
                    .build();
        }
        return chain.proceed(request);
    }
}
