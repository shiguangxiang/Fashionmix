package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/6/5.
 *
 * @author Shi GuangXiang.
 */

public class RecomedTagBean extends BaseBean {
    private List<ItemRecomedBean> TagLists;

    public List<ItemRecomedBean> getTagLists() {
        return TagLists;
    }

    public void setTagLists(List<ItemRecomedBean> tagLists) {
        TagLists = tagLists;
    }
}
