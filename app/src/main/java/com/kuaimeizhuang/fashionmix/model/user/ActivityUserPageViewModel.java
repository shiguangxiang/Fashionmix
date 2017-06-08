package com.kuaimeizhuang.fashionmix.model.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.HotListAdapter;
import com.kuaimeizhuang.fashionmix.api.HomePagerApi;
import com.kuaimeizhuang.fashionmix.api.MyPagerApi;
import com.kuaimeizhuang.fashionmix.api.PostApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.User;
import com.kuaimeizhuang.fashionmix.bean.UserPageBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.ActivityUserPageBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.ui.activity.video.VideoPlayDetailsActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.GlideUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.List;

import rx.Observable;

/**
 * <p>用户个人主页Model</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class ActivityUserPageViewModel extends BaseViewModel {
    private MyPagerApi api = (MyPagerApi) RetrofitFactory.create(MyPagerApi.class);
    private HomePagerApi homePagerApi = (HomePagerApi) RetrofitFactory.create(HomePagerApi.class);
    private PostApi postApi = (PostApi) RetrofitFactory.create(PostApi.class);
    private int page;
    private HotListAdapter adapter;
    public User mUser;
    private int postId;
    private String createAt;

    public ActivityUserPageViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
        adapter = new HotListAdapter(mActivity, false);
    }

    @Override
    public void onActivityDestroy() {

    }

    /**
     * 初始化用户个人主页数据
     *
     * @param binding binding
     */
    public void initData(ActivityUserPageBinding binding, User user, boolean isRefresh) {
        this.mUser = user;
        GlideUtils.imageLoaderBlur(binding.ivBlurImageUrl, user.getBackground_url());
        boolean hasFollowed = user.isHas_followed();
        if (hasFollowed) {
            binding.ivFollow.setBackgroundResource(R.drawable.iv_tag_del_follow);
        } else {
            binding.ivFollow.setBackgroundResource(R.drawable.iv_tag_add_follow);
        }
        binding.setUser(user);
        binding.recyclerView.setAdapter(adapter);
        loadPostData(binding, user, isRefresh, "");
    }

    public void loadPostData(ActivityUserPageBinding binding, User user, boolean isRefresh, String load) {
        List<Post> postList = adapter.getData();
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
        Observable<HttpResult<UserPageBean>> observable = api.getUserPostList(user.getId() + "", "outer-video", postId, load, createAt);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                UserPageBean userPageBean = (UserPageBean) object;
                boolean hasMorePages = userPageBean.isHas_page();

                if (load.equals(Constants.UP)) {
                    adapter.addPostData(userPageBean.getPosts());
                    binding.recyclerView.notifyMoreFinish(hasMorePages);
                } else {
                    adapter.replacePostData(userPageBean.getPosts());
                }
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });

        binding.recyclerView.setOnRecyclerViewItemClickListener((itemView, position) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("post", adapter.getData().get(position));
            ActivitySwitchUtil.switchActivity(VideoPlayDetailsActivity.class, bundle);
        });
    }

    /**
     * 取关标签
     *
     * @param user 标签
     */
    public void deleteTagFollow(User user, View view) {
        mUser = user;
        view.setClickable(false);
        Observable<HttpResult<Object>> observable = postApi.delFollowed(UiUtils.getUserId(), user.getId());
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                view.setClickable(true);
                view.setBackgroundResource(R.drawable.iv_tag_add_follow);
                mUser.setHas_followed(false);
            }

            @Override
            public void onDataError(String errorMsg) {
                view.setClickable(true);
            }
        });
    }


    /**
     * 关注标签
     *
     * @param user 标签
     */
    public void addTagFollow(User user, View view) {
        mUser = user;
        view.setClickable(false);
        Observable<HttpResult<Object>> observable = postApi.addFollowed(UiUtils.getUserId(), user.getId());
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                view.setClickable(true);
                view.setBackgroundResource(R.drawable.iv_tag_del_follow);
                mUser.setHas_followed(true);
            }

            @Override
            public void onDataError(String errorMsg) {
                view.setClickable(true);
            }
        });
    }

    public User getUser() {
        return mUser;
    }

}
