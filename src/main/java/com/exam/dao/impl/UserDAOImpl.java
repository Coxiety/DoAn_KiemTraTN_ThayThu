package com.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.exam.dao.UserDAO;
import com.exam.models.User;
import com.exam.utils.DatabaseConfig;

/**
 * Implementation of the UserDAO interface
 */
public class UserDAOImpl implements UserDAO {
    
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class.getName());
    
    @Override
    public User authenticate(String username, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    @Override
    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD, ROLE, MAGV, MASV) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().name());
            stmt.setString(4, user.getMaGV());
            stmt.setString(5, user.getMaSV());
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        }
    }
    
    @Override
    public User findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE ID = ?";
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
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USERS";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(extractFromResultSet(rs));
            }
        }
        return users;
    }
    
    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE USERS SET USERNAME = ?, PASSWORD = ?, ROLE = ?, MAGV = ?, MASV = ? WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().name());
            stmt.setString(4, user.getMaGV());
            stmt.setString(5, user.getMaSV());
            stmt.setInt(6, user.getId());
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM USERS WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    @Override
    public List<User> findByRole(User.Role role) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USERS WHERE ROLE = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(extractFromResultSet(rs));
                }
            }
        }
        return users;
    }
    
    @Override
    public User findByTeacher(String maGV) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE MAGV = ?";
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
    public User findByStudent(String maSV) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE MASV = ?";
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
    public boolean changePassword(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE USERS SET PASSWORD = ? WHERE ID = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }
    
    @Override
    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM USERS WHERE USERNAME = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean exists(Integer id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM USERS WHERE ID = ?";
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
        String sql = "SELECT COUNT(*) FROM USERS";
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
     * Extract a User object from a ResultSet
     * @param rs ResultSet
     * @return User object
     * @throws SQLException if database error occurs
     */
    private User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("ID"));
        user.setUsername(rs.getString("USERNAME"));
        user.setPassword(rs.getString("PASSWORD"));
        user.setRole(rs.getString("ROLE"));
        user.setMaGV(rs.getString("MAGV"));
        user.setMaSV(rs.getString("MASV"));
        return user;
    }
}