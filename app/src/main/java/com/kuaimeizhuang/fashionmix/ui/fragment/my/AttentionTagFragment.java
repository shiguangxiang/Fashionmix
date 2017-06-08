package com.kuaimeizhuang.fashionmix.ui.fragment.my;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.bean.Event;
import com.kuaimeizhuang.fashionmix.databinding.FragmentAttentionTagBinding;
import com.kuaimeizhuang.fashionmix.model.my.FragmentAttentionTagViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * <p>我的关注：标签TAB</p>
 * Created on 17/5/25.
 *
 * @author Shi GuangXiang.
 */

public class AttentionTagFragment extends BaseFragment {
    private FragmentAttentionTagBinding binding;
    private FragmentAttentionTagViewModel viewModel;

    @Override
    protected View initView() {
        binding = (FragmentAttentionTagBinding) UiUtils.getDataBinding(mInflater, R.layout.fragment_attention_tag);
        binding.recyclerTagsAttention.setLayoutManager(new LinearLayoutManager(getActivity()));
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new FragmentAttentionTagViewModel(getBaseBinding(), mPager, mActivity);
    }

    @Override
    protected void reloadData() {
        viewModel.getAttentionTagData(binding);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getAttentionTagData(binding);

    }
}
