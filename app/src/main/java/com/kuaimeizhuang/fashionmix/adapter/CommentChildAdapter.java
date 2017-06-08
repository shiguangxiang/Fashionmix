package com.kuaimeizhuang.fashionmix.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.Comment;
import com.kuaimeizhuang.fashionmix.databinding.ItemVideoChildCommentBinding;
import com.kuaimeizhuang.fashionmix.model.home.VideoPlayDetailsViewModel;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>子评论</p>
 * Created on 17/6/1.
 *
 * @author Shi GuangXiang.
 */

public class CommentChildAdapter extends RecyclerView.Adapter<CommentChildAdapter.CommentChildViewHolder> {

    private List<Comment> data = new ArrayList<>();
    private Comment mParentComment;
    private VideoPlayDetailsViewModel viewModel;

    public CommentChildAdapter(VideoPlayDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setParentComment(Comment parentComment) {
        mParentComment = parentComment;
    }

    public List<Comment> getData() {
        return data;
    }

    public void addSingleData(Comment comment) {
        if (comment != null) {
            data.add(0, comment);
        }
        notifyDataSetChanged();
    }

    public void delSingleData(Comment comment) {
        if (comment != null) {
            data.remove(comment);
        }
        notifyDataSetChanged();
    }

    public void addData(List<Comment> data) {
        if (data != null) {
            this.data.addAll(data);
        }
        LogUtils.e("adapter : " + " size: " + this.data.size());
        notifyDataSetChanged();
    }

    @Override
    public CommentChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVideoChildCommentBinding binding = (ItemVideoChildCommentBinding) UiUtils.getDataBinding(LayoutInflater.from(parent
                .getContext
                        ()), parent, R.layout
                .item_video_child_comment);
        return new CommentChildViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CommentChildViewHolder holder, int position) {
        holder.setData(data.get(position),mParentComment);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommentChildViewHolder extends RecyclerView.ViewHolder {
        ItemVideoChildCommentBinding binding;
        public CommentChildViewHolder(ItemVideoChildCommentBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void setData(Comment comment, Comment mParentComment) {
            binding.setViewModel(viewModel);
            binding.llChildCommentItem.setTag(comment);
            Context context = binding.llChildCommentItem.getContext();
            SpannableString tvComment = getSpannableString(comment, context, mParentComment);
            binding.tvComment.setText(tvComment);
            binding.executePendingBindings();
        }

        /**
         * 获取子评论变色等文本
         *
         * @param comment
         * @param context
         * @param parentComment
         * @return
         */
        @NonNull
        private SpannableString getSpannableString(Comment comment, Context context, Comment parentComment) {
            String nickname = comment.getAuthor().getNickname();
            String pNickname = comment.getParent_author().getNickname();
            String content = comment.getContent();
            int flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
            SpannableString ss;
            if (parentComment.getId() == comment.getParent_id()) {
                String childComment = binding.llChildCommentItem.getContext().getString(R.string
                        .child_comment_normal, nickname, content);
                ss = new SpannableString(childComment);
                ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red_tao)), 0, nickname.length(), flags);
                ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.kmz_575757)), nickname.length(), childComment.length(), flags);
            } else {
                String childComment = binding.llChildCommentItem.getContext().getString(R.string.child_comment
                        , nickname
                        , pNickname
                        , content);
                ss = new SpannableString(childComment);
                ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red_tao)), 0, nickname.length(), flags);
                ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.kmz_575757)), nickname.length(), nickname.length() + 3, flags);
                ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red_tao)), nickname.length() + 3, nickname.length() + 5 + pNickname.length(), flags);
                ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.kmz_575757)), nickname.length() + 5 + pNickname.length(), childComment.length(), flags);
            }
            return ss;
        }
    }




}
