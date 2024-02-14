package com.uni.mental.mentalAddress.model.dto;

public class AddressDto {
    private int facilityno;
    private String facilityname;
    private String facilityaddress;
    private double facilitylatitude;
    private double facilitylongitude;

    public int getFacilityno() {
        return facilityno;
    }

    public void setFacilityno(int facilityno) {
        this.facilityno = facilityno;
    }

    public String getFacilityname() {
        return facilityname;
    }

    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
    }

    public String getFacilityaddress() {
        return facilityaddress;
    }

    public void setFacilityaddress(String facilityaddress) {
        this.facilityaddress = facilityaddress;
    }

    public double getFacilitylatitude() {
        return facilitylatitude;
    }

    public void setFacilitylatitude(double facilitylatitude) {
        this.facilitylatitude = facilitylatitude;
    }

    public double getFacilitylongitude() {
        return facilitylongitude;
    }

    public void setFacilitylongitude(double facilitylongitude) {
        this.facilitylongitude = facilitylongitude;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "facilityno=" + facilityno +
                ", facilityname='" + facilityname + '\'' +
                ", facilityaddress='" + facilityaddress + '\'' +
                ", facilitylatitude=" + facilitylatitude +
                ", facilitylongitude=" + facilitylongitude +
                '}';
    }
}
