package com.kuaimeizhuang.fashionmix.ui.fragment.home;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.EventBusBean;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.databinding.FragmentHomeAttentionBinding;
import com.kuaimeizhuang.fashionmix.model.home.FragmentHomeAttentionViewModel;
import com.kuaimeizhuang.fashionmix.ui.activity.account.LoginActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.SPUtils;
import com.kuaimeizhuang.fashionmix.utils.StringUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * <p>首页关注页面</p>
 * Created on 17/5/12.
 *
 * @author Shi GuangXiang.
 */

public class HomeAttentionFragment extends BaseFragment implements View.OnClickListener {
    private FragmentHomeAttentionBinding binding;
    private FragmentHomeAttentionViewModel viewModel;

    @Override
    protected View initView() {
        binding = (FragmentHomeAttentionBinding) UiUtils.getDataBinding(mInflater, R.layout.fragment_home_attention);
        binding.recyclerViewAttention.setLayoutManager(new GridLayoutManager(mActivity, 3));
        binding.loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        binding.ptrLayout.disableWhenHorizontalMove(true);
        UiUtils.setMakeupHeader(binding.ptrLayout, mActivity);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
        viewModel = new FragmentHomeAttentionViewModel(getBaseBinding(), mPager, mActivity);
        binding.loadMoreRecyclerView.setAdapter(viewModel.followAttentionAdapter);

        EventBus.getDefault().register(this);
        //初始化页面
        if (UiUtils.isLogin()) {
            viewModel.getUserInfo(binding);
        } else {
            viewModel.initPager(binding);
            binding.btnToLogin.setText("立即登录");
        }
    }

    @Override
    protected void reloadData() {
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击去登录
            case R.id.btn_to_login:
                if (binding.btnToLogin.getText().toString().equals("确定关注")) {
                    String toString = viewModel.getTagList().toString();
                    String substring = toString.substring(1);
                    String tags = substring.substring(0, substring.length() - 1);
                    if (tags.length() > 0) {
                        viewModel.follwTags(tags, binding);
                    } else {
                        UiUtils.showToastLong("请关注！");
                    }
                } else {
                    ActivitySwitchUtil.switchActivity(LoginActivity.class);
                }
                break;
        }
    }

    @Override
    protected void initListener() {
        binding.btnToLogin.setOnClickListener(this);

        binding.ptrLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // return ViewCompat.canScrollVertically(mRecyclerView, -1);
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, binding.loadMoreRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                viewModel.getFollowPostListData(binding, 0, Constants.DOWN, "");
            }
        });

        binding.loadMoreRecyclerView.setLoadMoreListener(() ->
                viewModel.getFollowPostListData(binding, 0, Constants.UP, "")
        );
    }

    @Subscribe
    public void onEventMainThread(EventBusBean eventBusBean) {
        UiUtils.showToastLong(eventBusBean.getMessage());
        //初始化页面
        if (UiUtils.isLogin()) {
            viewModel.getUserInfo(binding);
        } else {
            viewModel.initPager(binding);
            binding.btnToLogin.setText("立即登录");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
