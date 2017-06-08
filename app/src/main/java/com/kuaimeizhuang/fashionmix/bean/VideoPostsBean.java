package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

class VideoPostsBean extends BaseBean {
    private boolean has_more_pages;
    private int per_page;
    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public boolean isHas_more_pages() {
        return has_more_pages;
    }

    public void setHas_more_pages(boolean has_more_pages) {
        this.has_more_pages = has_more_pages;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }
}
