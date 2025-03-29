package com.exam.dao.impl;

import com.exam.dao.GiaoVienDangKyDAO;
import com.exam.models.GiaoVienDangKy;
import com.exam.utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GiaoVienDangKyDAOImpl implements GiaoVienDangKyDAO {
    private static final Logger LOGGER = Logger.getLogger(GiaoVienDangKyDAOImpl.class.getName());

    @Override
    public void insert(GiaoVienDangKy dangKy) throws SQLException {
        String sql = "INSERT INTO GIAOVIEN_DANGKY (MAGV, MAMH, ROLE) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dangKy.getMaGV());
            stmt.setString(2, dangKy.getMaMH());
            stmt.setString(3, dangKy.getRole());
            stmt.executeUpdate();
        }
    }

    @Override
    public GiaoVienDangKy findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM GIAOVIEN_DANGKY WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<GiaoVienDangKy> findAll() throws SQLException {
        List<GiaoVienDangKy> list = new ArrayList<>();
        String sql = "SELECT * FROM GIAOVIEN_DANGKY";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(extractFromResultSet(rs));
            }
        }
        return list;
    }

    @Override
    public void update(GiaoVienDangKy dangKy) throws SQLException {
        String sql = "UPDATE GIAOVIEN_DANGKY SET ROLE = ? WHERE MAGV = ? AND MAMH = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dangKy.getRole());
            stmt.setString(2, dangKy.getMaGV());
            stmt.setString(3, dangKy.getMaMH());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM GIAOVIEN_DANGKY WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<GiaoVienDangKy> findByTeacher(String maGV) throws SQLException {
        List<GiaoVienDangKy> list = new ArrayList<>();
        String sql = "SELECT * FROM GIAOVIEN_DANGKY WHERE MAGV = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGV);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractFromResultSet(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<GiaoVienDangKy> findBySubject(String maMH) throws SQLException {
        List<GiaoVienDangKy> list = new ArrayList<>();
        String sql = "SELECT * FROM GIAOVIEN_DANGKY WHERE MAMH = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractFromResultSet(rs));
                }
            }
        }
        return list;
    }

    @Override
    public void register(String maGV, String maMH, String role) throws SQLException {
        GiaoVienDangKy dangKy = new GiaoVienDangKy();
        dangKy.setMaGV(maGV);
        dangKy.setMaMH(maMH);
        dangKy.setRole(role);
        insert(dangKy);
    }

    @Override
    public void unregister(String maGV, String maMH) throws SQLException {
        String sql = "DELETE FROM GIAOVIEN_DANGKY WHERE MAGV = ? AND MAMH = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGV);
            stmt.setString(2, maMH);
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean exists(String maGV, String maMH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM GIAOVIEN_DANGKY WHERE MAGV = ? AND MAMH = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGV);
            stmt.setString(2, maMH);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public List<GiaoVienDangKy> findByTeacherAndRole(String maGV, String role) throws SQLException {
        List<GiaoVienDangKy> list = new ArrayList<>();
        String sql = "SELECT * FROM GIAOVIEN_DANGKY WHERE MAGV = ? AND ROLE = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGV);
            stmt.setString(2, role);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractFromResultSet(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<GiaoVienDangKy> findBySubjectAndRole(String maMH, String role) throws SQLException {
        List<GiaoVienDangKy> list = new ArrayList<>();
        String sql = "SELECT * FROM GIAOVIEN_DANGKY WHERE MAMH = ? AND ROLE = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
            stmt.setString(2, role);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractFromResultSet(rs));
                }
            }
        }
        return list;
    }

    @Override
    public boolean exists(Integer id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM GIAOVIEN_DANGKY WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
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
        String sql = "SELECT COUNT(*) FROM GIAOVIEN_DANGKY";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return 0;
    }

    private GiaoVienDangKy extractFromResultSet(ResultSet rs) throws SQLException {
        GiaoVienDangKy dangKy = new GiaoVienDangKy();
        dangKy.setId(rs.getInt("ID"));
        dangKy.setMaGV(rs.getString("MAGV"));
        dangKy.setMaMH(rs.getString("MAMH"));
        dangKy.setRole(rs.getString("ROLE"));
        return dangKy;
    }
}