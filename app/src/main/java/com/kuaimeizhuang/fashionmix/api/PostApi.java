package com.kuaimeizhuang.fashionmix.api;

import com.kuaimeizhuang.fashionmix.bean.CommentBody;
import com.kuaimeizhuang.fashionmix.bean.Comments;
import com.kuaimeizhuang.fashionmix.bean.Post;
import com.kuaimeizhuang.fashionmix.bean.UpDateVideoUrlBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;

import java.util.HashMap;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <p>帖子接口</p>
 * Created on 17/6/1.
 *
 * @author Shi GuangXiang.
 */

public interface PostApi {

    /**
     * 为帖子添加评论包括图片
     *
     * @param postId      帖子ID
     * @param content     评论内容
     * @param parentId    回复的评论 ID（@他人）
     * @param imageIdMap  图片 ID
     * @param imageDesMap 图片描述文字
     * @return
     */
    @FormUrlEncoded
    @POST("v1/posts/{post_id}/comments")
    Observable<HttpResult<CommentBody>> addComment(@Path("post_id") int postId, @Field("content") String content, @Field("parent_id") int parentId, @FieldMap() HashMap<String, String> imageIdMap, @FieldMap() HashMap<String, String> imageDesMap);


    /**
     * 获取指定的视频帖子
     *
     * @param post_id 帖子ID
     * @return 帖子详情
     */
    @GET("v1/posts/{post_id}")
    Observable<HttpResult<Post>> getVideoDetail(@Path("post_id") int post_id);

    /**
     * 删除评论
     *
     * @param postId     帖子ID
     * @param comment_id 评论ID
     * @return
     */
    @DELETE("v1/posts/{post_id}/comments/{comment_id}")
    Observable<HttpResult<Object>> delComment(@Path("post_id") int postId, @Path("comment_id") int comment_id);


    /**
     * 添加关注
     *
     * @param user_id          用户ID
     * @param followed_user_id 被关注的用户ID
     * @return 是否关注成功
     */
    @POST("v1/users/{user_id}/follow-users/{followed_user_id}")
    Observable<HttpResult<Object>> addFollowed(@Path("user_id") String user_id, @Path("followed_user_id") int followed_user_id);

    /**
     * 取消关注
     *
     * @param user_id          用户ID
     * @param followed_user_id 被关注的用户ID
     * @return 是否关注成功
     */
    @DELETE("v1/users/{user_id}/follow-users/{followed_user_id}")
    Observable<HttpResult<Object>> delFollowed(@Path("user_id") String user_id, @Path("followed_user_id") int followed_user_id);

    /**
     * 赞指定的帖子
     *
     * @param post_id 帖子ID
     * @return
     */
    @POST("v1/posts/{post_id}/likes")
    Observable<HttpResult<Object>> praisePostDetail(@Path("post_id") int post_id);


    /**
     * 获取帖子评论
     *
     * @param postId        帖子ID
     * @param page          页数，从 1 开始，默认值：1
     * @param lastCommentId 最新评论的 ID
     * @return
     */
    @GET("v1/posts/{post_id}/comments")
    Observable<HttpResult<Comments>> getPostComments(@Path("post_id") String postId, @Query("page") int page, @Query("last_comment_id") int lastCommentId);

    /**
     * 视频播放失败上报
     * @param postId
     * @return
     */
    @GET("v1/post/update-video-url/{post_id}")
    Observable<HttpResult<UpDateVideoUrlBean>> videoError(@Path("post_id") int postId);

}
