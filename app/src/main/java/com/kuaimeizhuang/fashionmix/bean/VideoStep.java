package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class VideoStep extends BaseBean {
    private String title;
    private int start_time;
    private List<Product> products;
    private boolean isLastItem;

    public boolean isLastItem() {
        return isLastItem;
    }

    public void setLastItem(boolean lastItem) {
        isLastItem = lastItem;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "VideoStep{" +
                "products=" + products +
                ", title='" + title + '\'' +
                ", start_time=" + start_time +
                '}';
    }
}
