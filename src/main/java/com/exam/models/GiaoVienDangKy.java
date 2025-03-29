package com.exam.models;

public class GiaoVienDangKy {
    private int id;
    private String maGV;
    private String maMH;
    private String role;

    public GiaoVienDangKy() {
    }

    public GiaoVienDangKy(String maGV, String maMH, String role) {
        this.maGV = maGV;
        this.maMH = maMH;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaGV() {
        return maGV;
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "GiaoVienDangKy{" +
                "id=" + id +
                ", maGV='" + maGV + '\'' +
                ", maMH='" + maMH + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}