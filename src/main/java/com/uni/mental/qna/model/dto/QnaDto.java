package com.uni.mental.qna.model.dto;

import java.util.Date;

public class QnaDto {
    private Integer no; // 공지사항 번호
    private String title; // 공지사항 제목
    private String content; // 공지사항 내용
    private String id;
    private Date date; // 글 등록일

    public QnaDto(Integer no, String title, String content, String id, Date date) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.id = id;
        this.date = date;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "QnaDto{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", date=" + date +
                '}';
    }
}