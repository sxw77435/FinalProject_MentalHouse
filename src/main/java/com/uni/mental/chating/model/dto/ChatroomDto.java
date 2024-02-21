package com.uni.mental.chating.model.dto;

import java.sql.Timestamp;

public class ChatroomDto {

    private int chatroomno;
    private String chatroomowner;
    private String chatroomname;
    private String chatroompwd;
    private Timestamp createdat;

    public int getChatroomno() {
        return chatroomno;
    }

    public void setChatroomno(int chatroomno) {
        this.chatroomno = chatroomno;
    }

    public String getChatroomowner() {
        return chatroomowner;
    }

    public void setChatroomowner(String chatroomowner) {
        this.chatroomowner = chatroomowner;
    }

    public String getChatroomname() {
        return chatroomname;
    }

    public void setChatroomname(String chatroomname) {
        this.chatroomname = chatroomname;
    }

    public String getChatroompwd() {
        return chatroompwd;
    }

    public void setChatroompwd(String chatroompwd) {
        this.chatroompwd = chatroompwd;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    @Override
    public String toString() {
        return "ChatroomDto{" +
                "chatroomno=" + chatroomno +
                ", chatroomowner='" + chatroomowner + '\'' +
                ", chatroomname='" + chatroomname + '\'' +
                ", chatroompwd='" + chatroompwd + '\'' +
                ", createdat=" + createdat +
                '}';
    }
}
