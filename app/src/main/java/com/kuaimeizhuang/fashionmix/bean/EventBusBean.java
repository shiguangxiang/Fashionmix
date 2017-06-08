package com.kuaimeizhuang.fashionmix.bean;

/**
 * <p>注释类</p>
 * Created on 17/5/15.
 *
 * @author Shi GuangXiang.
 */

public class EventBusBean {
    private String message;

    public EventBusBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
