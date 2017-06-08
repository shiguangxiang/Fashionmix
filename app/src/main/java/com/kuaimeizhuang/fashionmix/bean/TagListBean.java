package com.kuaimeizhuang.fashionmix.bean;

import java.util.List;

/**
 * <p>标签列表实体类</p>
 * Created on 17/5/25.
 *
 * @author Shi GuangXiang.
 */

public class TagListBean {
    private List<NewestTag> Tags;

    public List<NewestTag> getTags() {
        return Tags;
    }

    public void setTags(List<NewestTag> tags) {
        Tags = tags;
    }
}
