package com.kuaimeizhuang.fashionmix.retrofit.rx;

import com.kuaimeizhuang.fashionmix.base.LoadingPager;
import com.kuaimeizhuang.fashionmix.bean.base.HttpResult;
import com.kuaimeizhuang.fashionmix.databinding.ActivityBaseBinding;
import com.kuaimeizhuang.fashionmix.utils.LogUtils;

import rx.functions.Func1;

/**
 * <p>注释类</p>
 * Created on 17/4/10.
 *
 * @author Shi GuangXiang.
 */

public class BeautyFunc1<T> implements Func1<HttpResult<T>, T> {
    private ActivityBaseBinding mBinding;
    private boolean showSnackBarMsg = true;
    private String message;
    private LoadingPager mPager;

    public BeautyFunc1(ActivityBaseBinding binding, LoadingPager pager) {
        this.mBinding = binding;
        this.mPager = pager;
    }

    public BeautyFunc1(String message, ActivityBaseBinding binding, LoadingPager pager) {
        this.message = message;
        this.mBinding = binding;
        this.mPager = pager;
    }

    /**
     * @param binding         ActivityBaseViewBinding:用于操作通用loading
     * @param message         请求成功的提示消息eg：收藏成功
     * @param showSnackBarMsg 是否使用SnackBar提示（静默请求比如统计视频播放时长不需要任何提示信息）
     */
    public BeautyFunc1(ActivityBaseBinding binding, String message, boolean showSnackBarMsg, LoadingPager pager) {
        this.showSnackBarMsg = showSnackBarMsg;
        this.message = message;
        this.mBinding = binding;
        this.mPager = pager;
    }

    public void setShowSnackBarMsg(boolean showSnackBarMsg) {
        this.showSnackBarMsg = showSnackBarMsg;
    }

    @Override
    public T call(HttpResult<T> httpResult) {
        ErrorCode errorCode = ErrorCode.getErrorCode(httpResult.getStatus());
        showError(errorCode);
        switch (errorCode) {
            case SUCCESS:
                if (showSnackBarMsg && message != null) {
                    showErrPrompt(message);
                }
                return httpResult.getBody();
            case BUSINESS_ERROR:
                message = httpResult.getErrors().get(0).getMessage();
//                SnackbarUtil.showSnackBar(mBinding, message);
                showErrPrompt(message);
                return null;
            case INVALID_TOKEN:
                break;
            case PERMISSION_DENIED:
                break;
            case VERIFICATION_FAILURE:
                break;
            case RESOURCE_NOT_EXIST:
//                mPager.onDataLoading(LoadingPager.LoadedResult.DATANULL);
                break;
            case WRONG_REQUEST_METHOD:
//                mPager.onDataLoading(LoadingPager.LoadedResult.ERROR);
                break;
            case REQUEST_TOO_FREQUENT:
//                mPager.onDataLoading(LoadingPager.LoadedResult.ERROR);
                break;
            /**
             * 500 code
             */
            case SERVER_ERROR:
//                mPager.onDataLoading(LoadingPager.LoadedResult.SERVERERROR);
                /**
                 * 503 code
                 */
            case SERVER_MAINTENANCE:
//                mPager.onDataLoading(LoadingPager.LoadedResult.SERVERERROR);
                break;
        }
        message = errorCode.getErrMsg();

        if (showSnackBarMsg) {
            showErrPrompt(message);
        }
        throw new RuntimeException(message);
    }

    private void showErrPrompt(String errMsg) {
//        SnackbarUtil.showSnackBar( mBinding, errMsg);
//        Toast.makeText(MikaAppLocation.getContext(),errMsg,Toast.LENGTH_LONG).show();
        LogUtils.e(errMsg);
    }

    protected void showError(ErrorCode errorCode) {
        LogUtils.e("响应代码: " + errorCode.getErrCode() + ",响应信息: " + errorCode.getErrMsg());
    }
}

