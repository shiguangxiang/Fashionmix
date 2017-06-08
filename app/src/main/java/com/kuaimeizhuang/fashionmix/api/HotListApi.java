package com.kuaimeizhuang.fashionmix.api;

import com.kuaimeizhuang.fashionmix.bean.HomeNewestBean;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <p>热榜网络请求接口</p>
 * Created on 17/5/26.
 *
 * @author Shi GuangXiang.
 */

public interface HotListApi {
    /**
     * 获取热榜
     * @param data 日榜、周榜
     * @return 结果
     */
    @GET("v1/posts-video-billboard")
    Observable<HttpResult<HomeNewestBean>> getHotListData(@Query("data") String data);
}
