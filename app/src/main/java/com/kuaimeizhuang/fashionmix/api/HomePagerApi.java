package com.kuaimeizhuang.fashionmix.api;

import com.kuaimeizhuang.fashionmix.bean.HomeNewestBean;
import com.kuaimeizhuang.fashionmix.bean.RecomedTagBean;
import com.kuaimeizhuang.fashionmix.bean.TagDetailsBean;
import com.kuaimeizhuang.fashionmix.bean.Tags;
import com.kuaimeizhuang.fashionmix.bean.UserBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <p>首页的网络请求接口</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public interface HomePagerApi {

    /**
     * 获取首页 最新标签数据
     *
     * @param post_id 帖子ID
     * @param load    加载方式(上拉填up 下拉填down)
     * @return 最新标签数据
     */
    @GET("v1/home")
    Observable<HttpResult<HomeNewestBean>> getHomeNewestData(
            @Query("post_id") int post_id
            , @Query("load") String load
            , @Query("sequence") String sequence);

    /**
     * 获取关注的标签
     *
     * @return 标签数据
     */
    @GET("v1/tags/recommend")
    Observable<HttpResult<Tags>> getTagRecommened();

    /**
     * 获取用户信息
     *
     * @return 信息
     */
    @GET("v1/users/user-info")
    Observable<HttpResult<UserBean>> getUserInfo();

    /**
     * 关注标签
     *
     * @param tags 标签
     * @return 状态
     */
    @FormUrlEncoded
    @POST("v1/users/follow-tags")
    Observable<HttpResult<Object>> follwTags(@Field("tags") String tags);

    /**
     * 获取首页 关注帖子列表
     *
     * @param post_id 帖子ID
     * @param load    加载方式(上拉填up 下拉填down)
     * @return 最新标签数据
     */
    @GET("v1/follow-posts")
    Observable<HttpResult<HomeNewestBean>> getFollowPostData(@Query("post_id") int post_id, @Query("load") String load, @Query("sequence") String sequence);

    /**
     * 获取标签详情页面
     *
     * @param postId   postId
     * @param load     加载方式，上下拉
     * @param sequence 发布时间
     * @param tagId    标签id
     * @return
     */
    @GET("v1/posts/video-posts")
    Observable<HttpResult<TagDetailsBean>> getTagDetailsData(@Query("post_id") int postId, @Query("load")
            String load, @Query("sequence") String sequence, @Query("tag_id") String tagId);

    /**
     * 取消关注标签
     * @param tagId 标签id
     * @return 结果
     */
    @DELETE("v1/users/delete-follow-tags")
    Observable<HttpResult<Object>> deleteFollowTag(@Query("tags") String tagId);

    @GET("v1/tags/lists")
    Observable<HttpResult<RecomedTagBean>> getTagListData();
}
