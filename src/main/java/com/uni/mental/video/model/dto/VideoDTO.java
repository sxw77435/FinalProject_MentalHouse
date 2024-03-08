package com.uni.mental.video.model.dto;

import java.util.Date;

public class VideoDTO {

    private Integer no;

    private String title;

    private  String content;

    private Date date;

    private String link;

    private  String thumbnail;


    public VideoDTO(Integer no, String title, String content, Date date, String link, String thumbnail) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.date = date;
        this.link = link;
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "VideoDTO{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", link='" + link + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
