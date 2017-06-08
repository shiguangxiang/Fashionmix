package com.kuaimeizhuang.fashionmix.bean;

import android.databinding.ObservableBoolean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

/**
 * <p>用户实体类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class User extends BaseBean {
    private int id;
    private String title;
    private String nickname;
    private String avatar_url;
    private String background_url;
    private String sign;
    private String role;
    private Admin admin;
    private int fans_count;
    private int follow_count;
    private boolean has_followed;
    private int points_value;
    private int growth_value;
    private String level_name;
    private String reg_type;
    private String email;
    private String phone_number;
    private String logged_in_at;
    private boolean is_sign_in;
    private int sign_in_points;
    private String created_at;
    private boolean selected;
    private int best_answer_count;
    public ObservableBoolean isLoading = new ObservableBoolean();
    private int follow_tag_count;

    public int getFollow_tag_count() {
        return follow_tag_count;
    }

    public void setFollow_tag_count(int follow_tag_count) {
        this.follow_tag_count = follow_tag_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getBackground_url() {
        return background_url;
    }

    public void setBackground_url(String background_url) {
        this.background_url = background_url;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }

    public boolean isHas_followed() {
        return has_followed;
    }

    public void setHas_followed(boolean has_followed) {
        this.has_followed = has_followed;
    }

    public int getPoints_value() {
        return points_value;
    }

    public void setPoints_value(int points_value) {
        this.points_value = points_value;
    }

    public int getGrowth_value() {
        return growth_value;
    }

    public void setGrowth_value(int growth_value) {
        this.growth_value = growth_value;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getReg_type() {
        return reg_type;
    }

    public void setReg_type(String reg_type) {
        this.reg_type = reg_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getLogged_in_at() {
        return logged_in_at;
    }

    public void setLogged_in_at(String logged_in_at) {
        this.logged_in_at = logged_in_at;
    }

    public boolean is_sign_in() {
        return is_sign_in;
    }

    public void setIs_sign_in(boolean is_sign_in) {
        this.is_sign_in = is_sign_in;
    }

    public int getSign_in_points() {
        return sign_in_points;
    }

    public void setSign_in_points(int sign_in_points) {
        this.sign_in_points = sign_in_points;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getBest_answer_count() {
        return best_answer_count;
    }

    public void setBest_answer_count(int best_answer_count) {
        this.best_answer_count = best_answer_count;
    }

    private class Admin extends BaseBean {

    }
}

