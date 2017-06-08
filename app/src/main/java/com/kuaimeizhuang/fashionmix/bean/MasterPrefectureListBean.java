package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>关注人列表实体类</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class MasterPrefectureListBean extends BaseBean {

    private int per_page;
    private boolean has_more_pages;
    private List<User> users;

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public boolean isHas_more_pages() {
        return has_more_pages;
    }

    public void setHas_more_pages(boolean has_more_pages) {
        this.has_more_pages = has_more_pages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
