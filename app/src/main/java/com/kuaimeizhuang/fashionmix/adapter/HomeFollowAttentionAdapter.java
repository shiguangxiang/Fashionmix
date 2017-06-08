package com.kuaimeizhuang.fashionmix.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuaimeizhuang.fashionmix.R;
import com.kuaimeizhuang.fashionmix.bean.NewestTag;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.Tag;
import com.kuaimeizhuang.fashionmix.databinding.ItemFollowPostBinding;
import com.kuaimeizhuang.fashionmix.databinding.ItemNewestTagsBinding;
import com.kuaimeizhuang.fashionmix.ui.activity.home.TagDetailsActivity;
import com.kuaimeizhuang.fashionmix.ui.activity.user.UserPageActivity;
import com.kuaimeizhuang.fashionmix.utils.ActivitySwitchUtil;
import com.kuaimeizhuang.fashionmix.utils.GlideUtils;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;
import com.kuaimeizhuang.fashionmix.utils.UiUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/5/24.
 *
 * @author Shi GuangXiang.
 */

public class HomeFollowAttentionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> postList = new ArrayList<>();
    private List<NewestTag> newestTagList = new ArrayList<>();

    private static final int TYPE_POST = 12;

    private int mWidth;
    private Activity mActivity;
    private String createAt;

    /**
     * 添加帖子列表数据
     *
     * @param postList 帖子数据
     */
    public void addPostData(List<Post> postList) {
        this.postList.addAll(postList);
        notifyDataSetChanged();
    }


    /**
     * 获取数据
     *
     * @return 数据
     */
    public List<Post> getPostList() {
        return postList;
    }

    /**
     * 替换数据
     *
     * @param postList 帖子数据
     */
    public void replacePostData(List<Post> postList) {
        this.postList.clear();
        addPostData(postList);
    }


    /**
     * 添加标签数据
     *
     * @param newestTagList 标签列表数据
     */
    public void addTagData(List<NewestTag> newestTagList) {
        if (newestTagList != null) {
            this.newestTagList.addAll(newestTagList);
        }
        notifyDataSetChanged();
    }

    public void replaceTagData(List<NewestTag> newestTagList) {
        this.newestTagList.clear();
        addTagData(newestTagList);
    }

    public HomeFollowAttentionAdapter(int mWidth, Activity activity) {
        this.mWidth = mWidth;
        this.mActivity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemFollowPostBinding binding = (ItemFollowPostBinding) UiUtils.getDataBinding
                (inflater, R.layout.item_follow_post);
        return new HomeFollowAttentionAdapter.FollowPostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FollowPostViewHolder) holder).setPostData();
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    /**
     * 帖子ViewHolder
     */
    class FollowPostViewHolder extends RecyclerView.ViewHolder {
        ItemFollowPostBinding binding;

        public FollowPostViewHolder(ItemFollowPostBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            binding.imageHead.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", postList.get(getAdapterPosition()).getAuthor());
                ActivitySwitchUtil.switchActivity(UserPageActivity.class, bundle);
            });
        }

        /**
         * 设置帖子数据
         */
        public void setPostData() {
            Post post = postList.get(getAdapterPosition());
            binding.setPost(post);
            GlideUtils.imageLoaderXhdpi(binding.ivCoverImage, post.getCover().getImage_url());
            List<NewestTag> tags = post.getTags();
//            List<String> tagLists = new ArrayList<>();
//            for (int i = 0; i < tags.size(); i++) {
//                tagLists.add(tags.get(i).getName());
//            }
            TagAdapter<NewestTag> tagAdapter = new TagAdapter<NewestTag>(tags) {
                @Override
                public View getView(FlowLayout parent, int position, NewestTag s) {
                    TextView tv = (TextView) mActivity.getLayoutInflater().inflate(R.layout.item_hot_words_view,
                            binding.flowLayout, false);
                    tv.setText(s.getName());
                    return tv;
                }
            };
            binding.flowLayout.setAdapter(tagAdapter);
            binding.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("tag_id", tags.get(position).getId());
                    bundle.putString("tag_name", tags.get(position).getName());
                    ActivitySwitchUtil.switchActivity(TagDetailsActivity.class, bundle);
                    return true;
                }
            });

            binding.executePendingBindings();
        }
    }

    /**
     * 标签Viewholder
     */
    class HomeNewestTagViewHolder extends RecyclerView.ViewHolder {
        ItemNewestTagsBinding binding;
        HomeAttentionAdapter adapter = new HomeAttentionAdapter(mWidth);

        public HomeNewestTagViewHolder(ItemNewestTagsBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.recyclerViewTags.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            this.binding.recyclerViewTags.setLayoutManager(layoutManager);
            binding.recyclerViewTags.setAdapter(adapter);
        }

        /**
         * 设置数据
         */
        public void setTagData() {
            adapter.addData(newestTagList);
        }
    }
}

