package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>标签详情实体</p>
 * Created on 17/5/25.
 *
 * @author Shi GuangXiang.
 */

public class TagDetailsBean extends BaseBean {
    private boolean has_page;
    private List<Post> Posts;
    private NewestTag Tag;

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

    public NewestTag getTag() {
        return Tag;
    }

    public void setTag(NewestTag tag) {
        Tag = tag;
    }
}
