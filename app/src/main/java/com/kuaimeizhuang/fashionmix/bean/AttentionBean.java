package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

/**
 * <p>关注实体类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class AttentionBean extends BaseBean {
    private String imageUrl;
    private String tag;
    private String attentionUrl;

    public AttentionBean(String imageUrl, String tag,String attentionUrl) {
        this.imageUrl = imageUrl;
        this.tag = tag;
        this.attentionUrl = attentionUrl;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAttentionUrl() {
        return attentionUrl;
    }

    public void setAttentionUrl(String attentionUrl) {
        this.attentionUrl = attentionUrl;
    }
}
