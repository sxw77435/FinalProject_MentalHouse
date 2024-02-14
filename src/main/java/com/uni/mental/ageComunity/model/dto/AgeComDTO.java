package com.uni.mental.ageComunity.model.dto;


import lombok.Data;

import java.util.Date;

@Data
public class AgeComDTO {

    private int cateNo;
    private int ageComNo;
    private String ageComTitle;
    private String ageComDetail;
    private String memberNick;
    private Date ageComDate;
    private int views;
}