package com.exam.models;

import java.util.Date;

/**
 * Model class for SinhVien entity
 * Represents a student in the system
 */
public class SinhVien {
    private String maSV;
    private String ho;
    private String ten;
    private Date ngaySinh;
    private String diaChi;
    private String maLop;

    /**
     * Default constructor
     */
    public SinhVien() {
    }

    /**
     * Constructor with fields
     */
    public SinhVien(String maSV, String ho, String ten, Date ngaySinh, String diaChi, String maLop) {
        this.maSV = maSV;
        this.ho = ho;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.maLop = maLop;
    }

    /**
     * Get student ID
     * @return student ID
     */
    public String getMaSV() {
        return maSV;
    }

    /**
     * Set student ID
     * @param maSV student ID
     */
    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    /**
     * Get student's last name
     * @return last name
     */
    public String getHo() {
        return ho;
    }

    /**
     * Set student's last name
     * @param ho last name
     */
    public void setHo(String ho) {
        this.ho = ho;
    }

    /**
     * Get student's first name
     * @return first name
     */
    public String getTen() {
        return ten;
    }

    /**
     * Set student's first name
     * @param ten first name
     */
    public void setTen(String ten) {
        this.ten = ten;
    }

    /**
     * Get student's full name
     * @return full name
     */
    public String getHoTen() {
        if (ho == null || ten == null) {
            return "";
        }
        return ho + " " + ten;
    }

    /**
     * Get student's birth date
     * @return birth date
     */
    public Date getNgaySinh() {
        return ngaySinh;
    }

    /**
     * Set student's birth date
     * @param ngaySinh birth date
     */
    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    /**
     * Get student's address
     * @return address
     */
    public String getDiaChi() {
        return diaChi;
    }

    /**
     * Set student's address
     * @param diaChi address
     */
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    /**
     * Get student's class ID
     * @return class ID
     */
    public String getMaLop() {
        return maLop;
    }

    /**
     * Set student's class ID
     * @param maLop class ID
     */
    public void setMaLop(String maLop) {
        this.maLop = maLop;
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