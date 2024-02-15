package com.uni.mental.mentalInfo.model.dto;


public class AttachDto {

    private int attachno;
    private String attachoriname;
    private String attachnewname;


    public int getAttachno() {
        return attachno;
    }

    public void setAttachno(int attachno) {
        this.attachno = attachno;
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


    @Override
    public String toString() {
        return "AttachDto{" +
                "attachno=" + attachno +
                ", attachoriname='" + attachoriname + '\'' +
                ", attachnewname='" + attachnewname + '\'' +
                '}';
    }
}
