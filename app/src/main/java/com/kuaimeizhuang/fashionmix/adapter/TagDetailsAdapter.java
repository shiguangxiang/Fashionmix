package com.kuaimeizhuang.fashionmix.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.NewestTag;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.Tag;
import com.kuaimeizhuang.fashionmix.databinding.ItemNewestPostBinding;
import com.kuaimeizhuang.fashionmix.databinding.ItemTagDetailsBinding;
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
 * Created on 17/5/25.
 *
 * @author Shi GuangXiang.
 */

public class TagDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEAD = 11;
    private static final int TYPE_ITEM = 12;
    private List<Post> postList = new ArrayList<>();
    private NewestTag newestTag = new NewestTag();
    private Activity mActivity;
    private String createAt;
    private OnBtnFollow onBtnFollow;

    public TagDetailsAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }


    //添加标签
    public void addTag(NewestTag newestTag) {
        this.newestTag = newestTag;
        notifyDataSetChanged();
    }

    /**
     * 添加帖子列表数据
     *
     * @param postList 帖子数据
     */
    public void addPostData(List<Post> postList, int index) {
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
                        index = i;
                        postList.get(index).setIsTime(2);
                        createAt = TimeFormatUtil.getDateYear(postList.get(index).getCreated_at());
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
                        index = i;
                    } else {
                        //取数据第 index 条的日期
                        String createdAt = TimeFormatUtil.getDateYear(postList.get(index).getCreated_at());
                        if (TextUtils.equals(createdAt, dateYear)) {
                            postList.get(index).setIsTime(2);
                        } else {
                            index = i;
                        }

                    }
                    LogUtils.e("IsTime---" + postList.get(i).getIsTime());
                }
            }

            this.postList.addAll(postList);
        }
        notifyDataSetChanged();
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
    public void replacePostData(List<Post> postList) {
        this.postList.clear();
        addPostData(postList, 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEAD) {
            ItemTagDetailsBinding binding = (ItemTagDetailsBinding) UiUtils.getDataBinding(inflater, parent, R.layout.item_tag_details);
            return new HeadViewHolder(binding);
        } else if (viewType == TYPE_ITEM) {
            ItemNewestPostBinding binding = (ItemNewestPostBinding) UiUtils.getDataBinding(inflater, R.layout.item_newest_post);
            return new ItemViewHolder(binding);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            ((HeadViewHolder) holder).setData(position);
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).setData(position);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size() > 0 ? postList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * 通知改变关注状态
     *
     * @param isFollow 是否关注
     */
    public void nofiFollowStatus(boolean isFollow) {
        newestTag.setFollw(isFollow);
        notifyItemChanged(0);
    }

    /**
     * 头部标签详情
     */
    class HeadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemTagDetailsBinding binding;

        public HeadViewHolder(ItemTagDetailsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.ivTagIsfollow.setOnClickListener(this);
        }

        public void setData(int position) {
            GlideUtils.imageLoader(binding.ivTagHead, newestTag.getCover());
            binding.tvTagName.setText(newestTag.getName());
            binding.tvVideoCount.setText(newestTag.getVideo_count() + "条");
            if (newestTag.isFollw()) {
                binding.ivTagIsfollow.setBackgroundResource(R.drawable.iv_tag_del_follow);
            } else {
                binding.ivTagIsfollow.setBackgroundResource(R.drawable.iv_tag_add_follow);
            }
        }

        @Override
        public void onClick(View view) {
            if (onBtnFollow != null) {
                onBtnFollow.onBtnFollow(newestTag, view);
            }
        }
    }

    public interface OnBtnFollow {
        void onBtnFollow(NewestTag newestTag, View view);
    }

    public void setOnBtnFollow(OnBtnFollow onBtnFollow) {
        this.onBtnFollow = onBtnFollow;
    }

    /**
     * 标签详情列表
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemNewestPostBinding binding;

        public ItemViewHolder(ItemNewestPostBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.imageHead.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", postList.get(getAdapterPosition()).getAuthor());
                ActivitySwitchUtil.switchActivity(UserPageActivity.class, bundle);
            });
        }

        public void setData(int position) {
            Post post = postList.get(position - 1);
            binding.setPost(post);
            GlideUtils.imageLoaderXhdpi(binding.ivCoverImage, post.getCover().getImage_url());
            if (getAdapterPosition() == 1) {
                binding.ivDataRecommened.setVisibility(View.VISIBLE);
            } else {
                binding.ivDataRecommened.setVisibility(View.GONE);
            }
            List<NewestTag> tags = post.getTags();
//            List<String> tagLists = new ArrayList<>();
//            for (int i = 0; i < tags.size(); i++) {
//                tagLists.add(tags.get(i).getName());
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

//            if (post.getIsTime() == 1) {
//                binding.tvDate.setVisibility(View.VISIBLE);
//                binding.tvDate.setText(TimeFormatUtil.getDateYear(post.getCreated_at()));
//            } else if (post.getIsTime() == 2) {
//                binding.tvDate.setVisibility(View.VISIBLE);
//                binding.tvDate.setText(TimeFormatUtil.getDateYear(post.getCreated_at()));
//            } else if (post.getIsTime() == 5) {
//                binding.tvDate.setVisibility(View.GONE);
//                binding.viewItemDivider.setVisibility(View.VISIBLE);
//            } else {
//                binding.tvDate.setVisibility(View.GONE);
//                binding.viewItemDivider.setVisibility(View.VISIBLE);
//            }
            binding.tvDate.setVisibility(View.GONE);
            binding.ivDataRecommened.setVisibility(View.GONE);
            binding.viewItemDivider.setVisibility(View.VISIBLE);

            binding.executePendingBindings();
        }
    }
}
