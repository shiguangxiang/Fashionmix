package com.kuaimeizhuang.fashionmix.model.my;

import android.app.Activity;
import android.os.Bundle;

import com.kuaimeizhuang.fashionmix.adapter.HotListAdapter;
import com.kuaimeizhuang.fashionmix.api.MyPagerApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.PostList;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.ActivityLikeVideoBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.ui.activity.video.VideoPlayDetailsActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;

import java.util.List;

import rx.Observable;

/**
 * <p>我的喜欢 model</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class MyLikeVidepViewModel extends BaseViewModel {
    public HotListAdapter adapter;
    private MyPagerApi api = (MyPagerApi) RetrofitFactory.create(MyPagerApi.class);
    private String sequence;
    private int postId;

    public MyLikeVidepViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
        adapter = new HotListAdapter(mActivity, false);
    }

    @Override
    public void onActivityDestroy() {

    }

    /**
     * 加载数据
     *
     * @param binding binding
     * @param load    加载方式
     */
    public void initData(ActivityLikeVideoBinding binding, String load) {
        if (load.equals("down")) {//下拉刷新
            List<Post> posts = adapter.getData();
            if (posts.size() > 0) {
                Post post = posts.get(0);
                sequence = post.getCreated_at();
                postId = post.getId();
            }
        } else if (load.equals("up")) {//上拉加载
            List<Post> posts = adapter.getData();
            if (posts.size() > 0) {
                Post post = posts.get(posts.size() - 1);
                sequence = post.getCreated_at();
                postId = post.getId();
            }
        } else {//第一次加载
            sequence = "";
            postId = 0;
        }
        Observable<HttpResult<PostList>> observable = api.getLikePostDatas(10, postId, load, sequence);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                PostList postList = (PostList) object;
                boolean hasPage = postList.isHas_page();
                if (hasPage) {
                }

                if (load.equals("down")) {//下拉刷新
                    adapter.addPostData(postList.getPosts());
                    binding.recyclerView.refreshComplete(binding.ptrLayout, hasPage);
                } else if (load.equals("up")) {//上拉加载
                    adapter.addPostData(postList.getPosts());
                    binding.recyclerView.notifyMoreFinish(hasPage);
                } else {//第一次加载
                    adapter.replacePostData(postList.getPosts());
                    binding.recyclerView.notifyMoreFinish(hasPage);
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
}
