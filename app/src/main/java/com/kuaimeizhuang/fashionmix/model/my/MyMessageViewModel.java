package com.kuaimeizhuang.fashionmix.model.my;

import android.app.Activity;
import android.os.Bundle;

import com.kuaimeizhuang.fashionmix.adapter.MyCommentAdapter;
import com.kuaimeizhuang.fashionmix.api.MyPagerApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.Message;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.UserNewsBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.FragmentCommentBinding;
import com.kuaimeizhuang.fashionmix.databinding.FragmentNotificationBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.ui.activity.video.VideoPlayDetailsActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.List;

import rx.Observable;

/**
 * <p>我的消息Model</p>
 * Created on 17/6/1.
 *
 * @author Shi GuangXiang.
 */

public class MyMessageViewModel extends BaseViewModel {
    MyPagerApi api = (MyPagerApi) RetrofitFactory.create(MyPagerApi.class);
    private int page;
    public MyCommentAdapter adapter = new MyCommentAdapter();
    private int post_page;
    public MyCommentAdapter postAdapter = new MyCommentAdapter();

    public MyMessageViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
    }

    @Override
    public void onActivityDestroy() {

    }

    /**
     * 获取评论列表
     *
     * @param binding binding
     */
    public void getCommentListData(FragmentCommentBinding binding, boolean isResfresh) {
        if (isResfresh) {
            page = 1;
        }
        Observable<HttpResult<UserNewsBean>> observable = api.userNews(UiUtils.getUserId(), "reply", page, 10);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                UserNewsBean userNewsBean = (UserNewsBean) object;
                boolean hasMorePages = userNewsBean.isHas_more_pages();
                if (hasMorePages) {
                    page++;
                }
                if (isResfresh) {
                    adapter.replaceData(userNewsBean.getMessages());
                    binding.recyclerViewList.refreshComplete(binding.ptrFrameLayout, hasMorePages);
                } else {
                    adapter.addData(userNewsBean.getMessages());
                    binding.recyclerViewList.notifyMoreFinish(hasMorePages);
                }

                binding.recyclerViewList.setOnRecyclerViewItemClickListener((itemView, position) -> {
                    Post data = adapter.getData().get(position).getMy_post();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post",data);
                    ActivitySwitchUtil.switchActivity(VideoPlayDetailsActivity.class,bundle);
                });
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    /**
     * 我的通知列表
     *
     * @param binding binding
     */
    public void getNotificationData(FragmentNotificationBinding binding, boolean isRefresh) {
        if (isRefresh) {
            post_page = 1;
        }
        Observable<HttpResult<UserNewsBean>> observable = api.userNews(UiUtils.getUserId(),
                "system", post_page, 10);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                UserNewsBean userNewsBean = (UserNewsBean) object;
                boolean has_more_pages = userNewsBean.isHas_more_pages();
                if (has_more_pages) {
                    post_page++;
                }

                if (isRefresh) {
                    postAdapter.replaceData(userNewsBean.getMessages());
                    binding.recyclerViewPost.refreshComplete(binding.ptrFrameLayout, has_more_pages);
                } else {
                    postAdapter.addData(userNewsBean.getMessages());
                    binding.recyclerViewPost.notifyMoreFinish(has_more_pages);
                }
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }
}
