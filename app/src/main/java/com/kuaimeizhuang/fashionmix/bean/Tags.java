package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/5/19.
 *
 * @author Shi GuangXiang.
 */

public class Tags extends BaseBean {
    private List<NewestTag> Tags;

    public Tags(List<NewestTag> tags) {
        this.Tags = tags;
    }

    public List<NewestTag> getTags() {
        return Tags;
    }

    public void setTags(List<NewestTag> tags) {
        this.Tags = tags;
    }
}
