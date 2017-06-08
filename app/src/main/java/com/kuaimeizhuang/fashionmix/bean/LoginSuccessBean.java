package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

/**
 * <p>登录成功</p>
 * Created on 17/5/22.
 *
 * @author Shi GuangXiang.
 */

public class LoginSuccessBean extends BaseBean {

    private String user_token;
    private boolean is_register;
    private User user;
    private RedBag redbag;

    public boolean is_register() {
        return is_register;
    }

    public void setIs_register(boolean is_register) {
        this.is_register = is_register;
    }

    public RedBag getRedbag() {
        return redbag;
    }

    public void setRedbag(RedBag redbag) {
        this.redbag = redbag;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
