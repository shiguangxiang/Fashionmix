package com.kuaimeizhuang.fashionmix.bean.base;

import java.util.List;

/**
 * <p>网络请求状态信息实体</p>
 * Created on 17/4/10.
 *
 * @author Shi GuangXiang.
 */

public class HttpResult<T> {
    private int status;
    private String message;
    private T body;
    private List<ErrorsBean> errors;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public List<ErrorsBean> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorsBean> errors) {
        this.errors = errors;
    }
}
