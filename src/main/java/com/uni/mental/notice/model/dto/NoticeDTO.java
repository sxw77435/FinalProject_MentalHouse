package com.uni.mental.notice.model.dto;

import java.util.Date;

public class NoticeDTO {
    private Integer no; // 공지사항 번호
    private String title; // 공지사항 제목
    private String content; // 공지사항 내용
    private Date date; // 글 등록일
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NoticeDTO(Integer no, String title, String content, Date date, String id) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.date = date;
        this.id = id;
    }

    @Override
    public String toString() {
        return "NoticeDto{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", id='" + id + '\'' +
                '}';
    }
}
