package com.kuaimeizhuang.fashionmix.ui.activity.video;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.CommentAdapter;
import com.kuaimeizhuang.fashionmix.base.BaseActivity;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.Comment;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.databinding.ActivityVideoPlayDetailsBinding;
import com.kuaimeizhuang.fashionmix.model.home.VideoPlayDetailsViewModel;
import com.kuaimeizhuang.fashionmix.ui.activity.user.UserPageActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.GlideUtils;
import com.kuaimeizhuang.fashionmix.utils.KeyboardUtils;
import com.kuaimeizhuang.fashionmix.utils.SPUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;
import com.kuaimeizhuang.fashionmix.view.KeyboardChangeListener;
import com.kuaimeizhuang.fashionmix.view.SoftKeyboardStateHelper;


import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * <p>视频详情页面</p>
 * Created on 17/5/26.
 *
 * @author Shi GuangXiang.
 */

public class VideoPlayDetailsActivity extends BaseActivity implements View.OnClickListener, View
        .OnTouchListener, KeyboardChangeListener.KeyBoardListener {
    private ActivityVideoPlayDetailsBinding binding;
    private Post post;
    private VideoPlayDetailsViewModel viewModel;
    private KeyboardChangeListener mkeyListener;

    @Override
    protected View initView() {
        binding = (ActivityVideoPlayDetailsBinding) UiUtils.getDataBinding(getLayoutInflater(), R.layout.activity_video_play_details);
        SPUtils.setBoolean("isHide", true);
        post = (Post) getIntent().getSerializableExtra("post");
        if (post != null) {
            getBaseBinding().baseTitle.showHeader();
            getBaseBinding().baseTitle.setHeaderTitle(post.getTitle());
//            getBaseBinding().baseTitle.showHeadRightImage();
        }
        binding.recyclerLoadMore.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerTag.setLayoutManager(linearLayoutManager);
        binding.recyclerLoadMore.setHasFixedSize(true);
        mkeyListener = new KeyboardChangeListener(this);
        mkeyListener.setKeyBoardListener(this);
        return binding.getRoot();
    }

    @Override
    protected void initData() {
        viewModel = new VideoPlayDetailsViewModel(getBaseBinding(), mPager, mActivity);
        viewModel.getVideoPostDetailsData(binding, post.getId());
        binding.setViewModel(viewModel);
        binding.recyclerLoadMore.setAdapter(viewModel.adapter);
    }

    @Override
    protected void reloadData() {
        viewModel.getVideoPostDetailsData(binding, post.getId());
    }

    @Override
    protected void initListener() {
        binding.btnSendComment.setOnClickListener(this);
        binding.llRoot.setOnTouchListener(this);
        binding.ivChannelFollow.setOnClickListener(this);
        binding.ivVideoDetailZan.setOnClickListener(this);
        binding.ivUserHead.setOnClickListener(this);
        binding.recyclerLoadMore.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                KeyboardUtils.hide(mActivity);
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        binding.recyclerLoadMore.setLoadMoreListener(() ->
                viewModel.loadComments()
        );

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //发送评论
            case R.id.btn_send_comment:
                viewModel.sendComment(binding, post);
                break;
            //关注或者取关
            case R.id.iv_channel_follow:
                viewModel.AddAndDelMast(binding);
                break;
            case R.id.iv_video_detail_zan:
                viewModel.addFollowZan(binding, view);
                break;
            //频道头像
            case R.id.iv_user_head:
                Post post = viewModel.mPost.get();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", post.getAuthor());
                ActivitySwitchUtil.switchActivity(UserPageActivity.class, bundle);
                break;
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        KeyboardUtils.hide(mActivity);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                .MATCH_PARENT, UiUtils.getScreenWidth(mActivity) / 2);
        binding.headerLayout.setLayoutParams(params);
    }

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
//        UiUtils.showToastLong(isShow + "" + keyboardHeight);
        if (isShow) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                    .MATCH_PARENT, UiUtils.getScreenWidth(mActivity) / 6);
            binding.headerLayout.setLayoutParams(params);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                    .MATCH_PARENT, UiUtils.getScreenWidth(mActivity) / 2);
            binding.headerLayout.setLayoutParams(params);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
