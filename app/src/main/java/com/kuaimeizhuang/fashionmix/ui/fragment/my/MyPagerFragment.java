package com.kuaimeizhuang.fashionmix.ui.fragment.my;

import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.ShareBean;
import com.kuaimeizhuang.fashionmix.databinding.FragmentMyPagerBinding;
import com.kuaimeizhuang.fashionmix.model.my.MyPagerViewModel;
import com.kuaimeizhuang.fashionmix.ui.activity.MyLikeVidepActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.my.MyAttentionActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.my.MyMessageActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.my.SettingActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.account.LoginActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.my.FeedBackActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;
import com.kuaimeizhuang.fashionmix.view.ShareDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>首页 我的页面</p>
 * Created on 17/5/11.
 *
 * @author Shi GuangXiang.
 */

public class MyPagerFragment extends BaseFragment implements View.OnClickListener {
    private FragmentMyPagerBinding binding;
    private MyPagerViewModel viewModel;

    @Override
    protected View initView() {
        binding = (FragmentMyPagerBinding) UiUtils.getDataBinding(mInflater, R.layout.fragment_my_pager);
        if (!UiUtils.isLogin()) {
            ActivitySwitchUtil.switchActivityForResult(LoginActivity.class, 100);
        }
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new MyPagerViewModel(getBaseBinding(), mPager, mActivity);
    }

    @Override
    protected void reloadData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
    }

    @Override
    protected void initListener() {
        binding.llMyUserMessage.setOnClickListener(this);
        binding.llMyUserLike.setOnClickListener(this);
        binding.llMyUserAttention.setOnClickListener(this);
        binding.llMyUserFeedback.setOnClickListener(this);
        binding.llMyUserShare.setOnClickListener(this);
        binding.llMyUserSetting.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (UiUtils.isLogin()) {
            viewModel.initMyPagerData(binding);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //我的消息
            case R.id.ll_my_user_message:
                ActivitySwitchUtil.switchActivity(MyMessageActivity.class);
                break;
            //我喜欢的
            case R.id.ll_my_user_like:
                ActivitySwitchUtil.switchActivity(MyLikeVidepActivity.class);
                break;
            //我的关注
            case R.id.ll_my_user_attention:
                ActivitySwitchUtil.switchActivity(MyAttentionActivity.class);
                break;

            //意见反馈
            case R.id.ll_my_user_feedback:
                ActivitySwitchUtil.switchActivity(FeedBackActivity.class);
                break;

            //分享app给好友
            case R.id.ll_my_user_share:
                ShareDialog.Builder builder = new ShareDialog.Builder(mActivity);
                builder.setTitle("分享到").setShareData(getShareDataList())
                        .setOnItemClickListener(viewModel::sharePlatform)
                        .create().show();
                break;
            //设置
            case R.id.ll_my_user_setting:
                ActivitySwitchUtil.switchActivityForResult(SettingActivity.class, 100);
                break;
        }
    }

    /**
     * 获取数据
     *
     * @return 数据
     */
    private List<ShareBean> getShareDataList() {
        List<ShareBean> beanList = new ArrayList<>();
        beanList.add(new ShareBean("微信", R.drawable.iv_share_wx));
        beanList.add(new ShareBean("朋友圈", R.drawable.iv_share_pyq));
        beanList.add(new ShareBean("QQ好友", R.drawable.iv_share_qq));
        beanList.add(new ShareBean("QQ空间", R.drawable.iv_share_kj));
        beanList.add(new ShareBean("微博", R.drawable.iv_share_wb));
        return beanList;
    }
}
