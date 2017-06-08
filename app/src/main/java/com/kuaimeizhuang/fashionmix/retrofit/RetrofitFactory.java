package com.kuaimeizhuang.fashionmix.retrofit;

import com.kuaimeizhuang.fashionmix.BuildConfig;
import com.kuaimeizhuang.fashionmix.base.App;
import com.kuaimeizhuang.fashionmix.retrofit.interceptor.HeaderInterceptor;
import com.kuaimeizhuang.fashionmix.retrofit.interceptor.HttpCacheInterceptor;
import com.kuaimeizhuang.fashionmix.retrofit.interceptor.LoggingInterceptor;
import com.kuaimeizhuang.fashionmix.retrofit.interceptor.SignInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>网络通信工厂类</p>
 * Created on 17/4/7.
 *
 * @author Shi GuangXiang.
 */

public class RetrofitFactory {
    private static final String POR_BASE_URL = "http://app.api.dev.kuaimeizhuang.com/";
    private static final String DVE_BASE_URL = "http://snd.api.kuaimeizhuang.com/";
    private static String baseUrl;

    static {
        baseUrl = BuildConfig.DEBUG ? POR_BASE_URL : POR_BASE_URL;
    }

    public static Object create(Class clazz) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        File cacheFile = new File(App.getContext().getCacheDir(), "KmzHttpCache");
        builder.connectTimeout(30000, TimeUnit.MILLISECONDS).writeTimeout(30000, TimeUnit
                .MILLISECONDS).readTimeout(30000, TimeUnit.MILLISECONDS).build();
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //请求头拦截器
        builder.addInterceptor(new HeaderInterceptor());
        //签名处理拦截器
        builder.addInterceptor(new SignInterceptor());
        //log日志拦截器
        builder.addInterceptor(new LoggingInterceptor());
        //网络缓存拦截器
        builder.addNetworkInterceptor(new HttpCacheInterceptor()).cache(cache).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(builder.build())
                .build();
        return retrofit.create(clazz);
    }
}
