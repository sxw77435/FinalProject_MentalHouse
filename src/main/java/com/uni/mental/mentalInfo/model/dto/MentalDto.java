package com.uni.mental.mentalInfo.model.dto;

public class MentalDto {

    private int mentalinfono;
    private String mentalname;
    private String mentalcontent;
    private String mentalcause;
    private String mentalcase;
    private String mentalsymptom;
    private String mentaldrug;
    private String mentaltreat;
    private int attachno;
    private  String attachoriname;

    public int getMentalinfono() {
        return mentalinfono;
    }

    public void setMentalinfono(int mentalinfono) {
        this.mentalinfono = mentalinfono;
    }

    public String getMentalname() {
        return mentalname;
    }

    public void setMentalname(String mentalname) {
        this.mentalname = mentalname;
    }

    public String getMentalcontent() {
        return mentalcontent;
    }

    public void setMentalcontent(String mentalcontent) {
        this.mentalcontent = mentalcontent;
    }

    public String getMentalcause() {
        return mentalcause;
    }

    public void setMentalcause(String mentalcause) {
        this.mentalcause = mentalcause;
    }

    public String getMentalcase() {
        return mentalcase;
    }

    public void setMentalcase(String mentalcase) {
        this.mentalcase = mentalcase;
    }

    public String getMentalsymptom() {
        return mentalsymptom;
    }

    public void setMentalsymptom(String mentalsymptom) {
        this.mentalsymptom = mentalsymptom;
    }

    public String getMentaldrug() {
        return mentaldrug;
    }

    public void setMentaldrug(String mentaldrug) {
        this.mentaldrug = mentaldrug;
    }

    public String getMentaltreat() {
        return mentaltreat;
    }

    public void setMentaltreat(String mentaltreat) {
        this.mentaltreat = mentaltreat;
    }

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

    @Override
    public String toString() {
        return "MentalDto{" +
                "mentalinfono=" + mentalinfono +
                ", mentalname='" + mentalname + '\'' +
                ", mentalcontent='" + mentalcontent + '\'' +
                ", mentalcause='" + mentalcause + '\'' +
                ", mentalcase='" + mentalcase + '\'' +
                ", mentalsymptom='" + mentalsymptom + '\'' +
                ", mentaldrug='" + mentaldrug + '\'' +
                ", mentaltreat='" + mentaltreat + '\'' +
                ", attachno=" + attachno +
                ", attachoriname='" + attachoriname + '\'' +
                '}';
    }
}
