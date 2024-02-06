package com.uni.mental.mentalInfo.model.dto;

public class AttachDto {

    private int attachno;
    private String catename;
    private String attachoriname;
    private String attachnewname;
    private int attachbno;

    public int getAttachno() {
        return attachno;
    }

    public void setAttachno(int attachno) {
        this.attachno = attachno;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public String getAttachoriname() {
        return attachoriname;
    }

    public void setAttachoriname(String attachoriname) {
        this.attachoriname = attachoriname;
    }

    public String getAttachnewname() {
        return attachnewname;
    }

    public void setAttachnewname(String attachnewname) {
        this.attachnewname = attachnewname;
    }

    public int getAttachbno() {
        return attachbno;
    }

    public void setAttachbno(int attachbno) {
        this.attachbno = attachbno;
    }

    @Override
    public String toString() {
        return "AttachDto{" +
                "attachno=" + attachno +
                ", catename='" + catename + '\'' +
                ", attachoriname='" + attachoriname + '\'' +
                ", attachnewname='" + attachnewname + '\'' +
                ", attachbno=" + attachbno +
                '}';
    }
}
