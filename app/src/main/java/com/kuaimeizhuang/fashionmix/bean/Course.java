package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>课程</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Course extends BaseBean {
    private int total_duration;
    private String total_duration_unit;
    private int finished_count;
    private int count;
    private boolean has_finished;
    private int total_days;
    private int total_intergration;
    private int total_viewed_duration;
    private List<Post> lessons;

    public int getTotal_viewed_duration() {
        return total_viewed_duration;
    }

    public void setTotal_viewed_duration(int total_viewed_duration) {
        this.total_viewed_duration = total_viewed_duration;
    }

    public int getTotal_intergration() {
        return total_intergration;
    }

    public void setTotal_intergration(int total_intergration) {
        this.total_intergration = total_intergration;
    }

    public int getTotal_days() {
        return total_days;
    }

    public void setTotal_days(int total_days) {
        this.total_days = total_days;
    }

    public int getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(int total_duration) {
        this.total_duration = total_duration;
    }

    public String getTotal_duration_unit() {
        return total_duration_unit;
    }

    public void setTotal_duration_unit(String total_duration_unit) {
        this.total_duration_unit = total_duration_unit;
    }

    public int getFinished_count() {
        return finished_count;
    }

    public void setFinished_count(int finished_count) {
        this.finished_count = finished_count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHas_finished() {
        return has_finished;
    }

    public void setHas_finished(boolean has_finished) {
        this.has_finished = has_finished;
    }

    public List<Post> getLessons() {
        return lessons;
    }

    public void setLessons(List<Post> lessons) {
        this.lessons = lessons;
    }
}
