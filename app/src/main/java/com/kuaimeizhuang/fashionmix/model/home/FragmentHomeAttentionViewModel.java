package com.kuaimeizhuang.fashionmix.model.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.kuaimeizhuang.fashionmix.adapter.AttentionAdapter;
import com.kuaimeizhuang.fashionmix.adapter.HomeFollowAttentionAdapter;
import com.kuaimeizhuang.fashionmix.api.HomePagerApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.HomeNewestBean;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.Tags;
import com.kuaimeizhuang.fashionmix.bean.User;
import com.kuaimeizhuang.fashionmix.bean.UserBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.FragmentHomeAttentionBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.retrofit.rx.BeautyFunc1;
import com.kuaimeizhuang.fashionmix.retrofit.rx.BeautySubscriber;
import com.kuaimeizhuang.fashionmix.ui.activity.video.VideoPlayDetailsActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>首页关注model</p>
 * Created on 17/5/15.
 *
 * @author Shi GuangXiang.
 */

public class FragmentHomeAttentionViewModel extends BaseViewModel {
    private HomePagerApi api = (HomePagerApi) RetrofitFactory.create(HomePagerApi.class);
    public AttentionAdapter adapter;

    private List<String> tagList = new ArrayList<>();

    public HomeFollowAttentionAdapter followAttentionAdapter;

    public FragmentHomeAttentionViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
        int screenWidth = UiUtils.getScreenWidth(mActivity);
        adapter = new AttentionAdapter(screenWidth);
        followAttentionAdapter = new HomeFollowAttentionAdapter(screenWidth, mActivity);
    }

    @Override
    public void onActivityDestroy() {

    }

    /**
     * 初始化页面逻辑
     *
     * @param binding binding布局
     */
    public void initPager(FragmentHomeAttentionBinding binding) {
        binding.recyclerViewAttention.setAdapter(adapter);
        Observable<HttpResult<Tags>> observable = api.getTagRecommened();
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                Tags tags = (Tags) object;
                adapter.addData(tags.getTags());
                binding.llAttentionTag.setVisibility(View.VISIBLE);
                binding.ptrLayout.setVisibility(View.GONE);

                adapter.setOnItemClickListener(((newestTag, position) -> {
                    boolean follw = newestTag.isFollw();
                    adapter.nofiItem(!follw, position);
                    if (follw) {
                        tagList.remove(newestTag.getId() + "");
                    } else {
                        tagList.add(newestTag.getId() + "");
                    }
                    LogUtils.d(tagList.toString());

                }));
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });

    }

    /**
     * 获取关注的标签
     *
     * @return 标签列表
     */
    public List<String> getTagList() {
        return tagList;
    }

    /**
     * 获取用户信息
     *
     * @param binding binding
     */
    public void getUserInfo(FragmentHomeAttentionBinding binding) {
        Observable<HttpResult<UserBean>> observable = api.getUserInfo();
        observable.map(new BeautyFunc1<>(mBaseBinding, mPager))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BeautySubscriber<UserBean>(mBaseBinding, mPager) {
                    @Override
                    public void onDataSuccess(UserBean user) {
                        int followTagCount = user.getUser().getFollow_tag_count();
                        if (followTagCount > 0) {
                            binding.llAttentionTag.setVisibility(View.GONE);
                            binding.ptrLayout.setVisibility(View.VISIBLE);
                            getFollowPostListData(binding, 0, "", "");
                        } else {
                            initPager(binding);
                            binding.btnToLogin.setText("确定关注");
                            binding.tvTagTitle.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * 获取关注列表数据
     *
     * @param binding bindin
     */
    public void getFollowPostListData(FragmentHomeAttentionBinding binding, int postId, String load, String sequence) {
        if (load.equals("down")) {//下拉刷新
            List<Post> posts = followAttentionAdapter.getPostList();
            if (posts.size() > 0) {
                Post post = posts.get(0);
                sequence = post.getCreated_at();
                postId = post.getId();
            }
        } else if (load.equals("up")) {//上拉加载
            List<Post> posts = followAttentionAdapter.getPostList();
            if (posts.size() > 0) {
                Post post = posts.get(posts.size() - 1);
                sequence = post.getCreated_at();
                postId = post.getId();
            }
        } else {//第一次加载
            sequence = "";
            postId = 0;
        }
        Observable<HttpResult<HomeNewestBean>> observable = api.getFollowPostData(postId, load, sequence);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                HomeNewestBean homeNewestBean = (HomeNewestBean) object;
                boolean hasPage = homeNewestBean.isHas_page();
                if (load.equals(Constants.DOWN)) {
                    followAttentionAdapter.addPostData(homeNewestBean.getPosts());
                    binding.loadMoreRecyclerView.refreshComplete(binding.ptrLayout, hasPage);
                } else if (load.equals(Constants.UP)) {
                    followAttentionAdapter.addPostData(homeNewestBean.getPosts());
                    binding.loadMoreRecyclerView.notifyMoreFinish(hasPage);
                } else {
                    followAttentionAdapter.replacePostData(homeNewestBean.getPosts());
                    binding.loadMoreRecyclerView.refreshComplete(binding.ptrLayout, hasPage);
                }

                binding.loadMoreRecyclerView.setOnRecyclerViewItemClickListener((itemView, position) -> {
                    List<Post> postList = followAttentionAdapter.getPostList();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post",postList.get(position));
                    ActivitySwitchUtil.switchActivity(VideoPlayDetailsActivity.class,bundle);

                });
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    /**
     * 关注标签
     *
     * @param tags 标签
     */
    public void follwTags(String tags, FragmentHomeAttentionBinding binding) {
        Observable<HttpResult<Object>> observable = api.follwTags(tags);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                UiUtils.showToastLong("关注成功");
                getUserInfo(binding);
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }
}
