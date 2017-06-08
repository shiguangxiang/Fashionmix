package com.kuaimeizhuang.fashionmix.ui.activity.home;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.databinding.ActivityTagDateilsBinding;
import com.kuaimeizhuang.fashionmix.model.home.TagDetailsViewModel;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * <p>标签详情</p>
 * Created on 17/5/25.
 *
 * @author Shi GuangXiang.
 */

public class TagDetailsActivity extends BaseActivity {
    private ActivityTagDateilsBinding binding;
    private TagDetailsViewModel viewModel;
    private int tagId;
    private String tagName;

    @Override
    protected View initView() {
        binding = (ActivityTagDateilsBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_tag_dateils);
        binding.loadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        UiUtils.setMakeupHeader(binding.ptrLayout, this);
        tagId = getIntent().getIntExtra("tag_id", 0);
        tagName = getIntent().getStringExtra("tag_name");
        getBaseBinding().baseTitle.showHeader();
        getBaseBinding().baseTitle.setHeaderTitle(tagName);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new TagDetailsViewModel(getBaseBinding(), mPager, this);
        binding.loadMoreRecyclerView.setAdapter(viewModel.adapter);
        viewModel.initTagDetailsData(binding, "", "", tagId + "");
    }

    @Override
    protected void reloadData() {
        viewModel.initTagDetailsData(binding, "", "", tagId + "");
    }

    @Override
    protected void initListener() {
        binding.ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, binding.loadMoreRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                viewModel.initTagDetailsData(binding, "down", "", tagId + "");
            }
        });

        binding.loadMoreRecyclerView.setLoadMoreListener(() -> {
            viewModel.initTagDetailsData(binding, "up", "", tagId + "");
        });
    }
}
