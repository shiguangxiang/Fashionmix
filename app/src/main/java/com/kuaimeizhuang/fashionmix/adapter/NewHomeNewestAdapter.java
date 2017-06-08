package com.kuaimeizhuang.fashionmix.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.NewestTag;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.Tag;
import com.kuaimeizhuang.fashionmix.databinding.ItemNewestPostBinding;
import com.kuaimeizhuang.fashionmix.ui.activity.RecommendTagActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.home.TagDetailsActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.user.UserPageActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.GlideUtils;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.TimeFormatUtil;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/6/3.
 *
 * @author Shi GuangXiang.
 */

public class NewHomeNewestAdapter extends RecyclerView.Adapter<NewHomeNewestAdapter.HomeNewestPostViewHolder> {


    private List<Post> postList = new ArrayList<>();
    private List<NewestTag> newestTagList = new ArrayList<>();

    private static final int TYPE_TAGS = 11;
    private static final int TYPE_POST = 12;

    private int mWidth;
    private Activity mActivity;
    private String createAt;
    private OnItemTagClickListener onItemTagListener;
    private OnItemPostClickListener onItemPostClickListener;
    private String updateCount = "";
    private boolean isShow;

    /**
     * 添加帖子列表数据
     *
     * @param postList 帖子数据
     */
    public void addPostData(List<Post> postList, int index,boolean isShow) {
        this.isShow = isShow;
        if (postList != null) {
            //如果index大于0，就表示加载更多，否则就是第一页
            if (index > 0) {
                int mIndex = 0;
                createAt = TimeFormatUtil.getDateYear(this.postList.get(this.postList.size() - 1).getCreated_at());
                for (int i = 0; i < postList.size(); i++) {
                    //取数据第 i 条的日期
                    String dateYear = TimeFormatUtil.getDateYear(postList.get(i).getCreated_at());
                    LogUtils.d(dateYear + "dateYear" + createAt + "createdAt");
                    /**
                     * 拿第一条的时间日期比较上一条条数据的时间日期，
                     */
                    if (TextUtils.equals(createAt, dateYear)) {
                        postList.get(i).setIsTime(3);

                    } else {
                        mIndex = i;
                        postList.get(mIndex).setIsTime(2);
                        createAt = TimeFormatUtil.getDateYear(postList.get(mIndex).getCreated_at());
                    }
                }
            } else {
                int mIndex = 0;
                for (int i = 0; i < postList.size(); i++) {
                    //取数据第 i 条的日期
                    String dateYear = TimeFormatUtil.getDateYear(postList.get(i).getCreated_at());
                    String nowDate = TimeFormatUtil.getNowDate();
                    /**
                     * 拿第一条的时间日期比较第i条数据的时间日期，
                     */
                    if (TextUtils.equals(nowDate, dateYear)) {
                        postList.get(0).setIsTime(5);
                        mIndex = i;
                    } else {
                        //取数据第 index 条的日期
                        String createdAt = TimeFormatUtil.getDateYear(postList.get(mIndex).getCreated_at());
                        if (TextUtils.equals(createdAt, dateYear)) {
                            postList.get(mIndex).setIsTime(2);
                        } else {
                            mIndex = i;
                        }

                    }
                    LogUtils.e("IsTime---" + postList.get(i).getIsTime());
                }
            }
            this.postList.addAll(postList);
        }
        int size = postList.size();
        if (size > 0) {
            updateCount = "为您更新了" + size + "条数据~";

        } else {
            updateCount = "已经是最新视频！";
        }
        LogUtils.e("adapter : " + " size: " + this.postList.size());
        super.notifyDataSetChanged();
    }


    /**
     * 获取数据
     *
     * @return 数据
     */
    public List<Post> getPostList() {
        return postList;
    }

    /**
     * 替换数据
     *
     * @param postList 帖子数据
     */
    public void replacePostData(List<Post> postList,boolean isShow) {
        this.postList.clear();
        addPostData(postList, 1,isShow);
    }


    /**
     * 添加标签数据
     *
     * @param newestTagList 标签列表数据
     */
    public void addTagData(List<NewestTag> newestTagList) {
        if (newestTagList != null) {
            this.newestTagList.addAll(newestTagList);
        }
        notifyDataSetChanged();
    }

    public void replaceTagData(List<NewestTag> newestTagList) {
        this.newestTagList.clear();
        addTagData(newestTagList);
    }

    public NewHomeNewestAdapter(int mWidth, Activity activity) {
        this.mWidth = mWidth;
        this.mActivity = activity;
    }

    @Override
    public HomeNewestPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemNewestPostBinding binding = (ItemNewestPostBinding) UiUtils.getDataBinding(inflater, R.layout.item_newest_post);
        return new HomeNewestPostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(HomeNewestPostViewHolder holder, int position) {
        holder.setData(postList.get(position), newestTagList);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class HomeNewestPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemNewestPostBinding binding;
        HomeAttentionAdapter adapter = new HomeAttentionAdapter(mWidth);

        private Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                binding.tvUpdateData.setVisibility(View.GONE);
                return false;
            }
        });

        public HomeNewestPostViewHolder(ItemNewestPostBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            binding.getRoot().setOnClickListener(this);
            binding.imageHead.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", postList.get(getAdapterPosition()).getAuthor());
                ActivitySwitchUtil.switchActivity(UserPageActivity.class, bundle);
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.recyclerViewTags.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            this.binding.recyclerViewTags.setLayoutManager(layoutManager);
            binding.recyclerViewTags.setAdapter(adapter);

            adapter.setOnTagItemClickListener((newestTag, position, view) -> {
                        boolean follw = newestTag.isFollw();
                        adapter.nofiItem(follw, position);
                        if (onItemTagListener != null) {
                            onItemTagListener.onClick(newestTag);
                        }
                    }
            );

            binding.btnReccomTag.setOnClickListener(view -> {
                ActivitySwitchUtil.switchActivity(RecommendTagActivity.class);
            });
        }

        public void setData(Post post, List<NewestTag> newestTagList) {
            binding.setPost(post);

            if (isShow) {
                if (updateCount.equals("")) {
                    binding.tvUpdateData.setVisibility(View.GONE);
                } else {
                    binding.tvUpdateData.setVisibility(View.VISIBLE);
                    binding.tvUpdateData.setText(updateCount);
                    updateCount = "";
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                handler.sendMessage(new Message());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }else {
                binding.tvUpdateData.setVisibility(View.GONE);
            }


                if (getAdapterPosition() == 0) {
                    binding.recyclerViewTags.setVisibility(View.VISIBLE);
                    adapter.addData(newestTagList);
                } else {
                    binding.recyclerViewTags.setVisibility(View.GONE);
                }
            GlideUtils.imageLoaderXhdpi(binding.ivCoverImage, post.getCover().getImage_url());
            if (getAdapterPosition() == 0) {
                binding.btnReccomTag.setVisibility(View.VISIBLE);
            } else {
                binding.btnReccomTag.setVisibility(View.GONE);
            }
            if (adapter.getData().size() == 0) {
                binding.btnReccomTag.setVisibility(View.GONE);
            }

            if (getAdapterPosition() == 0) {
                binding.ivDataSelect.setVisibility(View.VISIBLE);
            } else {
                binding.ivDataSelect.setVisibility(View.GONE);
            }
            List<NewestTag> tags = post.getTags();
//            List<NewestTag> tagLists = new ArrayList<>();
//            for (int i = 0; i < tags.size(); i++) {
////                tagLists.add(tags.get(i).getName());
//            }
            TagAdapter<NewestTag> tagAdapter = new TagAdapter<NewestTag>(tags) {
                @Override
                public View getView(FlowLayout parent, int position, NewestTag s) {
                    TextView tv = (TextView) mActivity.getLayoutInflater().inflate(R.layout.item_hot_words_view,
                            binding.flowLayout, false);
                    tv.setText(s.getName());
                    return tv;
                }
            };
            binding.flowLayout.setAdapter(tagAdapter);
            binding.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("tag_id", tags.get(position).getId());
                    bundle.putString("tag_name", tags.get(position).getName());
                    ActivitySwitchUtil.switchActivity(TagDetailsActivity.class, bundle);
                    return true;
                }
            });

            if (post.getIsTime() == 1) {
                binding.tvDate.setVisibility(View.VISIBLE);
                binding.tvDate.setText(TimeFormatUtil.getDateYear(post.getCreated_at()));
            } else if (post.getIsTime() == 2) {
                binding.tvDate.setVisibility(View.VISIBLE);
                binding.tvDate.setText(TimeFormatUtil.getDateYear(post.getCreated_at()));
            } else if (post.getIsTime() == 5) {
                binding.tvDate.setVisibility(View.GONE);
                binding.viewItemDivider.setVisibility(View.VISIBLE);
            } else {
                binding.tvDate.setVisibility(View.GONE);
                binding.viewItemDivider.setVisibility(View.VISIBLE);
            }
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if (onItemPostClickListener != null) {
                onItemPostClickListener.onItemClick(postList.get(getAdapterPosition()));
            }
        }
    }


    public interface OnItemTagClickListener {
        void onClick(NewestTag newestTag);
    }

    public void setOnItemTagListener(OnItemTagClickListener onItemTagListener) {
        this.onItemTagListener = onItemTagListener;
    }


    public interface OnItemPostClickListener {
        void onItemClick(Post post);
    }

    public void setOnItemPostClickListener(OnItemPostClickListener onItemPostClickListener) {
        this.onItemPostClickListener = onItemPostClickListener;
    }


}
