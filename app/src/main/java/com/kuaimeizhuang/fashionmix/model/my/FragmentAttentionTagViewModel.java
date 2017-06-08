package com.kuaimeizhuang.fashionmix.model.my;

import android.app.Activity;
import android.os.Bundle;

import com.kuaimeizhuang.fashionmix.adapter.AttentionChannelListAdapter;
import com.kuaimeizhuang.fashionmix.adapter.AttentionTagListAdapter;
import com.kuaimeizhuang.fashionmix.api.MyPagerApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.AttentionBean;
import com.kuaimeizhuang.fashionmix.bean.MasterPrefectureListBean;
import com.kuaimeizhuang.fashionmix.bean.TagListBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.FragmentAttentionChannelBinding;
import com.kuaimeizhuang.fashionmix.databinding.FragmentAttentionTagBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.ui.activity.home.TagDetailsActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.user.UserPageActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import rx.Observable;

/**
 * <p>我的关注Model</p>
 * Created on 17/5/25.
 *
 * @author Shi GuangXiang.
 */

public class FragmentAttentionTagViewModel extends BaseViewModel {
    private MyPagerApi api = (MyPagerApi) RetrofitFactory.create(MyPagerApi.class);
    private AttentionTagListAdapter adapter = new AttentionTagListAdapter();
    private AttentionChannelListAdapter masterPrefectureAdapter = new AttentionChannelListAdapter();

    private int page;

    public FragmentAttentionTagViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
    }

    @Override
    public void onActivityDestroy() {

    }

    /**
     * 获取我的关注标签
     *
     * @param binding binding
     */
    public void getAttentionTagData(FragmentAttentionTagBinding binding) {
        binding.recyclerTagsAttention.setAdapter(adapter);
        Observable<HttpResult<TagListBean>> observable = api.getAttentionTagsData();
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                TagListBean tagListBean = (TagListBean) object;
                LogUtils.d(tagListBean.getTags().size() + "");
                adapter.addData(tagListBean.getTags());
                //item点击事件
                adapter.setOnItemClickListener(newestTag -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("tag_id", newestTag.getId());
                    bundle.putString("tag_name", newestTag.getName());
                    ActivitySwitchUtil.switchActivity(TagDetailsActivity.class, bundle);
                });
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    /**
     * 获取我的关注中的频道标签
     *
     * @param binding binding
     */
    public void getAttentionChannelData(FragmentAttentionChannelBinding binding, boolean isRefresh) {
        binding.recyclerViewChannal.setAdapter(masterPrefectureAdapter);
        if (isRefresh) {
            page = 1;
        }
        Observable<HttpResult<MasterPrefectureListBean>> observable = api.userFollowsList(UiUtils.getUserId(), page, 10);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                MasterPrefectureListBean masterPrefectureListBean = (MasterPrefectureListBean) object;
                boolean hasMorePages = masterPrefectureListBean.isHas_more_pages();
                if (hasMorePages) {
                    page++;
                }

                if (isRefresh) {
                    masterPrefectureAdapter.replacePostData(masterPrefectureListBean.getUsers());
                    binding.recyclerViewChannal.refreshComplete(binding.ptrLayout, hasMorePages);
                } else {
                    masterPrefectureAdapter.addData(masterPrefectureListBean.getUsers());
                    binding.recyclerViewChannal.notifyMoreFinish(hasMorePages);
                }

                binding.recyclerViewChannal.setOnRecyclerViewItemClickListener((itemView,
                                                                                position) -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", masterPrefectureListBean.getUsers().get(position));
                    ActivitySwitchUtil.switchActivity(UserPageActivity.class, bundle);
                });
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }
}
