package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>统一帖子实体</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Post extends BaseBean {
    private int id;
    private int parent_id;
    private List<String> title_prefixes;
    private String title;
    private String content;
    private Cover cover;
    private List<NewestTag> tags;
    private User author;
    private String type;
    private List<Section> sections;
    private List<Video> videos;
    private List<Post> subposts;
    private List<Post> recommended_posts;
    private Comments comments;
    private boolean has_favorited;
    private boolean has_liked;
    private int shares_count;
    private int likes_count;
    private int favorites_count;
    private int views_count;
    private int comments_count;
    private String created_at;
    private String updated_at;
    private Question question;
    private Trial trial;
    private Rights rights;
    private List<String> attributes;
    private int isTime;
    private int isCnxh;
    private Ad ad;
    private Course course;
    private boolean has_added;
    private String lessons_title;
    private boolean has_finished;
    private Counts counts;

    public Counts getCounts() {
        return counts;
    }

    public void setCounts(Counts counts) {
        this.counts = counts;
    }

    public boolean isHas_finished() {
        return has_finished;
    }

    public void setHas_finished(boolean has_finished) {
        this.has_finished = has_finished;
    }

    public String getLessons_title() {
        return lessons_title;
    }

    public void setLessons_title(String lessons_title) {
        this.lessons_title = lessons_title;
    }

    public boolean isHas_added() {
        return has_added;
    }

    public void setHas_added(boolean has_added) {
        this.has_added = has_added;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public int getIsCnxh() {
        return isCnxh;
    }

    public void setIsCnxh(int isCnxh) {
        this.isCnxh = isCnxh;
    }

    public int getIsTime() {
        return isTime;
    }

    public void setIsTime(int isTime) {
        this.isTime = isTime;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public Rights getRights() {
        return rights;
    }

    public void setRights(Rights rights) {
        this.rights = rights;
    }

    public Trial getTrial() {
        return trial;
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isHas_liked() {
        return has_liked;
    }

    public void setHas_liked(boolean has_liked) {
        this.has_liked = has_liked;
    }

    public boolean isHas_favorited() {
        return has_favorited;
    }

    public void setHas_favorited(boolean has_favorited) {
        this.has_favorited = has_favorited;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getFavorites_count() {
        return favorites_count;
    }

    public void setFavorites_count(int favorites_count) {
        this.favorites_count = favorites_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public List<Post> getRecommended_posts() {
        return recommended_posts;
    }

    public void setRecommended_posts(List<Post> recommended_posts) {
        this.recommended_posts = recommended_posts;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public int getShares_count() {
        return shares_count;
    }

    public void setShares_count(int shares_count) {
        this.shares_count = shares_count;
    }

    public List<Post> getSubposts() {
        return subposts;
    }

    public void setSubposts(List<Post> subposts) {
        this.subposts = subposts;
    }

    public List<NewestTag> getTags() {
        return tags;
    }

    public void setTags(List<NewestTag> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTitle_prefixes() {
        return title_prefixes;
    }

    public void setTitle_prefixes(List<String> title_prefixes) {
        this.title_prefixes = title_prefixes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public int getViews_count() {
        return views_count;
    }

    public void setViews_count(int views_count) {
        this.views_count = views_count;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }


    @Override
    public String toString() {
        return "Post{" +
                "user=" + author +
                ", id=" + id +
                ", title_prefixes=" + title_prefixes +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", cover=" + cover +
                ", tags=" + tags +
                ", type='" + type + '\'' +
                ", sections=" + sections +
                ", videos=" + videos +
                ", subposts=" + subposts +
                ", recommended_posts=" + recommended_posts +
                ", comments=" + comments +
                ", likes_count=" + likes_count +
                ", shares_count=" + shares_count +
                ", favorites_count=" + favorites_count +
                ", views_count=" + views_count +
                ", comments_count=" + comments_count +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }

    public boolean canApply(String statu) {
        if (statu == null) {
            return false;
        }
        return statu.trim().equals("applying");
    }

    public boolean isApplied(String user_status) {
        if (user_status == null) {
            return false;
        }
        return !user_status.trim().equals("not_apply");
    }

    public boolean isAlreadyApply(String statu) {
        if (statu == null) {
            return false;
        }

        return !statu.trim().equals("over");
    }

}
