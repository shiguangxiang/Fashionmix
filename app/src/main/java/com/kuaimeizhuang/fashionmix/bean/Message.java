package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

/**
 * <p>注释类</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class Message extends BaseBean {
    private int id;
    private String type;
    private String open;
    private User other_user;
    private Comment other_comment;
    private Post my_post;
    private OrdersBean my_order;
    private String content;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public User getOther_user() {
        return other_user;
    }

    public void setOther_user(User other_user) {
        this.other_user = other_user;
    }

    public Comment getOther_comment() {
        return other_comment;
    }

    public void setOther_comment(Comment other_comment) {
        this.other_comment = other_comment;
    }

    public Post getMy_post() {
        return my_post;
    }

    public void setMy_post(Post my_post) {
        this.my_post = my_post;
    }

    public OrdersBean getMy_order() {
        return my_order;
    }

    public void setMy_order(OrdersBean my_order) {
        this.my_order = my_order;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
