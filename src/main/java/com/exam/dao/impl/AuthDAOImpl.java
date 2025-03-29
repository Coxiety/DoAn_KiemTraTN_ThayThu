package com.exam.dao.impl;

import com.exam.dao.AuthDAO;
import com.exam.models.GiaoVien;
import com.exam.models.SinhVien;
import com.exam.utils.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class AuthDAOImpl implements AuthDAO {
    private static final Logger LOGGER = Logger.getLogger(AuthDAOImpl.class.getName());

    // SQL queries
    private static final String AUTH_TEACHER_SQL = 
        "SELECT g.* FROM Giaovien g INNER JOIN Users u ON g.MAGV = u.UserID " +
        "WHERE u.Login = ? AND u.Password = ? AND u.Role = 'TEACHER'";
    
    private static final String AUTH_STUDENT_SQL = 
        "SELECT s.* FROM Sinhvien s INNER JOIN Users u ON s.MASV = u.UserID " +
        "WHERE u.Login = ? AND u.Password = ? AND u.Role = 'STUDENT'";
    
    private static final String REGISTER_USER_SQL = 
        "INSERT INTO Users (UserID, Login, Password, Role) VALUES (?, ?, ?, ?)";
    
    private static final String CHECK_USER_SQL = 
        "SELECT COUNT(*) FROM Users WHERE Login = ?";
    
    private static final String UPDATE_PASSWORD_SQL = 
        "UPDATE Users SET Password = ? WHERE Login = ? AND Password = ?";

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Error hashing password", e);
            throw new RuntimeException("Error hashing password", e);
        }
    }

    @Override
    public GiaoVien authenticateTeacher(String maGV, String password) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(AUTH_TEACHER_SQL)) {
            
            stmt.setString(1, maGV);
            stmt.setString(2, hashPassword(password));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    GiaoVien giaoVien = new GiaoVien();
                    giaoVien.setMaGV(rs.getString("MAGV"));
                    giaoVien.setHo(rs.getString("HO"));
                    giaoVien.setTen(rs.getString("TEN"));
                    giaoVien.setSoDTLL(rs.getString("SODTLL"));
                    giaoVien.setDiaChi(rs.getString("DIACHI"));
                    return giaoVien;
                }
            }
        }
        return null;
    }

    @Override
    public SinhVien authenticateStudent(String maSV, String password) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(AUTH_STUDENT_SQL)) {
            
            stmt.setString(1, maSV);
            stmt.setString(2, hashPassword(password));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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
        }
        return null;
    }

    @Override
    public boolean registerTeacher(GiaoVien giaoVien, String password) throws SQLException {
        if (userExists(giaoVien.getMaGV())) {
            return false;
        }

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Insert into Users table
                try (PreparedStatement stmt = conn.prepareStatement(REGISTER_USER_SQL)) {
                    stmt.setString(1, giaoVien.getMaGV());
                    stmt.setString(2, giaoVien.getMaGV()); // Using MAGV as login
                    stmt.setString(3, hashPassword(password));
                    stmt.setString(4, "TEACHER");
                    stmt.executeUpdate();
                }

                // Insert into Giaovien table
                String insertTeacherSQL = "INSERT INTO Giaovien (MAGV, HO, TEN, SODTLL, DIACHI) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertTeacherSQL)) {
                    stmt.setString(1, giaoVien.getMaGV());
                    stmt.setString(2, giaoVien.getHo());
                    stmt.setString(3, giaoVien.getTen());
                    stmt.setString(4, giaoVien.getSoDTLL());
                    stmt.setString(5, giaoVien.getDiaChi());
                    stmt.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean registerStudent(SinhVien sinhVien, String password) throws SQLException {
        if (userExists(sinhVien.getMaSV())) {
            return false;
        }

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Insert into Users table
                try (PreparedStatement stmt = conn.prepareStatement(REGISTER_USER_SQL)) {
                    stmt.setString(1, sinhVien.getMaSV());
                    stmt.setString(2, "sv"); // Using common login for students
                    stmt.setString(3, hashPassword(password));
                    stmt.setString(4, "STUDENT");
                    stmt.executeUpdate();
                }

                // Insert into Sinhvien table
                String insertStudentSQL = "INSERT INTO Sinhvien (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertStudentSQL)) {
                    stmt.setString(1, sinhVien.getMaSV());
                    stmt.setString(2, sinhVien.getHo());
                    stmt.setString(3, sinhVien.getTen());
                    stmt.setDate(4, new java.sql.Date(sinhVien.getNgaySinh().getTime()));
                    stmt.setString(5, sinhVien.getDiaChi());
                    stmt.setString(6, sinhVien.getMaLop());
                    stmt.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean changePassword(String userId, String oldPassword, String newPassword) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_PASSWORD_SQL)) {
            
            stmt.setString(1, hashPassword(newPassword));
            stmt.setString(2, userId);
            stmt.setString(3, hashPassword(oldPassword));
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean userExists(String userId) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CHECK_USER_SQL)) {
            
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}