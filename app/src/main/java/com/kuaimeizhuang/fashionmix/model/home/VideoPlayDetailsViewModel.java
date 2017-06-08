package com.kuaimeizhuang.fashionmix.model.home;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.adapter.CommentAdapter;
import com.kuaimeizhuang.fashionmix.adapter.TagAdapter;
import com.kuaimeizhuang.fashionmix.api.PostApi;
import com.kuaimeizhuang.fashionmix.base.BaseViewModel;
import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.Comment;
import com.kuaimeizhuang.fashionmix.bean.CommentBody;
import com.kuaimeizhuang.fashionmix.bean.Comments;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.UpDateVideoUrlBean;
import com.kuaimeizhuang.fashionmix.bean.Video;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.databinding.ActivityVideoPlayDetailsBinding;
import com.kuaimeizhuang.fashionmix.retrofit.RetrofitFactory;
import com.kuaimeizhuang.fashionmix.retrofit.rx.BeautyFunc1;
import com.kuaimeizhuang.fashionmix.retrofit.rx.BeautySubscriber;
import com.kuaimeizhuang.fashionmix.utils.GlideUtils;
import com.kuaimeizhuang.fashionmix.utils.KeyboardUtils;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;
import com.kuaimeizhuang.fashionmix.view.BeautyDialog;
import com.kuaimeizhuang.fashionmix.view.SimpleTextWatcher;
import com.kuaimeizhuang.fashionmix.view.VideoPlayView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>视频详情</p>
 * Created on 17/6/1.
 *
 * @author Shi GuangXiang.
 */

public class VideoPlayDetailsViewModel extends BaseViewModel implements VideoPlayView.OnVideoPlayError {
    private PostApi api = (PostApi) RetrofitFactory.create(PostApi.class);
    public ObservableField<String> commentPrefix = new ObservableField();//评论前@别人的前缀
    public ObservableBoolean commentSending = new ObservableBoolean();//评论发送中
    public ObservableField<String> commentContent = new ObservableField();//评论内容
    private int commentSuccessPosition = 0;
    public ObservableField<Post> mPost = new ObservableField<>();
    public ObservableBoolean showSendBtn = new ObservableBoolean();
    public CommentAdapter adapter = new CommentAdapter(this);
    private ActivityVideoPlayDetailsBinding binding;
    private boolean isCanPraise = true;
    private int likeCount = 0;
    private TagAdapter tagAdapter = new TagAdapter();
    private int mPage = 2;
    private int mCommentId;
    private boolean isReportPlayUrl = false;

    public VideoPlayDetailsViewModel(ActivityBaseBinding mBaseBinding, LoadingPager mPager, Activity mActivity) {
        super(mBaseBinding, mPager, mActivity);
    }

    @Override
    public void onActivityDestroy() {

    }

    public void getVideoPostDetailsData(ActivityVideoPlayDetailsBinding binding, int postId) {
        this.binding = binding;
        binding.videoPlay.setOnVideoPlayError(this);
        Observable<HttpResult<Post>> observable = api.getVideoDetail(postId);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                Post post = (Post) object;
                binding.setPost(post);
                setPost(post, binding);
                mPost.set(post);
                adapter.setOnDelCommentClick(comment -> {
                            BeautyDialog dialog = new BeautyDialog(mActivity).builder();
                            dialog.setTitle("确定删除吗?");
                            dialog.setNegativeButton("算了", view ->
                                    dialog.dismiss()
                            );
                            dialog.setPositiveButton("确定", view ->
                                    delComment(comment)).show();
                        }
                );
                boolean hasFollowed = post.getAuthor().isHas_followed();
                if (hasFollowed) {
                    binding.ivChannelFollow.setBackgroundResource(R.drawable.iv_tag_del_follow);
                } else {
                    binding.ivChannelFollow.setBackgroundResource(R.drawable.iv_tag_add_follow);
                }
                likeCount = post.getLikes_count();
            }

            @Override
            public void onDataError(String errorMsg) {
                UiUtils.showToastLong("接口出错" + errorMsg);
            }
        });
    }

    /**
     * 监听输入状态
     */
    public TextWatcher textWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable editable) {
            if ("".equals(editable.toString())) {
                showSendBtn.set(false);
            } else {
                showSendBtn.set(true);
            }
            setCommentContent(editable.toString());
        }
    };

    public void setCommentContent(String commentContent) {
        LogUtils.d("commentContent====" + commentContent);
        this.commentContent.set(commentContent);
    }

    /**
     * 发送评论
     *
     * @param binding binding
     * @param post    post
     */
    public void sendComment(ActivityVideoPlayDetailsBinding binding, Post post) {
        commentSending.set(true);
        Object tag = binding.btnSendComment.getTag();
        int parentId = 0;
        if (tag instanceof Integer) {
            parentId = (int) tag;
        }
        Observable<HttpResult<CommentBody>> observable = api.addComment(post.getId(), commentContent.get(), parentId, new HashMap<>(), new HashMap<>());
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                CommentBody commentBody = (CommentBody) object;
                UiUtils.showToastLong("评论成功");
                commentSending.set(false);
                LogUtils.d(commentBody.toString());
                commentContent.set("");
                commentPrefix.set("来说几句吧");
                binding.btnSendComment.setTag(0);
                post.setComments_count(post.getComments_count() + 1);
                binding.setPost(post);
                if (commentBody.getComment().getParent_id() > 0) {
                    List<Comment> data = adapter.getData();
                    data.get(commentSuccessPosition).getChildren_comments().add(commentBody.getComment());
                } else {
                    adapter.addSingleData(commentBody.getComment());
                }
                binding.recyclerLoadMore.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    private void setPost(Post post, ActivityVideoPlayDetailsBinding binding) {
        adapter.addData(post.getComments().getComments());
        GlideUtils.imageLoader(binding.ivUserHead, post.getAuthor().getAvatar_url());
        binding.tvUserName.setText(post.getAuthor().getNickname());
        binding.tvVideoDetailZanText.setText(String.valueOf(post.getLikes_count()));
        binding.tvCommentsCount.setText(post.getComments_count() + "人");
        commentPrefix.set("来说几句吧");

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.videoPlay.getLayoutParams();
        int screenWidth = UiUtils.getScreenWidth(mActivity);
        int videoWidth = post.getVideos().get(0).getWidth();
        int videoHeight = post.getVideos().get(0).getHeight();
        float i = (float) videoWidth / (float) videoHeight;
        int height = (int) ((float) screenWidth / i);
        layoutParams.height = height;
        binding.videoPlay.setLayoutParams(layoutParams);
        binding.videoPlay.setUp(post.getVideos().get(0).getVideo_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST, post.getTitle(), post);
        binding.videoPlay.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideUtils.imageLoaderXhdpi(binding.videoPlay.thumbImageView, post.getVideos().get(0).getImage_url());
        binding.recyclerLoadMore.setAutoLoadMoreEnable(post.getComments().getHas_more_pages());
        binding.recyclerLoadMore.setOnRecyclerViewItemClickListener((itemView, position) -> {
            KeyboardUtils.popSoftKeyboard(mActivity, binding.etPubCommentContent, true);
            Comment comment = adapter.getData().get(position);
            commentSuccessPosition = position;
            commentPrefix.set(comment != null ? "回复" + comment.getAuthor().getNickname() + ":" : "来说几句吧");
            binding.btnSendComment.setTag(comment.getId());
        });

        binding.recyclerTag.setAdapter(tagAdapter);
        tagAdapter.addDatas(post.getTags());
    }

    public void delComment(Comment comment) {
        Observable<HttpResult<Object>> observable = api.delComment(comment.getPost_id(), comment.getId());
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                UiUtils.showToastLong("删除成功");
                adapter.delSingleData(comment);
                mPost.get().setComments_count(mPost.get().getComments_count() - 1);
                List<Comment> comments = adapter.getData();
                for (int i = 0; i < comments.size(); i++) {
                    List<Comment> childrenComments = comments.get(i).getChildren_comments();
                    if (childrenComments.contains(comment)) {
                        childrenComments.remove(comment);
                    }
                }
                binding.recyclerLoadMore.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    /**
     * 取消或者添加关注
     *
     * @param binding binding
     */
    public void AddAndDelMast(ActivityVideoPlayDetailsBinding binding) {
        boolean hasFollowed = mPost.get().getAuthor().isHas_followed();
        if (hasFollowed) {
            delFollow(binding);
        } else {
            addFollow(binding);
        }

    }

    /**
     * 添加关注
     *
     * @param binding binding
     */
    private void addFollow(ActivityVideoPlayDetailsBinding binding) {
        Observable<HttpResult<Object>> observable = api.addFollowed(UiUtils.getUserId(), mPost.get().getAuthor().getId());
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                binding.ivChannelFollow.setBackgroundResource(R.drawable.iv_tag_del_follow);
                mPost.get().getAuthor().setHas_followed(true);
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    /**
     * 取消关注
     *
     * @param binding binding
     */
    private void delFollow(ActivityVideoPlayDetailsBinding binding) {
        Observable<HttpResult<Object>> observable = api.delFollowed(UiUtils.getUserId(), mPost.get().getAuthor().getId());
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                binding.ivChannelFollow.setBackgroundResource(R.drawable.iv_tag_add_follow);
                mPost.get().getAuthor().setHas_followed(false);
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    /**
     * 添加赞
     *
     * @param binding binding
     */
    public void addFollowZan(ActivityVideoPlayDetailsBinding binding, View view) {
        view.setSelected(true);
        binding.flFavor.addFavors();
        if (isCanPraise) {
            praisePost(mPost.get().getId());
        }
    }

    /**
     * 请求赞网络
     *
     * @param postId 帖子id
     */
    private void praisePost(int postId) {
        Observable<HttpResult<Object>> observable = api.praisePostDetail(postId);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                likeCount += 1;
                binding.tvVideoDetailZanText.setText(String.valueOf(likeCount));
                isCanPraise = false;
                mPost.get().setLikes_count(Integer.valueOf(likeCount));
                mPost.get().setHas_liked(true);
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    /**
     * 子评论被点击
     *
     * @param view
     */
    public void childCommentClick(View view) {
        binding.etPubCommentContent.requestFocus();
        binding.etPubCommentContent.setFocusableInTouchMode(true);
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.etPubCommentContent, InputMethodManager.SHOW_FORCED);

        Comment comment = (Comment) view.getTag();
        List<Comment> commentList = adapter.getData();
        for (int i = 0; i < commentList.size(); i++) {
            List<Comment> children_comments = commentList.get(i).getChildren_comments();
            if (children_comments.contains(comment)) {
                commentSuccessPosition = i;
            }
        }
        commentPrefix.set(comment != null ? "回复" + comment.getAuthor().getNickname() + ":" : "来说几句吧");
        binding.btnSendComment.setTag(comment.getId());
    }

    /**
     * 加载评论
     */
    public void loadComments() {
        Observable<HttpResult<Comments>> observable = api.getPostComments(mPost.get().getId() + "", mPage,
                mCommentId);
        addSubscription(observable, new OnDataSuccess() {
            @Override
            public void onDataSuccess(Object object) {
                Comments comments = (Comments) object;
                adapter.addData(comments.getComments());
                mCommentId = adapter.getData().get(adapter.getData().size() - 1).getId();
                mPage++;
                binding.recyclerLoadMore.notifyMoreFinish(comments.getHas_more_pages());
            }

            @Override
            public void onDataError(String errorMsg) {

            }
        });
    }

    @Override
    public void onErrorListener() {
        if (isReportPlayUrl) {
            isReportPlayUrl = false;
        } else {
            int postId = mPost.get().getId();
            Observable<HttpResult<UpDateVideoUrlBean>> collectPost = api.videoError(postId);
            collectPost.map(new BeautyFunc1<>(mBaseBinding, mPager))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BeautySubscriber<UpDateVideoUrlBean>(mBaseBinding, mPager) {
                        @Override
                        public void onDataSuccess(UpDateVideoUrlBean videoUrlBean) {
                            isReportPlayUrl = true;
                            LogUtils.d("UpDateVideoUrlBean" + videoUrlBean.getVideo_urls());
                            if (!videoUrlBean.getVideo_urls().equals("")) {
                                Post post = mPost.get();
                                Video video = post.getVideos().get(0);
                                List<String> urls = new ArrayList<>();
                                urls.add(videoUrlBean.getVideo_urls());
                                video.setVideo_urls(urls);
                                binding.videoPlay.setUp(videoUrlBean.getVideo_urls(), JCVideoPlayer
                                        .SCREEN_LAYOUT_LIST, mPost.get().getTitle(), post);
                                binding.videoPlay.startButton.performClick();
                            }
                        }
                    });
        }
    }
}
