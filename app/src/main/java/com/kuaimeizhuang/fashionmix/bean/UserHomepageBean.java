package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

/**
 * <p>个人主页</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class UserHomepageBean extends BaseBean{
    private User user;
    private VideoPostsBean video_posts;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VideoPostsBean getVideo_posts() {
        return video_posts;
    }

    public void setVideo_posts(VideoPostsBean video_posts) {
        this.video_posts = video_posts;
    }
}
