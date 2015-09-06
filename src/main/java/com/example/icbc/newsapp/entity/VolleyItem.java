package com.example.icbc.newsapp.entity;

/**
 * Created by admin on 2015/9/5.
 */
public class VolleyItem {
    private long id;
    private String title;
    private String content;
    private String img_width;
    private String full_title;
    private String pdate;
    private String src;
    private String img;
    private String img_length;
    private String url;
    private String pdate_src;

    public VolleyItem(long id, String pdate_src, String url, String title, String content, String img_width, String full_title, String pdate, String src, String img, String img_length) {
        this.id = id;
        this.pdate_src = pdate_src;
        this.url = url;
        this.title = title;
        this.content = content;
        this.img_width = img_width;
        this.full_title = full_title;
        this.pdate = pdate;
        this.src = src;
        this.img = img;
        this.img_length = img_length;
    }

    public VolleyItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg_width() {
        return img_width;
    }

    public void setImg_width(String img_width) {
        this.img_width = img_width;
    }

    public String getFull_title() {
        return full_title;
    }

    public void setFull_title(String full_title) {
        this.full_title = full_title;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg_length() {
        return img_length;
    }

    public void setImg_length(String img_length) {
        this.img_length = img_length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPdate_src() {
        return pdate_src;
    }

    public void setPdate_src(String pdate_src) {
        this.pdate_src = pdate_src;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", img_width='" + img_width + '\'' +
                ", full_title='" + full_title + '\'' +
                ", pdate='" + pdate + '\'' +
                ", src='" + src + '\'' +
                ", img='" + img + '\'' +
                ", img_length='" + img_length + '\'' +
                ", url='" + url + '\'' +
                ", pdate_src='" + pdate_src
                ;
    }
}
