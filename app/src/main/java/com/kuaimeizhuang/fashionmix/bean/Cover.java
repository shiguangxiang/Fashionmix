package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

/**
 * <p>帖子封面</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Cover extends BaseBean {
    private String image_url;
    private int image_width;
    private int image_height;

    public int getImage_width() {
        return image_width;
    }

    public void setImage_width(int image_width) {
        this.image_width = image_width;
    }

    public int getImage_height() {
        return image_height;
    }

    public void setImage_height(int image_height) {
        this.image_height = image_height;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "Cover{" +
                "image_url='" + image_url + '\'' +
                '}';
    }
}
