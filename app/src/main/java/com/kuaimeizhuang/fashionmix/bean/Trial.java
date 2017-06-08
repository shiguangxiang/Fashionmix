package com.kuaimeizhuang.fashionmix.bean;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * <p>试用</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Trial extends BaseObservable implements Serializable {
    private String how_to_apply;
    private String rules;
    private int points;
    private int limit;
    private int user_count;
    private String selected_users;
    private String starting_at;
    private String ending_at;
    private String closing_at;
    private String status;
    private String user_status;
    private UserReportPost user_report_post;
    private UserApplication user_application;
    private boolean is_show_reports;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getClosing_at() {
        return closing_at;
    }

    public void setClosing_at(String closing_at) {
        this.closing_at = closing_at;
    }

    public String getEnding_at() {
        return ending_at;
    }

    public void setEnding_at(String ending_at) {
        this.ending_at = ending_at;
    }

    public String getHow_to_apply() {
        return how_to_apply;
    }

    public void setHow_to_apply(String how_to_apply) {
        this.how_to_apply = how_to_apply;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }


    public String getSelected_users() {
        return selected_users;
    }

    public void setSelected_users(String selected_users) {
        this.selected_users = selected_users;
    }

    public String getStarting_at() {
        return starting_at;
    }

    public void setStarting_at(String starting_at) {
        this.starting_at = starting_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUser_count() {
        return user_count;
    }

    public void setUser_count(int user_count) {
        this.user_count = user_count;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public static class UserReportPost implements Serializable {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public UserReportPost getUser_report_post() {
        return user_report_post;
    }

    public void setUser_report_post(UserReportPost user_report_post) {
        this.user_report_post = user_report_post;
    }

    public UserApplication getUser_application() {
        return user_application;
    }

    public void setUser_application(UserApplication user_application) {
        this.user_application = user_application;
    }

    public boolean is_show_reports() {
        return is_show_reports;
    }

    public void setIs_show_reports(boolean is_show_reports) {
        this.is_show_reports = is_show_reports;
    }

    public static class UserApplication implements Serializable {
        private String status;
        private String created_at;
        private UserReportPost report_post;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public UserReportPost getReport_post() {
            return report_post;
        }

        public void setReport_post(UserReportPost report_post) {
            this.report_post = report_post;
        }
    }

    @Override
    public String toString() {
        return "Trial{" +
                "closing_at='" + closing_at + '\'' +
                ", how_to_apply='" + how_to_apply + '\'' +
                ", rules='" + rules + '\'' +
                ", limit=" + limit +
                ", user_count=" + user_count +
                ", selected_users='" + selected_users + '\'' +
                ", starting_at='" + starting_at + '\'' +
                ", ending_at='" + ending_at + '\'' +
                ", status='" + status + '\'' +
                ", user_status='" + user_status + '\'' +
                '}';
    }
}
