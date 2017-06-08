package com.kuaimeizhuang.fashionmix.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.ShareBean;
import com.kuaimeizhuang.fashionmix.databinding.ItemShareBinding;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;
import com.kuaimeizhuang.fashionmix.view.ShareDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>分享adapter</p>
 * Created on 17/5/23.
 *
 * @author Shi GuangXiang.
 */

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ShareViewHodler> {

    private List<ShareBean> shareBeanList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void addData(List<ShareBean> shareBeanList){
        this.shareBeanList.clear();
        if (shareBeanList != null) {
            this.shareBeanList.addAll(shareBeanList);
        }
        this.notifyDataSetChanged();
    }
    @Override
    public ShareViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemShareBinding binding = (ItemShareBinding) UiUtils.getDataBinding(inflater, parent,R
                .layout.item_share);
        return new ShareViewHodler(binding);
    }

    @Override
    public void onBindViewHolder(ShareViewHodler holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return shareBeanList.size();
    }

    class ShareViewHodler extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemShareBinding binding;
        public ShareViewHodler(ItemShareBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(this);
        }

        public void setData(int position) {
            ShareBean shareBean = shareBeanList.get(position);
            binding.ivShareImage.setBackgroundResource(shareBean.getImage());
            binding.tvShareName.setText(shareBean.getTitle());
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null){
                ShareBean shareBean = shareBeanList.get(getAdapterPosition());
                onItemClickListener.onItemClick(shareBean.getTitle());
            }
        }
    }
    public interface OnItemClickListener{
        void onItemClick(String title);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
