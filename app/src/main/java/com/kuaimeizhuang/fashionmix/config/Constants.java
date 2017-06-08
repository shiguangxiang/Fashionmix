package com.kuaimeizhuang.fashionmix.config;

import com.kuaimeizhuang.fashionmix.BuildConfig;

/**
 * <p>配置信息常量类</p>
 * Created on 17/4/7.
 *
 * @author Shi GuangXiang.
 */

public interface Constants {
    /*升级提示标题*/
    String UPDATE_TITLE = "update_title";
    /*是否有新版本*/
    String HAS_NEW_VERSION = "has_new_version";
    /*升级地址*/
    String UPDATE_ADDRESS = "update_address";
    /*是否需要强制升级*/
    String NEED_FORCE_UPDATE = "need_force_update";
    /*升级更新说明*/
    String UPDATE_DES = "update_des";
    /*每天一次*/
    String IS_TODAY = "isToday";
    /**
     * 用户token
     */
    String KEY_IS_USER_TOKEN = "token";
    /**
     * 用户ID
     */
    String KEY_IS_USER_ID = "userID";
    /**
     * 用户性别
     */
    String KEY_IS_USER_GENDER = "gender";
    /**
     * 用户手机号码
     */
    String KEY_IS_USER_PHONE = "userPhone";

    /**
     * 用户昵称
     */
    String KEY_IS_USER_NICKNAME = "userNickName";

    /**
     * 用户登陆状态
     */
    String KEY_IS_USER_LOGIN = "is_login";
    /**
     * 根据存储的版本号判断用户是否是第一次登陆
     */
    String KEY_IS_FIRST_OPEN = "key_is_first_open";

    String SALT = "i2XtSy!bRQ%bxjb0";

    /*今天领取视频的时间*/
    String VIDEO_TODAY = "video_today";

    String UP = "up";
    String DOWN = "down";

    /**
     * 微博
     */
    interface weibo {
        String APP_KEY = "2957048708";   // 应用的APP_KEY
        String APP_SECRET = "039f076a4205c9d4aab79fc6f6f27e59";
        String REDIRECT_URL = "https://www.kuaimeizhuang.com";// 应用的回调页
        String SCOPE =                               // 应用申请的高级权限
                "email,direct_messages_read,direct_messages_write,"
                        + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                        + "follow_app_official_microblog," + "invitation_write";
    }

    interface LoginType {
        String QQ = "QQ";
        String WX = "WX";
        String WB = "WB";
        String MI = "MI";
    }

    interface BundleConstants {
        String ADDRESS = "address";
        String VIDEO_DETAIL = "video_detail";
        String USER = "user";
        String POST_ID = "post_id";
        String WEB_URL = "web_url";
        String WEB_TITLE = "web_title";
        String POST_DETAIL = "post_detail";
        String TAG = "tag";
        String CHILDREN = "children";
        String AUTHORHEAD = "authorHead";
        String AUTHORID = "authorID";
        String TRIAL = "trial";
        String ANSWER = "answer";
        String ORDER = "order";
        String TRADE_NO = "trade_no";
        String imagePath = "imagePath";
        String point = "point";
        String parentId = "parentId";
        String comment = "comment";
        String product = "product";
        String shareImage = "shareImage";
        String NEWPOST = "newPost";
        String Message = "messaget";
        String Search = "search";
    }
    interface QQ {
        String TencentAPP_ID = "1106063816";
        String buglyAPP_ID = "1103957156";
    }

    interface weixin {
        String APP_ID = "wx49004261dfdb8fd7";
        String AppSecret = "68f22cde625961c85b21d4189a4da8a6";
    }

    interface WIFI {
        /**
         * 存储网络状态
         */
        String NET_WORK_STATE = "network_state";
        /**
         * 无网络
         */
        int NETWORK_NONE = -1;
        /**
         * 移动网络3G/4G
         */
        int NETWORK_MOBILE = 0;
        /**
         * wifi
         */
        int NETWORK_ONLY_WIFI = 1;
        /**
         * 其他网络
         */
        int NETWORK_OHTER = 2;
    }

    interface share {
        String title = "快美搭，在这里你一定能学会穿衣搭配！";
        String titleUrl = "http://kuaimeizhuang.com/dest.html?fom=invite";
        String text = "潮流明星御用美妆班底强势进驻，邓紫棋、王珞丹、古天乐、林俊杰等大牌明星专用化妆造型师细致剖析妆容要点；亚洲一线美妆达人化身美妆po主，零距离互动美妆心得";
        String imageUrl = "http://www.kuaimeizhuang.com/images/FashionMix.png";
        String url = "http://kuaimeizhuang.com/dest.html?fom=invite";
        String site = "快美搭";
        String proShareUrlStart = "http://pro.m.kuaimeizhuang.com/v1/posts/";
        String devShareUrlStart = "http://snd.m.kuaimeizhuang.com/v1/posts/";
        String proShareFirend = "https://pro.m.kuaimeizhuang.com/v1/app/invite-friends.html?";
        String devShareFirend = "https://snd.m.kuaimeizhuang.com/v1/app/invite-friends.html?";
        String sharefriends = BuildConfig.DEBUG? devShareFirend : proShareFirend;
        String shareUrlStart = BuildConfig.DEBUG ? devShareUrlStart : proShareUrlStart;

        String shareUrlEnd = ".html";
    }
}

