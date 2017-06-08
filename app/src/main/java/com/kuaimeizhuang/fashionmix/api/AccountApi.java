package com.kuaimeizhuang.fashionmix.api;

import com.kuaimeizhuang.fashionmix.bean.LoginSuccessBean;
import com.kuaimeizhuang.fashionmix.bean.User;
import com.kuaimeizhuang.fashionmix.bean.WeiXinBean;
import com.kuaimeizhuang.fashionmix.bean.WeiXinUserBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <p>账户网络api</p>
 * Created on 17/5/22.
 *
 * @author Shi GuangXiang.
 */

public interface AccountApi {

    /**
     * 第三方登录
     *
     * @param type       第三方帐号类型，枚举值：QQ, WX, WB
     * @param third_id   第三方帐号 ID
     * @param nickname   昵称
     * @param avatar_url 头像图片 URL
     * @return 是否登录成功
     */
    @FormUrlEncoded
    @POST("v1/access/login-by-{type}")
    Observable<HttpResult<LoginSuccessBean>> thirdLogin(@Path("type") String type, @Field("third_id") String third_id, @Field("nickname") String nickname, @Field("avatar_url") String avatar_url);

    /**
     * 获取用户基本信息
     *
     * @param user_id 用户ID
     * @return 用户基本信息
     */
    @GET("v1/users/{user_id}/profile")
    Observable<HttpResult<User>> getUsersProfile(@Path("user_id") String user_id);

    /**
     * 获取微信的token
     *
     * @param appid
     * @param secret
     * @param grant_type
     * @param code
     * @return
     */
    @GET("/sns/oauth2/access_token")
    Call<WeiXinBean> weixinGetUser(@Query("appid") String appid, @Query("secret") String secret, @Query("grant_type") String grant_type, @Query("code") String code);

    /**
     * 获取微信用户资料
     *
     * @param access_token
     * @param openid
     * @return
     */
    @GET("/sns/userinfo")
    Call<WeiXinUserBean> weixinUserInfo(@Query("access_token") String access_token, @Query("openid") String openid);
}
