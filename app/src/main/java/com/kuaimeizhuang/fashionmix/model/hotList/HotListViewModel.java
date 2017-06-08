package com.kuaimeizhuang.fashionmix.model.hotList;

import android.app.Activity;
import android.os.Bundle;

import com.kuaimeizhuang.fashionmix.adapter.HotListAdapter;
import com.kuaimeizhuang.fashionmix.api.HotListApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.HomeNewestBean;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.FragmentDayListBinding;
import com.kuaimeizhuang.fashionmix.databinding.FragmentWeekListBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.ui.activity.video.VideoPlayDetailsActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.SPUtils;

import java.util.List;

import rx.Observable;

/**
 * <p>热榜</p>
 * Created on 17/5/26.
 *
 * @author Shi GuangXiang.
 */

public class HotListViewModel extends BaseViewModel {
    private HotListApi api = (HotListApi) RetrofitFactory.create(HotListApi.class);
    private HotListAdapter adapter;
    private HotListAdapter hotWeekAdapter;
    public HotListViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
        adapter = new HotListAdapter(mActivity, true);
        hotWeekAdapter = new HotListAdapter(mActivity, true);
    }

    @Override
    public void onActivityDestroy() {

    }

    /**
     * 获取日榜数据
     *
     * @param binding binding
     */
    public void getDayListDataa(FragmentDayListBinding binding) {
        binding.recyclerViewHot.setAdapter(adapter);
        Observable<HttpResult<HomeNewestBean>> observable = api.getHotListData("day");
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                HomeNewestBean homeNewestBean = (HomeNewestBean) object;
                List<Post> posts = homeNewestBean.getPosts();
                LogUtils.d(posts.size() + "");
                adapter.addPostData(posts);
                adapter.setOnItemClickListener(post -> {
                    SPUtils.setBoolean("isHide",false);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post", post);
                    ActivitySwitchUtil.switchActivity(VideoPlayDetailsActivity.class, bundle);
                });
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    /**
     * 获取周榜数据
     *
     * @param binding binding
     */
    public void getWeekListDataa(FragmentWeekListBinding binding) {
        binding.recyclerWeek.setAdapter(hotWeekAdapter);
        Observable<HttpResult<HomeNewestBean>> observable = api.getHotListData("week");
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                HomeNewestBean homeNewestBean = (HomeNewestBean) object;
                List<Post> posts = homeNewestBean.getPosts();
                LogUtils.d(posts.size() + "");
                hotWeekAdapter.addPostData(posts);
                hotWeekAdapter.setOnItemClickListener(post -> {
                    SPUtils.setBoolean("isHide",false);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post", post);
                    ActivitySwitchUtil.switchActivity(VideoPlayDetailsActivity.class, bundle);
                });
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }
}
