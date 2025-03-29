package com.exam.models;

import java.util.HashSet;
import java.util.Set;

public class Lop {
    private String maLop;
    private String tenLop;
    private Set<SinhVien> sinhViens;
    private Set<GiaoVienDangKy> giaoVienDangKys;

    public Lop() {
        this.sinhViens = new HashSet<>();
        this.giaoVienDangKys = new HashSet<>();
    }

    public Lop(String maLop, String tenLop) {
        this();
        this.maLop = maLop;
        this.tenLop = tenLop;
    }

    // Getters and Setters
    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop != null ? maLop.trim().toUpperCase() : null;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public Set<SinhVien> getSinhViens() {
        return sinhViens;
    }

    public void setSinhViens(Set<SinhVien> sinhViens) {
        this.sinhViens = sinhViens;
    }

    public Set<GiaoVienDangKy> getGiaoVienDangKys() {
        return giaoVienDangKys;
    }

    public void setGiaoVienDangKys(Set<GiaoVienDangKy> giaoVienDangKys) {
        this.giaoVienDangKys = giaoVienDangKys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lop lop = (Lop) o;
        return maLop != null && maLop.equals(lop.maLop);
    }

    @Override
    public int hashCode() {
        return maLop != null ? maLop.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Lop{" +
                "maLop='" + maLop + '\'' +
                ", tenLop='" + tenLop + '\'' +
                '}';
    }
}