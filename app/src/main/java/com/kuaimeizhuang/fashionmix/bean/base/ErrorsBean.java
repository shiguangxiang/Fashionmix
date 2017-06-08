package com.kuaimeizhuang.fashionmix.bean.base;

/**
 * <p>网络请求错误</p>
 * Created on 17/4/10.
 *
 * @author Shi GuangXiang.
 */

public class ErrorsBean {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorsBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
