package com.exam.dao.impl;

import com.exam.dao.LopDAO;
import com.exam.models.Lop;
import com.exam.utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LopDAOImpl implements LopDAO {
    private static final Logger LOGGER = Logger.getLogger(LopDAOImpl.class.getName());

    @Override
    public void insert(Lop lop) throws SQLException {
        String sql = "INSERT INTO Lop (MALOP, TENLOP) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lop.getMaLop());
            stmt.setString(2, lop.getTenLop());
            stmt.executeUpdate();
        }
    }

    @Override
    public Lop findById(String maLop) throws SQLException {
        String sql = "SELECT * FROM Lop WHERE MALOP = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maLop);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractLopFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Lop> findAll() throws SQLException {
        List<Lop> lops = new ArrayList<>();
        String sql = "SELECT * FROM Lop ORDER BY TENLOP";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lops.add(extractLopFromResultSet(rs));
            }
        }
        return lops;
    }

    @Override
    public void update(Lop lop) throws SQLException {
        String sql = "UPDATE Lop SET TENLOP = ? WHERE MALOP = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, lop.getTenLop());
            stmt.setString(2, lop.getMaLop());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String maLop) throws SQLException {
        String sql = "DELETE FROM Lop WHERE MALOP = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maLop);
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean exists(String maLop) throws SQLException {
        return existsByCode(maLop);
    }

    @Override
    public long count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Lop";
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
    public Lop findByName(String tenLop) throws SQLException {
        String sql = "SELECT * FROM Lop WHERE TENLOP = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenLop);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractLopFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Lop> searchByName(String searchTerm) throws SQLException {
        List<Lop> lops = new ArrayList<>();
        String sql = "SELECT * FROM Lop WHERE TENLOP LIKE ? ORDER BY TENLOP";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lops.add(extractLopFromResultSet(rs));
                }
            }
        }
        return lops;
    }

    @Override
    public List<Lop> getClassesWithStudents() throws SQLException {
        List<Lop> lops = new ArrayList<>();
        String sql = "SELECT DISTINCT l.* FROM Lop l " +
                    "INNER JOIN Sinhvien sv ON l.MALOP = sv.MALOP " +
                    "ORDER BY l.TENLOP";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lops.add(extractLopFromResultSet(rs));
            }
        }
        return lops;
    }

    @Override
    public List<Lop> getClassesWithExams(String maMH) throws SQLException {
        List<Lop> lops = new ArrayList<>();
        String sql = "SELECT DISTINCT l.* FROM Lop l " +
                    "INNER JOIN Giaovien_Dangky gvdk ON l.MALOP = gvdk.MALOP " +
                    "WHERE gvdk.MAMH = ? " +
                    "ORDER BY l.TENLOP";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lops.add(extractLopFromResultSet(rs));
                }
            }
        }
        return lops;
    }

    @Override
    public List<Lop> getClassesByTeacher(String maGV) throws SQLException {
        List<Lop> lops = new ArrayList<>();
        String sql = "SELECT DISTINCT l.* FROM Lop l " +
                    "INNER JOIN Giaovien_Dangky gvdk ON l.MALOP = gvdk.MALOP " +
                    "WHERE gvdk.MAGV = ? " +
                    "ORDER BY l.TENLOP";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGV);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lops.add(extractLopFromResultSet(rs));
                }
            }
        }
        return lops;
    }

    @Override
    public boolean existsByCode(String maLop) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Lop WHERE MALOP = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maLop);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public boolean existsByName(String tenLop) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Lop WHERE TENLOP = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenLop);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public int getStudentCount(String maLop) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Sinhvien WHERE MALOP = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maLop);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    private Lop extractLopFromResultSet(ResultSet rs) throws SQLException {
        Lop lop = new Lop();
        lop.setMaLop(rs.getString("MALOP"));
        lop.setTenLop(rs.getString("TENLOP"));
        return lop;
    }
}