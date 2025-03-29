package com.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.exam.dao.BoDeDAO;
import com.exam.models.BoDe;
import com.exam.utils.DatabaseConfig;

public class BoDeDAOImpl implements BoDeDAO {
    private static final Logger LOGGER = Logger.getLogger(BoDeDAOImpl.class.getName());

    @Override
    public void insert(BoDe boDe) throws SQLException {
        String sql = "INSERT INTO BODE (MAMH, MAGV, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, boDe.getMaMH());
            stmt.setString(2, boDe.getMaGV());
            stmt.setString(3, boDe.getTrinhDo());
            stmt.setString(4, boDe.getNoiDung());
            stmt.setString(5, boDe.getA());
            stmt.setString(6, boDe.getB());
            stmt.setString(7, boDe.getC());
            stmt.setString(8, boDe.getD());
            stmt.setString(9, boDe.getDapAn());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    boDe.setCauHoi(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public BoDe findById(Integer cauHoi) throws SQLException {
        String sql = "SELECT * FROM BODE WHERE CAUHOI = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cauHoi);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractBoDeFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<BoDe> findAll() throws SQLException {
        List<BoDe> questions = new ArrayList<>();
        String sql = "SELECT * FROM BODE ORDER BY CAUHOI";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                questions.add(extractBoDeFromResultSet(rs));
            }
        }
        return questions;
    }

    @Override
    public void update(BoDe boDe) throws SQLException {
        String sql = "UPDATE BODE SET MAMH = ?, MAGV = ?, TRINHDO = ?, NOIDUNG = ?, A = ?, B = ?, C = ?, D = ?, DAP_AN = ? WHERE CAUHOI = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, boDe.getMaMH());
            stmt.setString(2, boDe.getMaGV());
            stmt.setString(3, boDe.getTrinhDo());
            stmt.setString(4, boDe.getNoiDung());
            stmt.setString(5, boDe.getA());
            stmt.setString(6, boDe.getB());
            stmt.setString(7, boDe.getC());
            stmt.setString(8, boDe.getD());
            stmt.setString(9, boDe.getDapAn());
            stmt.setInt(10, boDe.getCauHoi());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer cauHoi) throws SQLException {
        String sql = "DELETE FROM BODE WHERE CAUHOI = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cauHoi);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<BoDe> findBySubject(String maMH) throws SQLException {
        List<BoDe> questions = new ArrayList<>();
        String sql = "SELECT * FROM BODE WHERE MAMH = ? ORDER BY CAUHOI";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(extractBoDeFromResultSet(rs));
                }
            }
        }
        return questions;
    }

    @Override
    public List<BoDe> findBySubjectAndLevel(String maMH, String trinhDo) throws SQLException {
        List<BoDe> questions = new ArrayList<>();
        String sql = "SELECT * FROM BODE WHERE MAMH = ? AND TRINHDO = ? ORDER BY CAUHOI";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
            stmt.setString(2, trinhDo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(extractBoDeFromResultSet(rs));
                }
            }
        }
        return questions;
    }

    @Override
    public List<BoDe> findByTeacher(String maGV) throws SQLException {
        List<BoDe> questions = new ArrayList<>();
        String sql = "SELECT * FROM BODE WHERE MAGV = ? ORDER BY CAUHOI";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGV);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(extractBoDeFromResultSet(rs));
                }
            }
        }
        return questions;
    }

    @Override
    public List<BoDe> searchByContent(String searchTerm) throws SQLException {
        List<BoDe> questions = new ArrayList<>();
        String sql = "SELECT * FROM BODE WHERE NOIDUNG LIKE ? ORDER BY CAUHOI";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(extractBoDeFromResultSet(rs));
                }
            }
        }
        return questions;
    }

    @Override
    public int countBySubjectAndLevel(String maMH, String trinhDo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM BODE WHERE MAMH = ? AND TRINHDO = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
            stmt.setString(2, trinhDo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    @Override
    public List<BoDe> getRandomQuestions(String maMH, String trinhDo, int soCau) throws SQLException {
        List<BoDe> questions = new ArrayList<>();
        // Using NEWID() for random selection in SQL Server
        String sql = "SELECT TOP (?) * FROM BODE WHERE MAMH = ? AND TRINHDO = ? ORDER BY NEWID()";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, soCau);
            stmt.setString(2, maMH);
            stmt.setString(3, trinhDo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(extractBoDeFromResultSet(rs));
                }
            }
        }
        return questions;
    }

    @Override
    public boolean exists(Integer cauHoi) throws SQLException {
        return existsByQuestion(cauHoi);
    }

    @Override
    public boolean existsByQuestion(int cauHoi) throws SQLException {
        String sql = "SELECT COUNT(*) FROM BODE WHERE CAUHOI = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cauHoi);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public boolean existsSimilarQuestion(String noiDung, String maMH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM BODE WHERE MAMH = ? AND NOIDUNG = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maMH);
            stmt.setString(2, noiDung);
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
        String sql = "SELECT COUNT(*) FROM BODE";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return 0;
    }

    private BoDe extractBoDeFromResultSet(ResultSet rs) throws SQLException {
        BoDe boDe = new BoDe();
        boDe.setCauHoi(rs.getInt("CAUHOI"));
        boDe.setMaMH(rs.getString("MAMH"));
        boDe.setMaGV(rs.getString("MAGV"));
        boDe.setTrinhDo(rs.getString("TRINHDO"));
        boDe.setNoiDung(rs.getString("NOIDUNG"));
        boDe.setA(rs.getString("A"));
        boDe.setB(rs.getString("B"));
        boDe.setC(rs.getString("C"));
        boDe.setD(rs.getString("D"));
        boDe.setDapAn(rs.getString("DAP_AN"));
        return boDe;
    }
}