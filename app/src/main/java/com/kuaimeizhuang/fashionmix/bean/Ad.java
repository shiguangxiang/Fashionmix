package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>广告实体类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Ad extends BaseBean {
    private String web_url;
    private List<String> view_report_apis;
    private List<String> click_report_apis;
    private boolean isView;

    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    public List<String> getView_report_apis() {
        return view_report_apis;
    }

    public void setView_report_apis(List<String> view_report_apis) {
        this.view_report_apis = view_report_apis;
    }

    public List<String> getClick_report_apis() {
        return click_report_apis;
    }

    public void setClick_report_apis(List<String> click_report_apis) {
        this.click_report_apis = click_report_apis;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }
}
