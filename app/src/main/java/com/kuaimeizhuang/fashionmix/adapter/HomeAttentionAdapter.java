package com.kuaimeizhuang.fashionmix.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.AttentionBean;
import com.kuaimeizhuang.fashionmix.bean.NewestTag;
import com.kuaimeizhuang.fashionmix.databinding.ItemHomeAttentionBinding;
import com.kuaimeizhuang.fashionmix.ui.activity.home.TagDetailsActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>首页最新页面关注列表</p>
 * Created on 17/5/18.
 *
 * @author Shi GuangXiang.
 */

public class HomeAttentionAdapter extends RecyclerView.Adapter<HomeAttentionAdapter.HomeAttentionViewHolder> {

    private int mWidth;
    private List<NewestTag> newestTagList = new ArrayList<>();
    private OnItemTagClickListener onItemTagClickListener;
    private OnItemClickListener onItemClickListener;

    public HomeAttentionAdapter(int mWidth) {
        this.mWidth = mWidth;
    }

    public void addData(List<NewestTag> datas) {
        if (datas != null) {
            newestTagList.clear();
            newestTagList.addAll(datas);
        }
        notifyDataSetChanged();
        LogUtils.d("数据长度--" + newestTagList.size());
    }

    public List<NewestTag> getData(){
     return newestTagList;
    }

    @Override
    public HomeAttentionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHomeAttentionBinding binding = (ItemHomeAttentionBinding) UiUtils.getDataBinding(LayoutInflater.from(parent
                .getContext()), parent, R.layout.item_home_attention);
        return new HomeAttentionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(HomeAttentionViewHolder holder, int position) {
        int width = mWidth / 3 - UiUtils.dip2px(20);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        holder.binding.rlRoot.setLayoutParams(params);
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return newestTagList.size();
    }

    class HomeAttentionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemHomeAttentionBinding binding;

        public HomeAttentionViewHolder(ItemHomeAttentionBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            binding.itemViewTag.setOnClickListener(this);
            binding.llRoot.setOnClickListener(this);
        }

        public void setData(int position) {
            NewestTag newestTag = newestTagList.get(position);
            binding.setTag(newestTag);
            if (newestTag.isFollw()) {
                binding.itemViewTag.setBackgroundResource(R.drawable.follw_ok);
            } else {
                binding.itemViewTag.setBackgroundResource(R.drawable.follw_add);
            }
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.item_view_tag){
                if (onItemTagClickListener != null){
                    onItemTagClickListener.onItemClick(newestTagList.get(getAdapterPosition()),
                            getAdapterPosition(),view);
                }
            }else if (view.getId() == R.id.ll_root){
                Bundle bundle = new Bundle();
                bundle.putInt("tag_id",newestTagList.get(getAdapterPosition()).getId());
                bundle.putString("tag_name",newestTagList.get(getAdapterPosition()).getName());
                ActivitySwitchUtil.switchActivity(TagDetailsActivity.class,bundle);
            }
        }
    }

    public interface OnItemTagClickListener {
        void onItemClick(NewestTag newestTag, int position,View view);
    }

    public void setOnTagItemClickListener(OnItemTagClickListener onItemTagClickListener) {
        this.onItemTagClickListener = onItemTagClickListener;
    }

    public void nofiItem(boolean isFollw, int position) {
        NewestTag newestTag = newestTagList.get(position);
        newestTagList.remove(newestTag);
        notifyItemRemoved(position);
        LogUtils.d(newestTagList.size() + "");
    }

    public interface OnItemClickListener {
        void onItemClick(NewestTag newestTag, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
