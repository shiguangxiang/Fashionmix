package com.kuaimeizhuang.fashionmix.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.ItemRecomedBean;
import com.kuaimeizhuang.fashionmix.databinding.ItemRecomedTagBinding;
import com.kuaimeizhuang.fashionmix.model.home.RecommendTagViewModel;
import com.kuaimeizhuang.fashionmix.ui.activity.account.LoginActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.GlideUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>推荐标签</p>
 * Created on 17/6/5.
 *
 * @author Shi GuangXiang.
 */

public class RecomenedTagAdapter extends RecyclerView.Adapter<RecomenedTagAdapter.RecomenedTagViewHolder> {
    private List<ItemRecomedBean> datas = new ArrayList<>();
    private int mWidth;
    private RecommendTagViewModel viewModel;

    public RecomenedTagAdapter(RecommendTagViewModel viewModel, int mWidth) {
        this.mWidth = mWidth;
        this.viewModel = viewModel;
    }

    public void addDatas(List<ItemRecomedBean> dataList) {
        if (dataList != null) {
            this.datas.addAll(dataList);
        }
    }

    @Override
    public RecomenedTagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecomedTagBinding binding = (ItemRecomedTagBinding) UiUtils.getDataBinding(LayoutInflater.from(parent
                .getContext()), parent, R.layout.item_recomed_tag);
        return new RecomenedTagViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecomenedTagViewHolder holder, int position) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mWidth * 223 / 62, mWidth *
                23 / 323);
        holder.binding.ivStyle.setLayoutParams(lp);
        holder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class RecomenedTagViewHolder extends RecyclerView.ViewHolder {
        ItemRecomedTagBinding binding;
        HomeAttentionAdapter adapter = new HomeAttentionAdapter(mWidth);

        public RecomenedTagViewHolder(ItemRecomedTagBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.itemRecycler.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            binding.itemRecycler.setLayoutManager(layoutManager);
            binding.itemRecycler.setAdapter(adapter);

            adapter.setOnTagItemClickListener((newestTag, position, view) -> {
                if (!UiUtils.getToken().equals("")) {
                    if (newestTag.isFollw()) {
                        viewModel.delFollowTag(newestTag, view);
                    } else {
                        viewModel.addFollowTag(newestTag, view);
                    }
                } else {
                    ActivitySwitchUtil.switchActivity(LoginActivity.class);
                }
            });
        }


        public void setData(ItemRecomedBean itemRecomedBean) {
            adapter.addData(itemRecomedBean.getTags());
            GlideUtils.glideFitCenter(binding.ivStyle, itemRecomedBean.getCover());
//            if (itemRecomedBean.getName().equals("身材")) {
//                binding.ivStyle.setBackgroundResource(R.drawable.iv_style_sc);
//            } else if (itemRecomedBean.getName().equals("流行")) {
//                binding.ivStyle.setBackgroundResource(R.drawable.iv_style_lx);
//            } else if (itemRecomedBean.getName().equals("风格")) {
//                binding.ivStyle.setBackgroundResource(R.drawable.iv_style_fengge);
//            } else if (itemRecomedBean.getName().equals("场景")) {
//                binding.ivStyle.setBackgroundResource(R.drawable.iv_style_cj);
//            } else if (itemRecomedBean.getName().equals("季节")) {
//                binding.ivStyle.setBackgroundResource(R.drawable.iv_style_jj);
//            } else {
//                binding.ivStyle.setBackgroundResource(R.drawable.iv_style_dp);
//            }
        }
    }
}
