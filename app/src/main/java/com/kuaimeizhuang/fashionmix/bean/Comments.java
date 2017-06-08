package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Comments extends BaseBean {
    private int per_page;
    private boolean has_more_pages;
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean getHas_more_pages() {
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

    @Override
    public String toString() {
        return "Comments{" +
                "comments=" + comments +
                ", per_page=" + per_page +
                ", has_more_pages='" + has_more_pages + '\'' +
                '}';
    }
}

