package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/6/5.
 *
 * @author Shi GuangXiang.
 */

public class ItemRecomedBean extends BaseBean {
    private String name;
    private String cover;
    private List<NewestTag> tags;


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NewestTag> getTags() {
        return tags;
    }

    public void setTags(List<NewestTag> tags) {
        this.tags = tags;
    }
}
