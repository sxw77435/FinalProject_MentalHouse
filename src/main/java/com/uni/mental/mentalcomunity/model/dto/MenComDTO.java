package com.uni.mental.mentalcomunity.model.dto;


import java.util.Date;

public class MenComDTO {

    private Integer no;

    private String nickName;

    private String title;

    private String content;

    private Date date;

    private int cate;

    private Integer views;

    public MenComDTO(Integer no, String nickName, String title, String content, Date date, int cate, Integer views) {
        this.no = no;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
        this.date = date;
        this.cate = cate;
        this.views = views;
    }

    @Override
    public String toString() {
        return "MenComDTO{" +
                "no=" + no +
                ", nickName='" + nickName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", cate=" + cate +
                ", views=" + views +
                '}';
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}