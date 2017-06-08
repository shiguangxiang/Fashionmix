package com.kuaimeizhuang.fashionmix.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.Comment;
import com.kuaimeizhuang.fashionmix.config.PageManager;
import com.kuaimeizhuang.fashionmix.databinding.ItemVideoCommentBinding;
import com.kuaimeizhuang.fashionmix.model.home.VideoPlayDetailsViewModel;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>评论adapter</p>
 * Created on 17/6/1.
 *
 * @author Shi GuangXiang.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList = new ArrayList<>();
    private OnDelCommentClick onDelCommentClick;
    private VideoPlayDetailsViewModel viewModel;

    public CommentAdapter(VideoPlayDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void addData(List<Comment> comments) {
        if (comments != null) {
            commentList.addAll(comments);
        }
        LogUtils.d(commentList.size() + "条");
        notifyDataSetChanged();
    }

    public void addSingleData(Comment comment) {
        if (comment != null) {
            commentList.add(0, comment);
        }
        notifyDataSetChanged();
    }

    public void delSingleData(Comment comment) {
        if (comment != null) {
            commentList.remove(comment);
        }
        notifyDataSetChanged();
    }

    public List<Comment> getData() {
        return commentList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVideoCommentBinding binding = (ItemVideoCommentBinding) UiUtils.getDataBinding(LayoutInflater.from(parent
                .getContext()), parent, R.layout.item_video_comment);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.setData(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemVideoCommentBinding binding;

        public CommentViewHolder(ItemVideoCommentBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            binding.ivDelComment.setOnClickListener(this);
        }

        public void setData(Comment comment) {
            binding.setComment(comment);
            binding.ivDelComment.setTag(comment);
            boolean allowDeleting = comment.getRights().isAllow_deleting();
            if (allowDeleting){
                binding.ivDelComment.setVisibility(View.VISIBLE);
            }else {
                binding.ivDelComment.setVisibility(View.GONE);
            }

            List<Comment> childrenComments = comment.getChildren_comments();
            CommentChildAdapter adapter = new CommentChildAdapter(viewModel);
            binding.recyclerCommentChild.setAdapter(adapter);
            if (childrenComments != null && childrenComments.size() > 0) {
                binding.recyclerCommentChild.setLayoutManager(new LinearLayoutManager(PageManager.getCurrentActivity()));
                adapter.addData(childrenComments);
                adapter.setParentComment(comment);
            }
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if (onDelCommentClick != null) {
                onDelCommentClick.onDelComment(commentList.get(getAdapterPosition()));
            }
        }
    }

    public interface OnDelCommentClick {
        void onDelComment(Comment comment);
    }

    public void setOnDelCommentClick(OnDelCommentClick onDelCommentClick) {
        this.onDelCommentClick = onDelCommentClick;
    }
}
