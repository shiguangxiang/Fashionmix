package com.kuaimeizhuang.fashionmix.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.NewestTag;
import com.kuaimeizhuang.fashionmix.databinding.ItemAttentionTagsBinding;
import com.kuaimeizhuang.fashionmix.utils.GlideUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/5/25.
 *
 * @author Shi GuangXiang.
 */

public class AttentionTagListAdapter extends RecyclerView.Adapter<AttentionTagListAdapter.AttentionTagViewHolder> {
    private List<NewestTag> newestTagList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    //添加数据
    public void addData(List<NewestTag> newestTagList) {
        this.newestTagList = newestTagList;
        this.notifyDataSetChanged();
    }

    @Override
    public AttentionTagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAttentionTagsBinding binding = (ItemAttentionTagsBinding) UiUtils.getDataBinding(LayoutInflater.from(parent
                .getContext()), parent, R.layout.item_attention_tags);
        return new AttentionTagViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AttentionTagViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return newestTagList.size();
    }

    class AttentionTagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemAttentionTagsBinding binding;

        public AttentionTagViewHolder(ItemAttentionTagsBinding itemView) {
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
            NewestTag newestTag = newestTagList.get(position);
            binding.tvTagName.setText(newestTag.getName());
            GlideUtils.imageLoader(binding.ivTagHead, newestTag.getCover());
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null){
                onItemClickListener.onItemclick(newestTagList.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener{
        void onItemclick(NewestTag newestTag);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
     this.onItemClickListener = onItemClickListener;
    }
}
