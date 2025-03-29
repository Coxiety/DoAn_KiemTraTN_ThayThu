package com.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.exam.dao.SinhVienDAO;
import com.exam.models.SinhVien;
import com.exam.utils.DatabaseConfig;

public class SinhVienDAOImpl implements SinhVienDAO {
    private static final Logger LOGGER = Logger.getLogger(SinhVienDAOImpl.class.getName());

    @Override
    public void insert(SinhVien sinhVien) throws SQLException {
        String sql = "INSERT INTO Sinhvien (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sinhVien.getMaSV());
            stmt.setString(2, sinhVien.getHo());
            stmt.setString(3, sinhVien.getTen());
            stmt.setDate(4, new java.sql.Date(sinhVien.getNgaySinh().getTime()));
            stmt.setString(5, sinhVien.getDiaChi());
            stmt.setString(6, sinhVien.getMaLop());
            stmt.executeUpdate();
        }
    }

    @Override
    public SinhVien findById(String maSV) throws SQLException {
        String sql = "SELECT * FROM Sinhvien WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractSinhVienFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<SinhVien> findAll() throws SQLException {
        List<SinhVien> sinhViens = new ArrayList<>();
        String sql = "SELECT * FROM Sinhvien ORDER BY HO, TEN";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sinhViens.add(extractSinhVienFromResultSet(rs));
            }
        }
        return sinhViens;
    }

    @Override
    public void update(SinhVien sinhVien) throws SQLException {
        String sql = "UPDATE Sinhvien SET HO = ?, TEN = ?, NGAYSINH = ?, DIACHI = ?, MALOP = ? WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sinhVien.getHo());
            stmt.setString(2, sinhVien.getTen());
            stmt.setDate(3, new java.sql.Date(sinhVien.getNgaySinh().getTime()));
            stmt.setString(4, sinhVien.getDiaChi());
            stmt.setString(5, sinhVien.getMaLop());
            stmt.setString(6, sinhVien.getMaSV());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String maSV) throws SQLException {
        String sql = "DELETE FROM Sinhvien WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSV);
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean exists(String maSV) throws SQLException {
        return existsByCode(maSV);
    }

    @Override
    public long count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Sinhvien";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return 0;
    }

    @Override
    public List<SinhVien> findByClass(String maLop) throws SQLException {
        List<SinhVien> sinhViens = new ArrayList<>();
        String sql = "SELECT * FROM Sinhvien WHERE MALOP = ? ORDER BY HO, TEN";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maLop);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sinhViens.add(extractSinhVienFromResultSet(rs));
                }
            }
        }
        return sinhViens;
    }

    @Override
    public List<SinhVien> findUnassignedStudents() throws SQLException {
        List<SinhVien> sinhViens = new ArrayList<>();
        String sql = "SELECT * FROM Sinhvien WHERE MALOP IS NULL ORDER BY HO, TEN";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sinhViens.add(extractSinhVienFromResultSet(rs));
            }
        }
        return sinhViens;
    }

    @Override
    public void assignToClass(String maSV, String maLop) throws SQLException {
        String sql = "UPDATE Sinhvien SET MALOP = ? WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maLop);
            stmt.setString(2, maSV);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeFromClass(String maSV) throws SQLException {
        String sql = "UPDATE Sinhvien SET MALOP = NULL WHERE MASV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSV);
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean existsByCode(String maSV) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Sinhvien WHERE MASV = ?";
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
    public List<SinhVien> searchByName(String searchTerm) throws SQLException {
        List<SinhVien> sinhViens = new ArrayList<>();
        String sql = "SELECT * FROM Sinhvien WHERE HO LIKE ? OR TEN LIKE ? ORDER BY HO, TEN";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String term = "%" + searchTerm + "%";
            stmt.setString(1, term);
            stmt.setString(2, term);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sinhViens.add(extractSinhVienFromResultSet(rs));
                }
            }
        }
        return sinhViens;
    }

    @Override
    public String getStudentClass(String maSV) throws SQLException {
        String sql = "SELECT MALOP FROM Sinhvien WHERE MASV = ?";
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

    private SinhVien extractSinhVienFromResultSet(ResultSet rs) throws SQLException {
        SinhVien sinhVien = new SinhVien();
        sinhVien.setMaSV(rs.getString("MASV"));
        sinhVien.setHo(rs.getString("HO"));
        sinhVien.setTen(rs.getString("TEN"));
        sinhVien.setNgaySinh(rs.getDate("NGAYSINH"));
        sinhVien.setDiaChi(rs.getString("DIACHI"));
        sinhVien.setMaLop(rs.getString("MALOP"));
        return sinhVien;
    }
}