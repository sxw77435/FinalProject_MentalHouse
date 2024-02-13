package com.uni.mental.checkup.model.dto;

import lombok.Data;

@Data
public class CheckupUserDTO {
    private int cuUserNo;
    private String cuTitle;
    private String cuQuestion;
    private int cuAnswer;
    private String memberId;
}
