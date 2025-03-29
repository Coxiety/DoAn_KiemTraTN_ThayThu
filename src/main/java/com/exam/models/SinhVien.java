package com.exam.models;

import java.util.Date;

public class SinhVien {
    private String maSV;
    private String ho;
    private String ten;
    private Date ngaySinh;
    private String diaChi;
    private String maLop;

    public SinhVien() {
    }

    public SinhVien(String maSV, String ho, String ten) {
        this.maSV = maSV;
        this.ho = ho;
        this.ten = ten;
    }

    // Getters and Setters
    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV != null ? maSV.trim().toUpperCase() : null;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop != null ? maLop.trim().toUpperCase() : null;
    }

    public String getHoTen() {
        return (ho != null ? ho : "") + " " + (ten != null ? ten : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SinhVien sinhVien = (SinhVien) o;
        return maSV != null && maSV.equals(sinhVien.maSV);
    }

    @Override
    public int hashCode() {
        return maSV != null ? maSV.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "maSV='" + maSV + '\'' +
                ", ho='" + ho + '\'' +
                ", ten='" + ten + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", diaChi='" + diaChi + '\'' +
                ", maLop='" + maLop + '\'' +
                '}';
    }
}