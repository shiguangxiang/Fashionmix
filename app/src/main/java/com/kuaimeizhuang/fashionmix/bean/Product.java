package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

/**
 * <p>注释类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Product extends BaseBean {
    private int id;
    private String title;
    private boolean has_favorited;
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isHas_favorited() {
        return has_favorited;
    }

    public void setHas_favorited(boolean has_favorited) {
        this.has_favorited = has_favorited;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Product{" +
                "category=" + category +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}

