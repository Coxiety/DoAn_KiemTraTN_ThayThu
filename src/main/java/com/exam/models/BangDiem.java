package com.exam.models;

import java.util.Date;

public class BangDiem {
    private String maSV;
    private String maMH;
    private Short lan;      // 1 or 2
    private Date ngayThi;
    private Float diem;     // 0-10

    public BangDiem() {
    }

    public BangDiem(String maSV, String maMH, Short lan) {
        this.maSV = maSV;
        this.maMH = maMH;
        this.lan = lan;
    }

    // Getters and Setters
    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV != null ? maSV.trim().toUpperCase() : null;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH != null ? maMH.trim().toUpperCase() : null;
    }

    public Short getLan() {
        return lan;
    }

    public void setLan(Short lan) {
        if (lan != null && lan >= 1 && lan <= 2) {
            this.lan = lan;
        } else {
            throw new IllegalArgumentException("Lần thi must be between 1 and 2");
        }
    }

    public Date getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(Date ngayThi) {
        this.ngayThi = ngayThi;
    }

    public Float getDiem() {
        return diem;
    }

    public void setDiem(Float diem) {
        if (diem != null && diem >= 0 && diem <= 10) {
            this.diem = diem;
        } else {
            throw new IllegalArgumentException("Điểm must be between 0 and 10");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BangDiem bangDiem = (BangDiem) o;
        if (maSV == null || maMH == null || lan == null) return false;
        return maSV.equals(bangDiem.maSV) && 
               maMH.equals(bangDiem.maMH) && 
               lan.equals(bangDiem.lan);
    }

    @Override
    public int hashCode() {
        int result = maSV != null ? maSV.hashCode() : 0;
        result = 31 * result + (maMH != null ? maMH.hashCode() : 0);
        result = 31 * result + (lan != null ? lan.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BangDiem{" +
                "maSV='" + maSV + '\'' +
                ", maMH='" + maMH + '\'' +
                ", lan=" + lan +
                ", ngayThi=" + ngayThi +
                ", diem=" + diem +
                '}';
    }
}