package com.uni.mental.qna.model.dto;

import java.util.Date;

public class CommentDto {

    private Integer pno; // 댓글 번호
    private Integer no;  // qna 번호
    private String content; //  내용
    private String id; //멤버 id
    private Date date; // 댓글 등록일
    private String nick;

    public Integer getPno() {
        return pno;
    }

    public void setPno(Integer pno) {
        this.pno = pno;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "pno=" + pno +
                ", no=" + no +
                ", content='" + content + '\'' +
                ", id='" + id + '\'' +
                ", date=" + date +
                ", nick='" + nick + '\'' +
                '}';
    }




}