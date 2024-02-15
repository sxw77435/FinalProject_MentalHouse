package com.uni.mental.ageComunity.model.dto;
import lombok.Data;
import java.util.Date;

public class AgeComDTO {
    private Integer ageComNo;
    private int cateNo;
    private String ageComTitle;
    private String ageComDetail;
    private String memberNick;
    private Date ageComDate;
    private int ageComViews;


    public AgeComDTO(int ageComNo, int cateNo, String ageComTitle, String ageComDetail, String memberNick, Date ageComDate, int ageComViews) {
        this.ageComNo = ageComNo;
        this.cateNo = cateNo;
        this.ageComTitle = ageComTitle;
        this.ageComDetail = ageComDetail;
        this.memberNick = memberNick;
        this.ageComDate = ageComDate;
        this.ageComViews = ageComViews;
    }

    public int getAgeComNo() {
        return ageComNo;
    }

    public void setAgeComNo(int ageComNo) {
        this.ageComNo = ageComNo;
    }

    public int getCateNo() {
        return cateNo;
    }

    public void setCateNo(int cateNo) {
        this.cateNo = cateNo;
    }

    public String getAgeComTitle() {
        return ageComTitle;
    }

    public void setAgeComTitle(String ageComTitle) {
        this.ageComTitle = ageComTitle;
    }

    public String getAgeComDetail() {
        return ageComDetail;
    }

    public void setAgeComDetail(String ageComDetail) {
        this.ageComDetail = ageComDetail;
    }

    public String getMemberNick() {
        return memberNick;
    }

    public void setMemberNick(String memberNick) {
        this.memberNick = memberNick;
    }

    public Date getAgeComDate() {
        return ageComDate;
    }

    public void setAgeComDate(Date ageComDate) {
        this.ageComDate = ageComDate;
    }

    public int getAgeComViews() {
        return ageComViews;
    }

    public void setAgeComViews(int ageComViews) {
        this.ageComViews = ageComViews;
    }

    @Override
    public String toString() {
        return "AgeComDTO{" +
                "ageComNo=" + ageComNo +
                ", cateNo=" + cateNo +
                ", ageComTitle='" + ageComTitle + '\'' +
                ", ageComDetail='" + ageComDetail + '\'' +
                ", memberNick='" + memberNick + '\'' +
                ", ageComDate=" + ageComDate +
                ", ageComViews=" + ageComViews +
                '}';
    }
}
