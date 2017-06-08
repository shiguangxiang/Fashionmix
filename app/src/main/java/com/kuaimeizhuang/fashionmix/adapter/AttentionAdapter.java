package com.kuaimeizhuang.fashionmix.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.AttentionBean;
import com.kuaimeizhuang.fashionmix.bean.NewestTag;
import com.kuaimeizhuang.fashionmix.databinding.ItemAttentionBinding;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>首页 关注页面</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class AttentionAdapter extends RecyclerView.Adapter<AttentionAdapter.AttentionViewHolder> {
    private List<NewestTag> attentionBeanList = new ArrayList<>();
    private int mWidth;
    private OnItemClickListener onItemClickListener;

    public AttentionAdapter(int mWidth) {
        this.mWidth = mWidth;
    }

    public void addData(List<NewestTag> datas) {
        if (datas != null) {
            attentionBeanList.clear();
            attentionBeanList.addAll(datas);
        }
        notifyDataSetChanged();
        LogUtils.d("数据长度--" + attentionBeanList.size());
    }

    @Override
    public AttentionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAttentionBinding binding = (ItemAttentionBinding) UiUtils.getDataBinding(layoutInflater, R.layout.item_attention);
        return new AttentionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AttentionViewHolder holder, int position) {
        int width = mWidth / 3 - UiUtils.dip2px(30);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
        holder.binding.rlRoot.setLayoutParams(params);
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return attentionBeanList.size();
    }

    class AttentionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemAttentionBinding binding;

        public AttentionViewHolder(ItemAttentionBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.ccView.setOnClickListener(this);
        }

        /**
         * 设置数据
         * @param position 位置
         */
        public void setData(int position) {
            NewestTag newestTag = attentionBeanList.get(position);
            binding.setAttention(newestTag);
            if (newestTag.isFollw()){
                binding.ccView.setBackgroundResource(R.drawable.follw_ok);
            }else {
                binding.ccView.setBackgroundResource(R.drawable.follw_add);
            }
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(attentionBeanList.get(getAdapterPosition()),getAdapterPosition());
            }
        }
    }
    public interface OnItemClickListener{
        void onItemClick(NewestTag newestTag,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void nofiItem(boolean isFollw,int position){
        NewestTag newestTag = attentionBeanList.get(position);
        newestTag.setFollw(isFollw);
        notifyItemChanged(position);
    }
}
