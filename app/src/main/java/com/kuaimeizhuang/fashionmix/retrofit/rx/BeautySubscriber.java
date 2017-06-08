package com.kuaimeizhuang.fashionmix.retrofit.rx;

import com.google.gson.JsonSyntaxException;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * <p>注释类</p>
 * Created on 17/4/10.
 *
 * @author Shi GuangXiang.
 */

public abstract class BeautySubscriber<T> extends Subscriber<T> {
    private ActivityBaseBinding mBinding;
    private LoadingPager mPager;

    public BeautySubscriber(ActivityBaseBinding binding, LoadingPager pager) {
        this.mBinding = binding;
        this.mPager = pager;
    }

    @Override
    public void onCompleted() {
        //do nothing
    }

    @Override
    public void onNext(T t) {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
        onDataSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onDataError(e.getMessage());
        if (e instanceof SocketTimeoutException) {
            //SnackbarUtil.showSnackBar(mBinding, "请求超时");
            UiUtils.showToastLong("请求超时");
//            mPager.onDataLoading(LoadingPager.LoadedResult.ERROR);
        } else if (e instanceof JsonSyntaxException) {
            //SnackbarUtil.showSnackBar(mBinding, "数据解析失败，请稍后重试");
            UiUtils.showToastLong("数据解析失败，请稍后重试");
//            mPager.onDataLoading(LoadingPager.LoadedResult.DATANULL);
        } else if (e instanceof HttpException) {
            //SnackbarUtil.showSnackBar(mBinding, "服务器维护中，请稍后重试");
            UiUtils.showToastLong("服务器维护中，请稍后重试");
//            mPager.onDataLoading(LoadingPager.LoadedResult.SERVERERROR);
        }
        e.printStackTrace();
    }

    public abstract void onDataSuccess(T t);

    public void onDataError(String message) {
        LogUtils.e(message);
    }
}
