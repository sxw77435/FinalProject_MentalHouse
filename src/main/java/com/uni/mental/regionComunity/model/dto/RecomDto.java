package com.uni.mental.regionComunity.model.dto;

import java.util.Date;

public class RecomDto {

    private int recomno;
    private int cateno;
    private String recomtitle;
    private String membernick;
    private Date recomdate;
    private int recomviews;
    private String recomecontent;


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

    public String getRecomecontent() {
        return recomecontent;
    }

    public void setRecomecontent(String recomecontent) {
        this.recomecontent = recomecontent;
    }


    @Override
    public String toString() {
        return "RecomDto{" +
                "recomno=" + recomno +
                ", cateno=" + cateno +
                ", recomtitle='" + recomtitle + '\'' +
                ", membernick='" + membernick + '\'' +
                ", recomdate=" + recomdate +
                ", recomviews=" + recomviews +
                ", recomecontent='" + recomecontent + '\'' +

                '}';
    }
}
