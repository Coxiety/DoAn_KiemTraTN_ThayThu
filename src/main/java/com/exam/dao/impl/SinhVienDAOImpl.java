package com.exam.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.exam.dao.SinhVienDAO;
import com.exam.models.SinhVien;
import com.exam.utils.DatabaseConfig;

/**
 * Implementation of SinhVienDAO interface
 */
public class SinhVienDAOImpl implements SinhVienDAO {
    
    private static final Logger LOGGER = Logger.getLogger(SinhVienDAOImpl.class.getName());
    
    @Override
    public void insert(SinhVien sinhVien) throws SQLException {
        String sql = "INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sinhVien.getMaSV());
            stmt.setString(2, sinhVien.getHo());
            stmt.setString(3, sinhVien.getTen());
            if (sinhVien.getNgaySinh() != null) {
                stmt.setDate(4, new java.sql.Date(sinhVien.getNgaySinh().getTime()));
            } else {
                stmt.setNull(4, Types.DATE);
            }
            stmt.setString(5, sinhVien.getDiaChi());
            stmt.setString(6, sinhVien.getMaLop());
            stmt.executeUpdate();
        }
    }
    
    @Override
    public SinhVien findById(String maSV) throws SQLException {
        String sql = "SELECT * FROM SINHVIEN WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    @Override
    public List<SinhVien> findAll() throws SQLException {
        List<SinhVien> sinhViens = new ArrayList<>();
        String sql = "SELECT * FROM SINHVIEN ORDER BY HO, TEN";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sinhViens.add(extractFromResultSet(rs));
            }
        }
        return sinhViens;
    }
    
    @Override
    public void update(SinhVien sinhVien) throws SQLException {
        String sql = "UPDATE SINHVIEN SET HO = ?, TEN = ?, NGAYSINH = ?, DIACHI = ?, MALOP = ? WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sinhVien.getHo());
            stmt.setString(2, sinhVien.getTen());
            if (sinhVien.getNgaySinh() != null) {
                stmt.setDate(3, new java.sql.Date(sinhVien.getNgaySinh().getTime()));
            } else {
                stmt.setNull(3, Types.DATE);
            }
            stmt.setString(4, sinhVien.getDiaChi());
            stmt.setString(5, sinhVien.getMaLop());
            stmt.setString(6, sinhVien.getMaSV());
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void delete(String maSV) throws SQLException {
        String sql = "DELETE FROM SINHVIEN WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSV);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public List<SinhVien> findByClass(String maLop) throws SQLException {
        List<SinhVien> sinhViens = new ArrayList<>();
        String sql = "SELECT * FROM SINHVIEN WHERE MALOP = ? ORDER BY HO, TEN";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maLop);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sinhViens.add(extractFromResultSet(rs));
                }
            }
        }
        return sinhViens;
    }
    
    @Override
    public List<SinhVien> searchByName(String name) throws SQLException {
        List<SinhVien> sinhViens = new ArrayList<>();
        String sql = "SELECT * FROM SINHVIEN WHERE HO + ' ' + TEN LIKE ? ORDER BY HO, TEN";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sinhViens.add(extractFromResultSet(rs));
                }
            }
        }
        return sinhViens;
    }
    
    @Override
    public List<SinhVien> findByExamAttempt(String maMH, int lan) throws SQLException {
        List<SinhVien> sinhViens = new ArrayList<>();
        String sql = "SELECT s.* FROM SINHVIEN s " +
                     "JOIN BANGDIEM b ON s.MASV = b.MASV " +
                     "WHERE b.MAMH = ? AND b.LAN = ? " +
                     "ORDER BY s.HO, s.TEN";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
            stmt.setInt(2, lan);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sinhViens.add(extractFromResultSet(rs));
                }
            }
        }
        return sinhViens;
    }
    
    @Override
    public String getStudentClass(String maSV) throws SQLException {
        String sql = "SELECT MALOP FROM SINHVIEN WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("MALOP");
                }
            }
        }
        return null;
    }
    
    @Override
    public void assignToClass(String maSV, String maLop) throws SQLException {
        String sql = "UPDATE SINHVIEN SET MALOP = ? WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maLop);
            stmt.setString(2, maSV);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void removeFromClass(String maSV) throws SQLException {
        String sql = "UPDATE SINHVIEN SET MALOP = NULL WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSV);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public boolean exists(String maSV) throws SQLException {
        String sql = "SELECT COUNT(*) FROM SINHVIEN WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    @Override
    public long count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM SINHVIEN";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return 0;
    }
    
    /**
     * Extract a SinhVien object from a ResultSet
     * @param rs ResultSet
     * @return SinhVien object
     * @throws SQLException if database error occurs
     */
    private SinhVien extractFromResultSet(ResultSet rs) throws SQLException {
        SinhVien sinhVien = new SinhVien();
        sinhVien.setMaSV(rs.getString("MASV"));
        sinhVien.setHo(rs.getString("HO"));
        sinhVien.setTen(rs.getString("TEN"));
        
        Date ngaySinh = rs.getDate("NGAYSINH");
        if (ngaySinh != null) {
            sinhVien.setNgaySinh(new java.util.Date(ngaySinh.getTime()));
        }
        
        sinhVien.setDiaChi(rs.getString("DIACHI"));
        sinhVien.setMaLop(rs.getString("MALOP"));
        return sinhVien;
    }
}