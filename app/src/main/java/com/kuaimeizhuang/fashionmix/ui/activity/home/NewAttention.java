package com.kuaimeizhuang.fashionmix.ui.activity.home;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.NewAdapter;
import com.kuaimeizhuang.fashionmix.base.BaseFragment;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.EventBusBean;
import com.kuaimeizhuang.fashionmix.config.Constants;
import com.kuaimeizhuang.fashionmix.databinding.FragmentHomeAttentionBinding;
import com.kuaimeizhuang.fashionmix.databinding.NewItemBinding;
import com.kuaimeizhuang.fashionmix.model.home.FragmentHomeAttentionViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * <p>注释类</p>
 * Created on 17/6/6.
 *
 * @author Shi GuangXiang.
 */

public class NewAttention extends BaseFragment {
    private NewItemBinding binding;

    @Override
    protected View initView() {
        binding = (NewItemBinding) UiUtils.getDataBinding(mInflater, R.layout.new_item);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        UiUtils.setMakeupHeader(binding.ptrLayout,mActivity);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        mPager.onDataLoading(LoadingPager.LoadedResult.SUCCESS);
        NewAdapter adapter = new NewAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void reloadData() {
        initData();
    }


    @Override
    protected void initListener() {

    }


}

