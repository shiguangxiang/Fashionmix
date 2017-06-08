package com.kuaimeizhuang.fashionmix.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.databinding.ItemNewBinding;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

/**
 * <p>注释类</p>
 * Created on 17/6/6.
 *
 * @author Shi GuangXiang.
 */

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewViewHolder> {

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNewBinding binding = (ItemNewBinding) UiUtils.getDataBinding(LayoutInflater.from(parent.getContext()), R
                .layout
                .item_new);
        return new NewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(NewViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class NewViewHolder extends RecyclerView.ViewHolder {
        public NewViewHolder(ItemNewBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
