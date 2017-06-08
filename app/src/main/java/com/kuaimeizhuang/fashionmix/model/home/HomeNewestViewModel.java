package com.kuaimeizhuang.fashionmix.model.home;

import android.app.Activity;
import android.os.Bundle;

import com.kuaimeizhuang.fashionmix.adapter.NewHomeNewestAdapter;
import com.kuaimeizhuang.fashionmix.api.HomePagerApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.HomeNewestBean;
import com.kuaimeizhuang.fashionmix.bean.NewestTag;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.FragmentHomeNewestBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.ui.activity.account.LoginActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.video.VideoPlayDetailsActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * <p>首页最新标签页面</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class HomeNewestViewModel extends BaseViewModel {
    private HomePagerApi api = (HomePagerApi) RetrofitFactory.create(HomePagerApi.class);
    private Subscription homeSubscription;
    public NewHomeNewestAdapter adapter;
    private int index = 0;

    private int postId;
    private String createAt;

    public HomeNewestViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
        int screenWidth = UiUtils.getScreenWidth(mActivity);
        adapter = new NewHomeNewestAdapter(screenWidth, mActivity);
    }

    @Override
    public void onActivityDestroy() {
        unSubscribe(homeSubscription);
    }

    /**
     * 初始化页面数据
     *
     * @param binding binding
     */
    public void initData(FragmentHomeNewestBinding binding, String load) {
        List<Post> postList = adapter.getPostList();
        if (load.equals(Constants.DOWN)) {
            if (postList.size() > 0) {
                createAt = postList.get(0).getCreated_at();
                postId = postList.get(0).getId();
            }
        } else if (load.equals(Constants.UP)) {
            if (postList.size() > 0) {
                createAt = postList.get(postList.size() - 1).getCreated_at();
                postId = postList.get(postList.size() - 1).getId();
            }
        } else {
            createAt = "";
            postId = 0;
        }
        Observable<HttpResult<HomeNewestBean>> observable = api.getHomeNewestData(postId, load, createAt);
        homeSubscription = addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object obj) {
                HomeNewestBean homeNewestBean = (HomeNewestBean) obj;
                LogUtils.d("加载成功" + homeNewestBean.getPosts().size());

                boolean hasPage = homeNewestBean.isHas_page();
                if (hasPage) {

                }
                if (load.equals(Constants.DOWN)) { //下拉刷新
                    adapter.addPostData(homeNewestBean.getPosts(), 1, true);
                    adapter.replaceTagData(homeNewestBean.getTags());
                    binding.loadMoreRecyclerView.refreshComplete(binding.ptrLayout, hasPage);
                } else if (load.equals(Constants.UP)) { //上拉加载
                    adapter.addPostData(homeNewestBean.getPosts(), index++, false);
                    binding.loadMoreRecyclerView.notifyMoreFinish(hasPage);
                } else {
                    adapter.addPostData(homeNewestBean.getPosts(), index++, false);
                    adapter.replaceTagData(homeNewestBean.getTags());
                    binding.loadMoreRecyclerView.notifyMoreFinish(hasPage);
                }
                //点击首页标签关注
                adapter.setOnItemTagListener(newestTag -> {
                            if (!UiUtils.getToken().equals("")) {
                                addFollowTag(newestTag);
                            } else {
                                ActivitySwitchUtil.switchActivity(LoginActivity.class);
                            }
                        }
                );

                adapter.setOnItemPostClickListener(post -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post", post);
                    ActivitySwitchUtil.switchActivity(VideoPlayDetailsActivity.class, bundle);
                });
            }

            @Override
            public void onDataError(String errorMsg) {
                LogUtils.d("错误：" + errorMsg);
            }
        });
    }

    /**
     * 首页最新添加关注
     *
     * @param newestTag 标签实体
     */
    private void addFollowTag(NewestTag newestTag) {
        Observable<HttpResult<Object>> observable = api.follwTags(newestTag.getId() + "");
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                UiUtils.showToastLong("关注成功");
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }
}
