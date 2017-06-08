package com.kuaimeizhuang.fashionmix.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.NewestTag;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.Tag;
import com.kuaimeizhuang.fashionmix.bean.User;
import com.kuaimeizhuang.fashionmix.databinding.ItemNewestPostBinding;
import com.kuaimeizhuang.fashionmix.ui.activity.home.TagDetailsActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.user.UserPageActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.GlideUtils;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.SPUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>热榜Adapter</p>
 * Created on 17/5/26.
 *
 * @author Shi GuangXiang.
 */

public class HotListAdapter extends RecyclerView.Adapter<HotListAdapter.HotListViewHolder> {
    private List<Post> postList = new ArrayList<>();
    private Activity mActivity;
    private boolean isHotList = false;
    private OnItemClickListener onItemClickListener;

    public HotListAdapter(Activity mActivity) {
        this(mActivity, false);
    }

    public HotListAdapter(Activity mActivity, boolean isHotList) {
        this.mActivity = mActivity;
        this.isHotList = isHotList;

    }

    /**
     * 添加数据
     *
     * @param postList 帖子数据
     */
    public void addPostData(List<Post> postList) {
        if (postList != null) {
            this.postList.addAll(postList);
        }
        LogUtils.d(postList.size()+"------adapter");
        notifyDataSetChanged();
    }

    /**
     * 替换数据
     *
     * @param postList 帖子数据
     */
    public void replacePostData(List<Post> postList) {
        if (postList != null) {
            this.postList.clear();
            addPostData(postList);
        }
    }


    @Override
    public HotListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNewestPostBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent
                .getContext()),R.layout.item_newest_post,parent,false);
        return new HotListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(HotListViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public List<Post> getData() {
        return postList;
    }

    class HotListViewHolder extends RecyclerView.ViewHolder {
        ItemNewestPostBinding binding;

        public HotListViewHolder(ItemNewestPostBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            binding.getRoot().setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(postList.get(getAdapterPosition()));
                }
            });

            binding.imageHead.setOnClickListener(view -> {
                SPUtils.setBoolean("isHide", false);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", postList.get(getAdapterPosition()).getAuthor());
                ActivitySwitchUtil.switchActivity(UserPageActivity.class, bundle);
            });
        }

        /**
         * 设置数据
         *
         * @param position 位置
         */
        public void setData(int position) {
            Post post = postList.get(position);
            binding.setPost(post);
            if (isHotList) {
                binding.ivHotLevel.setVisibility(View.VISIBLE);
                setLevel(position);
            } else {
                binding.ivHotLevel.setVisibility(View.GONE);
            }


            GlideUtils.imageLoaderXhdpi(binding.ivCoverImage, post.getCover().getImage_url());
            binding.tvDate.setVisibility(View.GONE);
            binding.ivDataRecommened.setVisibility(View.GONE);
            binding.viewItemDivider.setVisibility(View.VISIBLE);
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
            binding.executePendingBindings();
        }

        /**
         * 设置星标
         *
         * @param position 位置
         */
        private void setLevel(int position) {
            switch (position) {
                case 0:
                    binding.ivHotLevel.setBackgroundResource(R.drawable.iv_hot_one);
                    break;
                case 1:
                    binding.ivHotLevel.setBackgroundResource(R.drawable.iv_hot_two);
                    break;
                case 2:
                    binding.ivHotLevel.setBackgroundResource(R.drawable.iv_hot_three);
                    break;
                case 3:
                    binding.ivHotLevel.setBackgroundResource(R.drawable.iv_hot_four);
                    break;
                case 4:
                    binding.ivHotLevel.setBackgroundResource(R.drawable.iv_hot_five);
                    break;
                case 5:
                    binding.ivHotLevel.setBackgroundResource(R.drawable.iv_hot_six);
                    break;

                case 6:
                    binding.ivHotLevel.setBackgroundResource(R.drawable.iv_hot_seven);
                    break;
                case 7:
                    binding.ivHotLevel.setBackgroundResource(R.drawable.iv_hot_eight);
                    break;

                case 8:
                    binding.ivHotLevel.setBackgroundResource(R.drawable.iv_hot_nine);
                    break;

                case 9:
                    binding.ivHotLevel.setBackgroundResource(R.drawable.iv_hot_ten);
                    break;

                default:
                    binding.ivHotLevel.setBackgroundResource(0);
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(Post post);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
