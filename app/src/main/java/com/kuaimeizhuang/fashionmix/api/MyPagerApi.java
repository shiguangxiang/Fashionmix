package com.kuaimeizhuang.fashionmix.api;

import com.kuaimeizhuang.fashionmix.bean.MasterPrefectureListBean;
import com.kuaimeizhuang.fashionmix.bean.PostList;
import com.kuaimeizhuang.fashionmix.bean.TagListBean;
import com.kuaimeizhuang.fashionmix.bean.UserHomepageBean;
import com.kuaimeizhuang.fashionmix.bean.UserNewsBean;
import com.kuaimeizhuang.fashionmix.bean.UserPageBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <p>我的页面网络接口</p>
 * Created on 17/5/25.
 *
 * @author Shi GuangXiang.
 */

public interface MyPagerApi {
    /**
     * 获取关注标签列表
     *
     * @return 数据
     */
    @GET("v1/user/follow-tags-lists")
    Observable<HttpResult<TagListBean>> getAttentionTagsData();

    /**
     * 我的消息列表
     *
     * @param user_id  用户ID
     * @param filter   帖子类型，枚举值：all (默认), reply, like, fans, notification
     * @param page     页数，从 1 开始，默认值：1
     * @param per_page 每页最大记录数，默认值：10
     * @return
     */
    @GET("v1/users/{user_id}/messages/{filter}")
    Observable<HttpResult<UserNewsBean>> userNews(@Path("user_id") String user_id, @Path("filter") String filter, @Query("page") int page, @Query("per_page") int per_page);


    /**
     * 我的关注人列表
     *
     * @param user_id  用户 ID
     * @param page     页数，从 1 开始，默认值：1
     * @param per_page 每页最大记录数，默认值：10
     * @return
     */
    @GET("v1/users/{user_id}/follow-users")
    Observable<HttpResult<MasterPrefectureListBean>> userFollowsList(@Path("user_id") String user_id, @Query("page") int page, @Query("per_page") int per_page);

    /**
     * 我的喜欢列表
     * per_page	否	integer	每页最大记录数，默认值：10
     * post_id	否	integer	帖子ID
     * load	    否	string	加载方式(上拉填up 下拉填down)
     * sequence	否	string	发布时间
     */
    @GET("v1/posts-like-lists")
    Observable<HttpResult<PostList>> getLikePostDatas(@Query("per_page") int per_page, @Query
            ("post_id") int post_id, @Query("load") String load, @Query("sequence") String sequence);

    /**
     * 获取用户主页或者达人主页数据
     *
     * @param user_id 用户ID
     * @return
     */
    @GET("v1/users/{user_id}/home")
    Observable<HttpResult<UserHomepageBean>> userHomepage(@Path("user_id") String user_id);

    /**
     * 用户个人主页帖子和问答列表
     *
     * @param user_id  用户id
     * @param type     类型
     * @return
     */
    @GET("v1/users/{user_id}/posts/{type}-posts")
    Observable<HttpResult<UserPageBean>> getUserPostList(@Path("user_id") String user_id, @Path("type")
            String type, @Query("post_id") int postId, @Query("load") String load, @Query("sequence") String sequence);

    /**
     * 用户提交反馈
     *
     * @param user_id 用户ID
     * @param qq      用户QQ
     * @param phone   用户手机
     * @param content 反馈内容
     * @return 是否反馈成功
     */
    @FormUrlEncoded
    @POST("v1/users/{user_id}/feedback")
    Observable<HttpResult<Object>> userGiveFeedback(@Path("user_id") String user_id, @Field("qq") String qq, @Field("phone_number") String phone, @Field("content") String content);
}
