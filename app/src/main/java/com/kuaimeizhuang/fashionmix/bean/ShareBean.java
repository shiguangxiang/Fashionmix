package com.kuaimeizhuang.fashionmix.bean;

/**
 * <p>注释类</p>
 * Created on 17/5/23.
 *
 * @author Shi GuangXiang.
 */

public class ShareBean {
    private String title;
    private int image;

    public ShareBean(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
