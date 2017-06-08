package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>自己的曝光点击上报</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Counts extends BaseBean {
    private List<String> view_report_apis;
    private List<String> click_report_apis;

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
}

