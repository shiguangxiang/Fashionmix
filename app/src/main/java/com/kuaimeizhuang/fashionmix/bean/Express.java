package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>物流</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class Express extends BaseBean {
    private String express_server;
    private String express_no;
    private List<Tracking> tracking;

    public String getExpress_server() {
        return express_server;
    }

    public void setExpress_server(String express_server) {
        this.express_server = express_server;
    }

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public List<Tracking> getTracking() {
        return tracking;
    }

    public void setTracking(List<Tracking> tracking) {
        this.tracking = tracking;
    }

}
