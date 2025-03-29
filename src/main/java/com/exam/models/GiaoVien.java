package com.exam.models;

import java.util.HashSet;
import java.util.Set;

public class GiaoVien {
    private String maGV;
    private String ho;
    private String ten;
    private String soDTLL;  // Số điện thoại liên lạc
    private String diaChi;
    private Set<BoDe> boDes;
    private Set<GiaoVienDangKy> giaoVienDangKys;

    public GiaoVien() {
        this.boDes = new HashSet<>();
        this.giaoVienDangKys = new HashSet<>();
    }

    public GiaoVien(String maGV, String ho, String ten) {
        this();
        this.maGV = maGV;
        this.ho = ho;
        this.ten = ten;
    }

    // Getters and Setters
    public String getMaGV() {
        return maGV;
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV != null ? maGV.trim().toUpperCase() : null;
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

    public String getSoDTLL() {
        return soDTLL;
    }

    public void setSoDTLL(String soDTLL) {
        this.soDTLL = soDTLL;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getHoTen() {
        return (ho != null ? ho : "") + " " + (ten != null ? ten : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiaoVien giaoVien = (GiaoVien) o;
        return maGV != null && maGV.equals(giaoVien.maGV);
    }

    @Override
    public int hashCode() {
        return maGV != null ? maGV.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GiaoVien{" +
                "maGV='" + maGV + '\'' +
                ", ho='" + ho + '\'' +
                ", ten='" + ten + '\'' +
                ", soDTLL='" + soDTLL + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}