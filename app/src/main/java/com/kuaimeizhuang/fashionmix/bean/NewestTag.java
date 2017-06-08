package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

/**
 * <p>首页最新tab页面标签</p>
 * Created on 17/5/18.
 *
 * @author Shi GuangXiang.
 */

public class NewestTag extends BaseBean {
    private int id;
    private String name;
    private String button_color;
    private String cover;
    private boolean is_follow;
    private int video_count;

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public boolean isFollw() {
        return is_follow;
    }

    public void setFollw(boolean follw) {
        is_follow = follw;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getButton_color() {
        return button_color;
    }

    public void setButton_color(String button_color) {
        this.button_color = button_color;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "NewestTag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", button_color='" + button_color + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
