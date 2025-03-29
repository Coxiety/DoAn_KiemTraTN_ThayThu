package com.exam.models;

/**
 * Model class for GiaoVien entity
 * Represents a teacher in the system
 */
public class GiaoVien {
    private String maGV;
    private String ho;
    private String ten;
    private String soDTLL; // Phone number field
    private String diaChi;
    
    /**
     * Default constructor
     */
    public GiaoVien() {
    }
    
    /**
     * Constructor with fields
     */
    public GiaoVien(String maGV, String ho, String ten, String soDTLL, String diaChi) {
        this.maGV = maGV;
        this.ho = ho;
        this.ten = ten;
        this.soDTLL = soDTLL;
        this.diaChi = diaChi;
    }
    
    /**
     * Get teacher ID
     * @return teacher ID
     */
    public String getMaGV() {
        return maGV;
    }
    
    /**
     * Set teacher ID
     * @param maGV teacher ID
     */
    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }
    
    /**
     * Get teacher's last name
     * @return last name
     */
    public String getHo() {
        return ho;
    }
    
    /**
     * Set teacher's last name
     * @param ho last name
     */
    public void setHo(String ho) {
        this.ho = ho;
    }
    
    /**
     * Get teacher's first name
     * @return first name
     */
    public String getTen() {
        return ten;
    }
    
    /**
     * Set teacher's first name
     * @param ten first name
     */
    public void setTen(String ten) {
        this.ten = ten;
    }
    
    /**
     * Get teacher's full name
     * @return full name
     */
    public String getHoTen() {
        return ho + " " + ten;
    }
    
    /**
     * Get teacher's phone number
     * @return phone number
     */
    public String getSoDTLL() {
        return soDTLL;
    }
    
    /**
     * Set teacher's phone number
     * @param soDTLL phone number
     */
    public void setSoDTLL(String soDTLL) {
        this.soDTLL = soDTLL;
    }
    
    /**
     * Get teacher's address
     * @return address
     */
    public String getDiaChi() {
        return diaChi;
    }
    
    /**
     * Set teacher's address
     * @param diaChi address
     */
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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