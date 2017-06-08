package com.kuaimeizhuang.fashionmix.bean;

import com.kuaimeizhuang.fashionmix.bean.base.BaseBean;

import java.util.List;

/**
 * <p>注释类</p>
 * Created on 17/5/16.
 *
 * @author Shi GuangXiang.
 */

public class Video extends BaseBean {
    private String channel;
    private String image_url;
    private String video_url;
    private int duration;
    private int width;
    private int height;
    private String description;
    private List<VideoStep> steps;
    private List<Product> products;
    private List<Goods> goods;
    private RedBag redbag;
    private Source source;
    private List<String> video_urls;
    private String video_source_url;
    /**
     * 文件大小，字节
     */
    private long totalSize;
    /**
     * 已加载大小
     */
    private long loadedSize;

    /**
     * 下载状态
     */

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getLoadedSize() {
        return loadedSize;
    }

    public void setLoadedSize(long loadedSize) {
        this.loadedSize = loadedSize;
    }

    public String getVideo_source_url() {
        return video_source_url;
    }

    public void setVideo_source_url(String video_source_url) {
        this.video_source_url = video_source_url;
    }

    public List<String> getVideo_urls() {
        return video_urls;
    }

    public void setVideo_urls(List<String> video_urls) {
        this.video_urls = video_urls;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public RedBag getRedbag() {
        return redbag;
    }

    public void setRedbag(RedBag redbag) {
        this.redbag = redbag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<VideoStep> getSteps() {
        return steps;
    }

    public void setSteps(List<VideoStep> steps) {
        this.steps = steps;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Video{" +
                "description='" + description + '\'' +
                ", image_url='" + image_url + '\'' +
                ", video_url='" + video_url + '\'' +
                ", duration=" + duration +
                ", width=" + width +
                ", height=" + height +
                ", steps=" + steps +
                ", products=" + products +
                ", goods=" + goods +
                '}';
    }
}
