package com.uni.mental.checkup.model.dto;

public class MessageResponse {
    private String message;    // 응답 메시지

    // 기본 생성자
    public MessageResponse() {
    }

    // 메시지를 포함하는 생성자
    public MessageResponse(String message) {
        this.message = message;
    }

    // 메시지에 대한 getter
    public String getMessage() {
        return message;
    }

    // 메시지에 대한 setter
    public void setMessage(String message) {
        this.message = message;
    }
}
