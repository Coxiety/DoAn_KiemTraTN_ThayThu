package com.exam.models;

public class BoDe {
    private Integer cauHoi;
    private String maMH;
    private String maGV;
    private String trinhDo;  // 'A', 'B', 'C'
    private String noiDung;
    private String a;
    private String b;
    private String c;
    private String d;
    private String dapAn;    // 'A', 'B', 'C', 'D'

    public BoDe() {
    }

    public BoDe(String maMH, String maGV, String trinhDo, String noiDung) {
        this.maMH = maMH;
        this.maGV = maGV;
        this.trinhDo = trinhDo;
        this.noiDung = noiDung;
    }

    // Getters and Setters
    public Integer getCauHoi() {
        return cauHoi;
    }

    public void setCauHoi(Integer cauHoi) {
        this.cauHoi = cauHoi;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH != null ? maMH.trim().toUpperCase() : null;
    }

    public String getMaGV() {
        return maGV;
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV != null ? maGV.trim().toUpperCase() : null;
    }

    public String getTrinhDo() {
        return trinhDo;
    }

    public void setTrinhDo(String trinhDo) {
        if (trinhDo != null && trinhDo.matches("[ABC]")) {
            this.trinhDo = trinhDo;
        } else {
            throw new IllegalArgumentException("Trình độ must be 'A', 'B', or 'C'");
        }
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getDapAn() {
        return dapAn;
    }

    public void setDapAn(String dapAn) {
        if (dapAn != null && dapAn.matches("[ABCD]")) {
            this.dapAn = dapAn;
        } else {
            throw new IllegalArgumentException("Đáp án must be 'A', 'B', 'C', or 'D'");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoDe boDe = (BoDe) o;
        return cauHoi != null && cauHoi.equals(boDe.cauHoi);
    }

    @Override
    public int hashCode() {
        return cauHoi != null ? cauHoi.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BoDe{" +
                "cauHoi=" + cauHoi +
                ", maMH='" + maMH + '\'' +
                ", maGV='" + maGV + '\'' +
                ", trinhDo='" + trinhDo + '\'' +
                ", noiDung='" + noiDung + '\'' +
                ", dapAn='" + dapAn + '\'' +
                '}';
    }
}