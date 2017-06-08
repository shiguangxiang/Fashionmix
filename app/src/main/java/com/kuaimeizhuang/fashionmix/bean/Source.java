package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

/**
 * <p>注释类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Source extends BaseBean {
    private String description;
    private String source_url;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }
}

