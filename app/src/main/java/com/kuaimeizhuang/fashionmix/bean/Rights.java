package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

/**
 * <p>注释类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Rights extends BaseBean {
    private boolean allow_deleting;
    private boolean allow_report;
    private boolean allow_updating;

    public boolean isAllow_deleting() {
        return allow_deleting;
    }

    public void setAllow_deleting(boolean allow_deleting) {
        this.allow_deleting = allow_deleting;
    }

    public boolean isAllow_report() {
        return allow_report;
    }

    public void setAllow_report(boolean allow_report) {
        this.allow_report = allow_report;
    }

    public boolean isAllow_updating() {
        return allow_updating;
    }

    public void setAllow_updating(boolean allow_updating) {
        this.allow_updating = allow_updating;
    }
}

