package com.kuaimeizhuang.fashionmix.bean.base;

import android.databinding.BaseObservable;

import java.io.Serializable;
import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class BaseBean extends BaseObservable implements Serializable {
    private int status;
    private String message;
    private List<ErrorsBean> errors;

    public List<ErrorsBean> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorsBean> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "errors=" + errors +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
