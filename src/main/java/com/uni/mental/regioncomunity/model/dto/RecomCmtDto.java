package com.uni.mental.regioncomunity.model.dto;

import java.util.Date;

public class RecomCmtDto {

    private int recmtno;
    private int recomno;
    private String content;
    private String  memberid;
    private Date recmtdate;
    private String membernick;

    public int getRecmtno() {
        return recmtno;
    }

    public void setRecmtno(int recmtno) {
        this.recmtno = recmtno;
    }

    public int getRecomno() {
        return recomno;
    }

    public void setRecomno(int recomno) {
        this.recomno = recomno;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public Date getRecmtdate() {
        return recmtdate;
    }

    public void setRecmtdate(Date recmtdate) {
        this.recmtdate = recmtdate;
    }

    public String getMembernick() {
        return membernick;
    }

    public void setMembernick(String membernick) {
        this.membernick = membernick;
    }

    @Override
    public String toString() {
        return "RecomCmtDto{" +
                "recmtno=" + recmtno +
                ", recomno=" + recomno +
                ", content='" + content + '\'' +
                ", memberid='" + memberid + '\'' +
                ", recmtdate=" + recmtdate +
                ", membernick='" + membernick + '\'' +
                '}';
    }
}
