package com.kuaimeizhuang.fashionmix.model.home;

import android.app.Activity;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.RecomenedTagAdapter;
import com.kuaimeizhuang.fashionmix.api.HomePagerApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.NewestTag;
import com.kuaimeizhuang.fashionmix.bean.RecomedTagBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.ActivityRecommendTagBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import rx.Observable;

/**
 * <p>推荐标签</p>
 * Created on 17/6/5.
 *
 * @author Shi GuangXiang.
 */

public class RecommendTagViewModel extends BaseViewModel {
    private HomePagerApi api = (HomePagerApi) RetrofitFactory.create(HomePagerApi.class);
    public RecomenedTagAdapter adapter;

    public RecommendTagViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
        int screenWidth = UiUtils.getScreenWidth(mActivity);
        adapter = new RecomenedTagAdapter(this,screenWidth);
    }

    @Override
    public void onActivityDestroy() {

    }

    /**
     * 获取标签列表
     *
     * @param binding binding
     */
    public void getTagListData(ActivityRecommendTagBinding binding) {
        Observable<HttpResult<RecomedTagBean>> observable = api.getTagListData();
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                RecomedTagBean recomedTagBean = (RecomedTagBean) object;
                adapter.addDatas(recomedTagBean.getTagLists());
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    /**
     * 首页最新添加关注
     *
     * @param newestTag 标签实体
     */
    public void addFollowTag(NewestTag newestTag,View view) {
        view.setClickable(false);
        Observable<HttpResult<Object>> observable = api.follwTags(newestTag.getId() + "");
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                view.setClickable(true);
                view.setBackgroundResource(R.drawable.follw_ok);
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    /**
     * 取消关注标签
     * @param newestTag 标签实体
     */
    public void delFollowTag(NewestTag newestTag, View view) {
        view.setClickable(false);
        Observable<HttpResult<Object>> observable = api.deleteFollowTag(newestTag.getId() + "");
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                view.setClickable(true);
                view.setBackgroundResource(R.drawable.follw_add);
            }

            @Override
            public void onDataError(String errorMsg) {
                view.setClickable(true);
            }
        });
    }
}
