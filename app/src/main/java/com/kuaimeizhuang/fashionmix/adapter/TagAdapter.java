package com.kuaimeizhuang.fashionmix.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.NewestTag;
import com.kuaimeizhuang.fashionmix.bean.Tag;
import com.kuaimeizhuang.fashionmix.databinding.ItemVideoDetailsTagBinding;
import com.kuaimeizhuang.fashionmix.ui.activity.home.TagDetailsActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>视频详情页面的标签</p>
 * Created on 17/6/5.
 *
 * @author Shi GuangXiang.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {
    private List<NewestTag> tagList = new ArrayList<>();

    public void addDatas(List<NewestTag> tagList) {
        if (tagList != null) {
            this.tagList.addAll(tagList);
        }
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVideoDetailsTagBinding binding = (ItemVideoDetailsTagBinding) UiUtils.getDataBinding(LayoutInflater.from(parent
                .getContext()), R.layout.item_video_details_tag);
        return new TagViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(TagViewHolder holder, int position) {
        holder.setData(tagList.get(position));
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    class TagViewHolder extends RecyclerView.ViewHolder {
        ItemVideoDetailsTagBinding binding;

        public TagViewHolder(ItemVideoDetailsTagBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            binding.tvTagName.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt("tag_id", tagList.get(getAdapterPosition()).getId());
                bundle.putString("tag_name", tagList.get(getAdapterPosition()).getName());
                ActivitySwitchUtil.switchActivity(TagDetailsActivity.class, bundle);
            });
        }

        public void setData(NewestTag tag) {
            binding.tvTagName.setText(tag.getName());
            binding.executePendingBindings();
        }
    }
}
