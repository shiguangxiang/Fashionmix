package com.kuaimeizhuang.fashionmix.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.User;
import com.kuaimeizhuang.fashionmix.databinding.ItemAttentionTagsBinding;
import com.kuaimeizhuang.fashionmix.utils.GlideUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>关注频道Adapter</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class AttentionChannelListAdapter extends RecyclerView.Adapter<AttentionChannelListAdapter.AttentionChannelViewHolder> {
    private List<User> userList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    //添加数据
    public void addData(List<User> userList) {
        this.userList.addAll(userList);
        this.notifyDataSetChanged();
    }

    /**
     * 替换数据
     *
     * @param userList 帖子数据
     */
    public void replacePostData(List<User> userList) {
        this.userList.clear();
        addData(userList);
    }

    @Override
    public AttentionChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAttentionTagsBinding binding = (ItemAttentionTagsBinding) UiUtils.getDataBinding(LayoutInflater.from(parent
                .getContext()), parent, R.layout.item_attention_tags);
        return new AttentionChannelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AttentionChannelViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class AttentionChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemAttentionTagsBinding binding;

        public AttentionChannelViewHolder(ItemAttentionTagsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(this);
        }

        /**
         * 设置数据
         *
         * @param position 位置
         */
        public void setData(int position) {
            User user = userList.get(position);
            binding.tvTagName.setText(user.getNickname());
            GlideUtils.imageLoader(binding.ivTagHead, user.getAvatar_url());
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemclick(userList.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemclick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
