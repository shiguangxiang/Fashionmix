package com.kuaimeizhuang.fashionmix.retrofit;

/**
 * <p>注释类</p>
 * Created on 17/6/5.
 *
 * @author Shi GuangXiang.
 */

public interface ProgressListener {
    void onProgress(long currentBytes, long contentLength, boolean done);
}
