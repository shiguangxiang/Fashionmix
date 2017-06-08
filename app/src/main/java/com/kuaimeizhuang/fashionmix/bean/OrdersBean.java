package com.kuaimeizhuang.fashionmix.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;
import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/6/2.
 *
 * @author Shi GuangXiang.
 */

public class OrdersBean extends BaseObservable implements Serializable {
    private String type;
    private String trade_no;
    private Address address;
    private String total_amount;
    private int int_total_amount;
    private double float_total_amount;
    private Express express;
    private int status;
    private String status_text;
    private String created_at;
    private List<Goods> goods;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public int getInt_total_amount() {
        return int_total_amount;
    }

    public void setInt_total_amount(int int_total_amount) {
        this.int_total_amount = int_total_amount;
    }

    public double getFloat_total_amount() {
        return float_total_amount;
    }

    public void setFloat_total_amount(double float_total_amount) {
        this.float_total_amount = float_total_amount;
    }

    public Express getExpress() {
        return express;
    }

    public void setExpress(Express express) {
        this.express = express;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

}
