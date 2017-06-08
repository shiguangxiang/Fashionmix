package com.kuaimeizhuang.fashionmix.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.Message;
import com.kuaimeizhuang.fashionmix.databinding.ItemMyCommentBinding;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>我的消息 评论adapter</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.MyCommentViewHolder> {
    private List<Message> data = new ArrayList<>();

    public void addData(List<Message> data) {
        if (data != null) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void replaceData(List<Message> data) {
        if (data != null) {
            this.data.clear();
            addData(data);
        }
    }

    public List<Message> getData(){
        return data;
    }

    @Override
    public MyCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMyCommentBinding binding = (ItemMyCommentBinding) UiUtils.getDataBinding(LayoutInflater.from(parent.getContext
                ()), parent, R.layout.item_my_comment);
        return new MyCommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyCommentViewHolder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyCommentViewHolder extends RecyclerView.ViewHolder {
        ItemMyCommentBinding binding;

        public MyCommentViewHolder(ItemMyCommentBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        /**
         * 设置数据
         * @param message message
         */
        public void setData(Message message) {
            binding.setMessage(message);
            binding.executePendingBindings();
        }
    }
}
