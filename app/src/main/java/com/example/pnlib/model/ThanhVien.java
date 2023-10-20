package com.example.pnlib.model;

public class ThanhVien {
    private int maTV;
    private String hoTenTV;
    private String namSinh;

    private String cccd;

    public ThanhVien() {
    }

    public ThanhVien(int maTV, String hoTenTV, String namSinh, String cccd) {
        this.maTV = maTV;
        this.hoTenTV = hoTenTV;
        this.namSinh = namSinh;
        this.cccd = cccd;
    }

    public ThanhVien(String hoTenTV, String namSinh) {
        this.hoTenTV = hoTenTV;
        this.namSinh = namSinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTenTV() {
        return hoTenTV;
    }

    public void setHoTenTV(String hoTenTV) {
        this.hoTenTV = hoTenTV;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }
}
