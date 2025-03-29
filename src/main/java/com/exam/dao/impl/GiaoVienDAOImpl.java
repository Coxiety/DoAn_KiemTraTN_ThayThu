package com.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.exam.dao.GiaoVienDAO;
import com.exam.models.GiaoVien;
import com.exam.utils.DatabaseConfig;

/**
 * Implementation of GiaoVienDAO interface
 */
public class GiaoVienDAOImpl implements GiaoVienDAO {
    
    private static final Logger LOGGER = Logger.getLogger(GiaoVienDAOImpl.class.getName());
    
    @Override
    public void insert(GiaoVien giaoVien) throws SQLException {
        String sql = "INSERT INTO GIAOVIEN (MAGV, HO, TEN, SODTLL, DIACHI) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, giaoVien.getMaGV());
            stmt.setString(2, giaoVien.getHo());
            stmt.setString(3, giaoVien.getTen());
            stmt.setString(4, giaoVien.getSoDTLL());
            stmt.setString(5, giaoVien.getDiaChi());
            stmt.executeUpdate();
        }
    }
    
    @Override
    public GiaoVien findById(String maGV) throws SQLException {
        String sql = "SELECT * FROM GIAOVIEN WHERE MAGV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    @Override
    public List<GiaoVien> findAll() throws SQLException {
        List<GiaoVien> giaoViens = new ArrayList<>();
        String sql = "SELECT * FROM GIAOVIEN ORDER BY HO, TEN";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                giaoViens.add(extractFromResultSet(rs));
            }
        }
        return giaoViens;
    }
    
    @Override
    public void update(GiaoVien giaoVien) throws SQLException {
        String sql = "UPDATE GIAOVIEN SET HO = ?, TEN = ?, SODTLL = ?, DIACHI = ? WHERE MAGV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, giaoVien.getHo());
            stmt.setString(2, giaoVien.getTen());
            stmt.setString(3, giaoVien.getSoDTLL());
            stmt.setString(4, giaoVien.getDiaChi());
            stmt.setString(5, giaoVien.getMaGV());
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void delete(String maGV) throws SQLException {
        String sql = "DELETE FROM GIAOVIEN WHERE MAGV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGV);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public List<GiaoVien> findByDepartment(String maKhoa) throws SQLException {
        // Since the GiaoVien table no longer has a MAKH field, we would need to
        // join with another table that maps teachers to departments, but since
        // that's not in the current schema, we'll return an empty list
        return new ArrayList<>();
    }
    
    @Override
    public List<GiaoVien> findBySubject(String maMH) throws SQLException {
        List<GiaoVien> giaoViens = new ArrayList<>();
        String sql = "SELECT DISTINCT g.* FROM GIAOVIEN g " +
                     "JOIN GIAOVIEN_DANGKY gvdk ON g.MAGV = gvdk.MAGV " +
                     "WHERE gvdk.MAMH = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    giaoViens.add(extractFromResultSet(rs));
                }
            }
        }
        return giaoViens;
    }
    
    @Override
    public List<GiaoVien> searchByName(String name) throws SQLException {
        List<GiaoVien> giaoViens = new ArrayList<>();
        String sql = "SELECT * FROM GIAOVIEN WHERE HO + ' ' + TEN LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    giaoViens.add(extractFromResultSet(rs));
                }
            }
        }
        return giaoViens;
    }
    
    @Override
    public boolean exists(String maGV) throws SQLException {
        String sql = "SELECT COUNT(*) FROM GIAOVIEN WHERE MAGV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGV);
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
        String sql = "SELECT COUNT(*) FROM GIAOVIEN";
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
     * Extract a GiaoVien object from a ResultSet
     * @param rs ResultSet
     * @return GiaoVien object
     * @throws SQLException if database error occurs
     */
    private GiaoVien extractFromResultSet(ResultSet rs) throws SQLException {
        GiaoVien giaoVien = new GiaoVien();
        giaoVien.setMaGV(rs.getString("MAGV"));
        giaoVien.setHo(rs.getString("HO"));
        giaoVien.setTen(rs.getString("TEN"));
        giaoVien.setSoDTLL(rs.getString("SODTLL"));
        giaoVien.setDiaChi(rs.getString("DIACHI"));
        return giaoVien;
    }
}