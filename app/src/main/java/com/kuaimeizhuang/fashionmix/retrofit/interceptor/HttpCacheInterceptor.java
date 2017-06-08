package com.kuaimeizhuang.fashionmix.retrofit.interceptor;

import com.kuaimeizhuang.fashionmix.base.App;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>缓存拦截器</p>
 * Created on 17/4/10.
 *
 * @author Shi GuangXiang.
 */

public class HttpCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtil.isNotAvailable(App.getContext())) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            LogUtils.i("HttpCacheInterceptor->no network");
        }
        Response originalResponse = chain.proceed(request);
        if (NetworkUtil.isAvailable(App.getContext())) {
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                    .removeHeader("Pragma").build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
