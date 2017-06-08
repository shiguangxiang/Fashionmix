package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>帖子列表</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class PostList extends BaseBean {
    private boolean has_page;
    private List<Post> Posts;

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
}
