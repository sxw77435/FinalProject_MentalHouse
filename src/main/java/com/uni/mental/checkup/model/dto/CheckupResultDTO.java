package com.uni.mental.checkup.model.dto;

public class CheckupResultDTO {
    private int yesCount;
    private int totalQuestions;

    // '예'로 응답한 개수를 반환하는 getter 메서드
    public int getYesCount() {
        return yesCount;
    }

    // '예'로 응답한 개수를 설정하는 setter 메서드
    public void setYesCount(int yesCount) {
        this.yesCount = yesCount;
    }

    // 총 질문 수를 반환하는 getter 메서드
    public int getTotalQuestions() {
        return totalQuestions;
    }

    // 총 질문 수를 설정하는 setter 메서드
    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
