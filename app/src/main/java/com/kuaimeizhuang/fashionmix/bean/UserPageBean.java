package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>用户个人主页</p>
 * Created on 17/6/3.
 *
 * @author Shi GuangXiang.
 */

public class UserPageBean extends BaseBean {

    private int per_page;
    private boolean has_page;
    private List<Post> Posts;

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

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