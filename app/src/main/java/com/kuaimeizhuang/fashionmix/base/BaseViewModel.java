package com.kuaimeizhuang.fashionmix.base;

import android.app.Activity;
import android.databinding.BaseObservable;

import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.retrofit.rx.BeautyFunc1;
import com.kuaimeizhuang.fashionmix.retrofit.rx.BeautySubscriber;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>模型基类</p>
 * Created on 17/4/1.
 *
 * @author Shi GuangXiang.
 */

public abstract class BaseViewModel<T> extends BaseObservable {
    protected ActivityBaseBinding mBaseBinding;
    protected LoadingPager mPager;
    protected Activity mActivity;
    private OnDataSuccess mOnDataSuccess;

    public BaseViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        this.mBaseBinding = mBaseBinding;
        this.mPager = mPager;
        this.mActivity = mActivity;
        checkNetwork();
    }

    /**
     * 检查网络
     */
    public void checkNetwork() {

    }

    /**
     * Activity onDestroy时调用，用来取消Activity Rx订阅等等清理工作
     */
    public abstract void onActivityDestroy();

    public void onActivityBackPressed() {

    }

    /**
     * 取消订阅
     *
     * @param subscriptions 需要取消的Subscriptions
     */
    protected void unSubscribe(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    public Subscription addSubscription(Observable<HttpResult<Object>> observable, OnDataSuccess onDataSuccess) {
        setOnDataSuccess(onDataSuccess);
        Subscription subscription;
        subscription = observable.map(new BeautyFunc1<>(mBaseBinding, mPager))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BeautySubscriber<Object>(mBaseBinding, mPager) {
                    @Override
                    public void onDataSuccess(Object obj) {
                        if (onDataSuccess != null) {
                            mOnDataSuccess.onDataSuccess(obj);
                        }
                    }
                    @Override
                    public void onDataError(String message) {
                        super.onDataError(message);
                        if (onDataSuccess != null) {
                            mOnDataSuccess.onDataError(message);
                        }
                    }
                });
        return subscription;
    }

    public interface OnDataSuccess {
        void onDataSuccess(Object object);

        void onDataError(String errorMsg);
    }

    public void setOnDataSuccess(OnDataSuccess onDataSuccess) {
        this.mOnDataSuccess = onDataSuccess;
    }

}
