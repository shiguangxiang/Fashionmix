package com.kuaimeizhuang.fashionmix.model.my;

import android.app.Activity;

import com.kuaimeizhuang.fashionmix.api.MyPagerApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.config.PageManager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.ActivityFeedbackBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import rx.Observable;

/**
 * <p>意见反馈</p>
 * Created on 17/6/3.
 *
 * @author Shi GuangXiang.
 */

public class FeedBackViewModel extends BaseViewModel {
    private MyPagerApi api = (MyPagerApi) RetrofitFactory.create(MyPagerApi.class);
    public FeedBackViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
    }

    @Override
    public void onActivityDestroy() {

    }

    /**
     * 提交反馈
     */
    public void submitFeedback(String qq,String content) {
        Observable<HttpResult<Object>> observable = api.userGiveFeedback(UiUtils.getUserId(), qq, "", content);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                if (object != null){
                    UiUtils.showToastLong("提交反馈成功");
                    PageManager.getCurrentActivity().finish();
                }
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }
}
