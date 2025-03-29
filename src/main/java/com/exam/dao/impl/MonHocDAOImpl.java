package com.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.exam.dao.MonHocDAO;
import com.exam.models.MonHoc;
import com.exam.utils.DatabaseConfig;

public class MonHocDAOImpl implements MonHocDAO {
    private static final Logger LOGGER = Logger.getLogger(MonHocDAOImpl.class.getName());

    @Override
    public void insert(MonHoc monHoc) throws SQLException {
        String sql = "INSERT INTO MONHOC (MAMH, TENMH) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, monHoc.getMaMH());
            stmt.setString(2, monHoc.getTenMH());
            stmt.executeUpdate();
        }
    }

    @Override
    public MonHoc findById(String maMH) throws SQLException {
        String sql = "SELECT * FROM MONHOC WHERE MAMH = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractMonHocFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<MonHoc> findAll() throws SQLException {
        List<MonHoc> monHocs = new ArrayList<>();
        String sql = "SELECT * FROM MONHOC ORDER BY TENMH";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                monHocs.add(extractMonHocFromResultSet(rs));
            }
        }
        return monHocs;
    }

    @Override
    public void update(MonHoc monHoc) throws SQLException {
        String sql = "UPDATE MONHOC SET TENMH = ? WHERE MAMH = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, monHoc.getTenMH());
            stmt.setString(2, monHoc.getMaMH());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String maMH) throws SQLException {
        String sql = "DELETE FROM MONHOC WHERE MAMH = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<MonHoc> findByTeacher(String maGV) throws SQLException {
        List<MonHoc> monHocs = new ArrayList<>();
        String sql = 
            "SELECT DISTINCT m.* FROM MONHOC m " +
            "INNER JOIN GIAOVIEN_DANGKY gvdk ON m.MAMH = gvdk.MAMH " +
            "WHERE gvdk.MAGV = ? " +
            "ORDER BY m.TENMH";
            
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGV);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    monHocs.add(extractMonHocFromResultSet(rs));
                }
            }
        }
        return monHocs;
    }

    @Override
    public List<MonHoc> searchByName(String searchTerm) throws SQLException {
        List<MonHoc> monHocs = new ArrayList<>();
        String sql = "SELECT * FROM MONHOC WHERE TENMH LIKE ? ORDER BY TENMH";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    monHocs.add(extractMonHocFromResultSet(rs));
                }
            }
        }
        return monHocs;
    }

    @Override
    public boolean exists(String maMH) throws SQLException {
        return existsByCode(maMH);
    }

    @Override
    public boolean existsByCode(String maMH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM MONHOC WHERE MAMH = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
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
        String sql = "SELECT COUNT(*) FROM MONHOC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return 0;
    }

    private MonHoc extractMonHocFromResultSet(ResultSet rs) throws SQLException {
        MonHoc monHoc = new MonHoc();
        monHoc.setMaMH(rs.getString("MAMH"));
        monHoc.setTenMH(rs.getString("TENMH"));
        return monHoc;
    }
}