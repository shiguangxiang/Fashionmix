package com.kuaimeizhuang.fashionmix.ui.activity.user;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.bean.User;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.databinding.ActivityUserPageBinding;
import com.kuaimeizhuang.fashionmix.model.user.ActivityUserPageViewModel;
import com.kuaimeizhuang.fashionmix.ui.activity.account.LoginActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.SPUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

/**
 * <p>用户个人主页</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class UserPageActivity extends BaseActivity {
    private ActivityUserPageBinding binding;
    private ActivityUserPageViewModel viewModel;
    private User user;

    @Override
    protected View initView() {
        binding = (ActivityUserPageBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_user_page);
        user = (User) getIntent().getSerializableExtra("user");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        SPUtils.setBoolean("isHide", true);
        viewModel = new ActivityUserPageViewModel(getBaseBinding(), mPager, mActivity);
        viewModel.initData(binding, user, true);

    }

    @Override
    protected void reloadData() {
        viewModel.initData(binding, user, true);
    }

    @Override
    protected void initListener() {
        binding.recyclerView.setLoadMoreListener(() ->
                viewModel.loadPostData(binding, user, false, Constants.UP)
        );
        binding.ivBack.setOnClickListener(view ->
                finish()
        );
    }

    /**
     * 点击关注按钮
     *
     * @param view view
     */
    public void btnFollow(View view) {
        if (UiUtils.isLogin()) {
            //如果是已关注的就删除
            User user = viewModel.getUser();
            if (user.isHas_followed()) {
                viewModel.deleteTagFollow(user, view);
            } else {//否则就是添加
                viewModel.addTagFollow(user, view);
            }
        } else {
            ActivitySwitchUtil.switchActivity(LoginActivity.class);
        }

    }
}
