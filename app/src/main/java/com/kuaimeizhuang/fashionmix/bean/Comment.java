package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Comment extends BaseBean {
    private int id;
    private int post_id;
    private int parent_id;
    private String content;
    private User author;
    private List<Section> sections;
    private String created_at;
    private String updated_at;
    private boolean isBestAnswer;
    private Rights rights;
    private List<Comment> children_comments;
    private User parent_author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isBestAnswer() {
        return isBestAnswer;
    }

    public void setBestAnswer(boolean bestAnswer) {
        isBestAnswer = bestAnswer;
    }

    public Rights getRights() {
        return rights;
    }

    public void setRights(Rights rights) {
        this.rights = rights;
    }

    public List<Comment> getChildren_comments() {
        return children_comments;
    }

    public void setChildren_comments(List<Comment> children_comments) {
        this.children_comments = children_comments;
    }

    public User getParent_author() {
        return parent_author;
    }

    public void setParent_author(User parent_author) {
        this.parent_author = parent_author;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "author=" + author +
                ", id=" + id +
                ", content='" + content + '\'' +
                ", sections=" + sections +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}

