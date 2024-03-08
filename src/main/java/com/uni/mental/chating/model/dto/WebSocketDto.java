package com.uni.mental.chating.model.dto;

import java.sql.Timestamp;

public class WebSocketDto {

    private int chatingno;
    private String senderId;
    private String sendermail;
    private String receiverId;
    private String receiveremail;
    private String message;
    private Timestamp sendtime;

    public int getChatingno() {
        return chatingno;
    }

    public void setChatingno(int chatingno) {
        this.chatingno = chatingno;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSendermail() {
        return sendermail;
    }

    public void setSendermail(String sendermail) {
        this.sendermail = sendermail;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiveremail() {
        return receiveremail;
    }

    public void setReceiveremail(String receiveremail) {
        this.receiveremail = receiveremail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getSendtime() {
        return sendtime;
    }

    public void setSendtime(Timestamp sendtime) {
        this.sendtime = sendtime;
    }

    @Override
    public String toString() {
        return "WebSocketDto{" +
                "chatingno=" + chatingno +
                ", senderId='" + senderId + '\'' +
                ", sendermail='" + sendermail + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", receiveremail='" + receiveremail + '\'' +
                ", message='" + message + '\'' +
                ", sendtime=" + sendtime +
                '}';
    }
}
