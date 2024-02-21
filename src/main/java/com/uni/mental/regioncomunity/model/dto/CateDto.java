package com.uni.mental.regioncomunity.model.dto;

public class CateDto {
    private int cateno;
    private String catename;
    private String catename2;

    public int getCateno() {
        return cateno;
    }

    public void setCateno(int cateno) {
        this.cateno = cateno;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public String getCatename2() {
        return catename2;
    }

    public void setCatename2(String catename2) {
        this.catename2 = catename2;
    }

    @Override
    public String toString() {
        return "CateDto{" +
                "cateno=" + cateno +
                ", catename='" + catename + '\'' +
                ", catename2='" + catename2 + '\'' +
                '}';
    }
}
