package com.uni.mental.chating.model.dto;

import java.sql.Timestamp;

public class ChatroomMsgDto {
    private int messageno;
    private int chatroomno;
    private String senderId;
    private String message;
    private Timestamp sendat;

    public int getMessageno() {
        return messageno;
    }

    public void setMessageno(int messageno) {
        this.messageno = messageno;
    }

    public int getChatroomno() {
        return chatroomno;
    }

    public void setChatroomno(int chatroomno) {
        this.chatroomno = chatroomno;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getSendat() {
        return sendat;
    }

    public void setSendat(Timestamp sendat) {
        this.sendat = sendat;
    }

    @Override
    public String toString() {
        return "ChatroomMsgDto{" +
                "messageno=" + messageno +
                ", chatroomno=" + chatroomno +
                ", senderId='" + senderId + '\'' +
                ", message='" + message + '\'' +
                ", sendat=" + sendat +
                '}';
    }
}
