package com.uni.mental.regioncomunity.model.dto;

import java.util.Date;

public class RecomDto {

    private int recomno;
    private int cateno;
    private String recomtitle;
    private String memberid;

    private String membernick;
    private Date recomdate;
    private int recomviews;
    private String recomcontent;
    private String recomimage;
    private  int replycount;



    public int getRecomno() {
        return recomno;
    }

    public void setRecomno(int recomno) {
        this.recomno = recomno;
    }

    public int getCateno() {
        return cateno;
    }

    public void setCateno(int cateno) {
        this.cateno = cateno;
    }

    public String getRecomtitle() {
        return recomtitle;
    }

    public void setRecomtitle(String recomtitle) {
        this.recomtitle = recomtitle;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getMembernick() {
        return membernick;
    }

    public void setMembernick(String membernick) {
        this.membernick = membernick;
    }

    public Date getRecomdate() {
        return recomdate;
    }

    public void setRecomdate(Date recomdate) {
        this.recomdate = recomdate;
    }

    public int getRecomviews() {
        return recomviews;
    }

    public void setRecomviews(int recomviews) {
        this.recomviews = recomviews;
    }

    public String getRecomcontent() {
        return recomcontent;
    }

    public void setRecomcontent(String recomcontent) {
        this.recomcontent = recomcontent;
    }

    public String getRecomimage() {
        return recomimage;
    }

    public void setRecomimage(String recomimage) {
        this.recomimage = recomimage;
    }

    public int getReplycount() {
        return replycount;
    }

    public void setReplycount(int replycount) {
        this.replycount = replycount;
    }

    @Override
    public String toString() {
        return "RecomDto{" +
                "recomno=" + recomno +
                ", cateno=" + cateno +
                ", recomtitle='" + recomtitle + '\'' +
                ", memberid='" + memberid + '\'' +
                ", membernick='" + membernick + '\'' +
                ", recomdate=" + recomdate +
                ", recomviews=" + recomviews +
                ", recomcontent='" + recomcontent + '\'' +
                ", recomimage='" + recomimage + '\'' +
                ", replycount=" + replycount +
                '}';
    }
}
