package com.kuaimeizhuang.fashionmix.model.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.TagDetailsAdapter;
import com.kuaimeizhuang.fashionmix.api.HomePagerApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.Event;
import com.kuaimeizhuang.fashionmix.bean.NewestTag;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.TagDetailsBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.ActivityTagDateilsBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.ui.activity.account.LoginActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.video.VideoPlayDetailsActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Observable;

/**
 * <p>标签详情页面</p>
 * Created on 17/5/25.
 *
 * @author Shi GuangXiang.
 */

public class TagDetailsViewModel extends BaseViewModel {
    private HomePagerApi api = (HomePagerApi) RetrofitFactory.create(HomePagerApi.class);
    public TagDetailsAdapter adapter;
    private int index;

    private boolean isFollow;
    private int postId;

    public TagDetailsViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
        adapter = new TagDetailsAdapter(mActivity);
    }

    @Override
    public void onActivityDestroy() {
    }

    /**
     * 初始化加载标签详情数据
     *
     * @param binding  binding
     * @param load     加载方式
     * @param sequence 发布时间
     * @param tagId    标签id
     */
    public void initTagDetailsData(ActivityTagDateilsBinding binding, String load, String sequence, String tagId) {
        if (load.equals("down")) {//下拉刷新
            List<Post> posts = adapter.getPostList();
            if (posts.size() > 0) {
                Post post = posts.get(0);
                sequence = post.getCreated_at();
                postId = post.getId();
            }
        } else if (load.equals("up")) {//上拉加载
            List<Post> posts = adapter.getPostList();
            if (posts.size() > 0) {
                Post post = posts.get(posts.size() - 1);
                sequence = post.getCreated_at();
                postId = post.getId();
            }
        } else {//第一次加载
            sequence = "";
            postId = 0;
        }
        Observable<HttpResult<TagDetailsBean>> observable = api.getTagDetailsData(postId, load,
                sequence, tagId);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                TagDetailsBean detailsBean = (TagDetailsBean) object;
                isFollow = detailsBean.getTag().isFollw();
                boolean hasPage = detailsBean.isHas_page();
                if (load.equals(Constants.DOWN)) {
                    adapter.addPostData(detailsBean.getPosts(),0);
                    binding.loadMoreRecyclerView.refreshComplete(binding.ptrLayout, hasPage);
                } else if (load.equals(Constants.UP)) {
                    adapter.addPostData(detailsBean.getPosts(), index++);
                    binding.loadMoreRecyclerView.notifyMoreFinish(hasPage);
                } else {
                    adapter.replacePostData(detailsBean.getPosts());
                    adapter.addTag(detailsBean.getTag());
                    binding.loadMoreRecyclerView.refreshComplete(binding.ptrLayout, hasPage);
                    binding.ptrLayout.setVisibility(View.VISIBLE);
                    binding.tvNullData.setVisibility(View.GONE);
                }

                adapter.setOnBtnFollow((newestTag, view) -> {
                    if (UiUtils.isLogin()) {
                        //如果是已关注的就删除
                        if (newestTag.isFollw()) {
                            deleteTagFollow(newestTag, view);
                        } else {//否则就是添加
                            addTagFollow(newestTag, view);
                        }
                    } else {
                        ActivitySwitchUtil.switchActivity(LoginActivity.class);
                    }

                });

                binding.loadMoreRecyclerView.setOnRecyclerViewItemClickListener((itemView, position) -> {
                    if (position > 0) {
                        Post post = adapter.getPostList().get(position - 1);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("post", post);
                        ActivitySwitchUtil.switchActivity(VideoPlayDetailsActivity.class, bundle);
                    }
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
     * @param newestTag 标签
     */
    private void addTagFollow(NewestTag newestTag, View view) {
        view.setClickable(false);
        Observable<HttpResult<Object>> observable = api.follwTags(newestTag.getId() + "");
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                adapter.nofiFollowStatus(true);
                view.setClickable(true);
                view.setBackgroundResource(R.drawable.iv_tag_del_follow);
            }

            @Override
            public void onDataError(String errorMsg) {
                view.setClickable(true);
            }
        });
    }

    /**
     * 取关标签
     *
     * @param newestTag 标签
     */
    private void deleteTagFollow(NewestTag newestTag, View view) {
        view.setClickable(false);
        Observable<HttpResult<Object>> observable = api.deleteFollowTag(newestTag.getId() + "");
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                adapter.nofiFollowStatus(false);
                view.setClickable(true);
                view.setBackgroundResource(R.drawable.iv_tag_add_follow);
            }

            @Override
            public void onDataError(String errorMsg) {
                view.setClickable(true);
            }
        });
    }
}
