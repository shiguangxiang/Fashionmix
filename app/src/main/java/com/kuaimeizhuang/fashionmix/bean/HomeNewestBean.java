package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>首页最新页面数据</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class HomeNewestBean extends BaseBean {
    private boolean has_page;
    private List<Post> Posts;
    private List<NewestTag> Tags;

    public boolean isHas_page() {
        return has_page;
    }

    public void setHas_page(boolean has_page) {
        this.has_page = has_page;
    }

    public List<Post> getPosts() {
        return Posts;
    }

    public void setPosts(List<Post> posts) {
        Posts = posts;
    }

    public List<NewestTag> getTags() {
        return Tags;
    }

    public void setTags(List<NewestTag> tags) {
        Tags = tags;
    }

    @Override
    public String toString() {
        return "HomeNewestBean{" +
                "has_page=" + has_page +
                ", Posts=" + Posts +
                ", Tags=" + Tags +
                '}';
    }
}
