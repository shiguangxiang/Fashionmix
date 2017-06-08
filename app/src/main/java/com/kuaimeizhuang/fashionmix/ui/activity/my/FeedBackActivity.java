package com.kuaimeizhuang.fashionmix.ui.activity.my;

import android.text.TextUtils;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.databinding.ActivityFeedbackBinding;
import com.kuaimeizhuang.fashionmix.model.my.FeedBackViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

/**
 * <p>意见反馈页面</p>
 * Created on 17/5/23.
 *
 * @author Shi GuangXiang.
 */

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    private ActivityFeedbackBinding binding;
    private FeedBackViewModel viewModel;

    @Override
    protected View initView() {
        binding = (ActivityFeedbackBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_feedback);
        getBaseBinding().baseTitle.showHeader();
        getBaseBinding().baseTitle.setHeaderTitle("意见反馈");
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
        viewModel = new FeedBackViewModel(getBaseBinding(), mPager, mActivity);
    }

    @Override
    protected void reloadData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
    }

    @Override
    protected void initListener() {
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String content = binding.edtContent.getText().toString().trim();
        String qq = binding.edtQq.getText().toString().trim();
        if (valid(qq, content)) {
            return;
        }
        viewModel.submitFeedback(qq, content);
    }

    /**
     * 验证手机号与QQ号码合法性
     *
     * @param feedQQ      反馈QQ
     * @param feedContent 反馈内容
     * @return 是否合法
     */
    public boolean valid(String feedQQ, String feedContent) {
        if (TextUtils.isEmpty(feedContent)) {
            UiUtils.showToastLong("请输入问题内容");
            return true;
        }
        if (TextUtils.isEmpty(feedQQ)) {
            UiUtils.showToastLong("请输入QQ号码");
            return true;
        }
        return false;
    }

}
