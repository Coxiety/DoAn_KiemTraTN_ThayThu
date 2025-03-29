package com.exam.models;

public class MonHoc {
    private String maMH;
    private String tenMH;

    public MonHoc() {
    }

    public MonHoc(String maMH, String tenMH) {
        this.maMH = maMH;
        this.tenMH = tenMH;
    }

    // Getters and Setters
    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH != null ? maMH.trim().toUpperCase() : null;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonHoc monHoc = (MonHoc) o;
        return maMH != null && maMH.equals(monHoc.maMH);
    }

    @Override
    public int hashCode() {
        return maMH != null ? maMH.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MonHoc{" +
                "maMH='" + maMH + '\'' +
                ", tenMH='" + tenMH + '\'' +
                '}';
    }
}