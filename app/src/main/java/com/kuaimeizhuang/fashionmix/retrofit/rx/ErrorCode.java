package com.kuaimeizhuang.fashionmix.retrofit.rx;

/**
 * <p>注释类</p>
 * Created on 17/4/10.
 *
 * @author Shi GuangXiang.
 */

public enum ErrorCode {
    /**
     * 请求成功
     */
    SUCCESS(200, "成功"),
    /**
     * 业务级错误
     */
    BUSINESS_ERROR(202, "业务级错误"),
    /**
     * 无效Token
     */
    INVALID_TOKEN(400, "未登录或者登陆信息过期，请重新登陆"),
    /**
     * 权限不足
     */
    PERMISSION_DENIED(401, "权限不足"),
    /**
     * 签名校验失败
     */
    VERIFICATION_FAILURE(403, "签名校验失败，请稍后重试"),
    /**
     * 资源不存在
     */
    RESOURCE_NOT_EXIST(404, "数据不存在"),
    /**
     * 错误的请求方法
     */
    WRONG_REQUEST_METHOD(405, "网络错误"),
    /**
     * 请求过频
     */
    REQUEST_TOO_FREQUENT(429, "网络错误"),
    /**
     * 服务器内部错误
     */
    SERVER_ERROR(500, "服务器维护中，请稍后重试"),
    /**
     * 服务器维护
     */
    SERVER_MAINTENANCE(503, "服务器维护中，请稍后重试...");

    private int errCode;
    private String errMsg;

    ErrorCode(int code, String message) {
        this.errCode = code;
        this.errMsg = message;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public static ErrorCode getErrorCode(int errCode){
        for (ErrorCode eC :
                ErrorCode.values()) {
            if (eC.getErrCode() == errCode) {
                return eC;
            }
        }
        return SERVER_ERROR;
    }
}


