package com.exam.dao;

import java.sql.SQLException;

import com.exam.models.GiaoVien;
import com.exam.models.SinhVien;

public interface AuthDAO {
    /**
     * Authenticate a teacher
     * @param maGV teacher ID
     * @param password password
     * @return GiaoVien object if authentication successful, null otherwise
     * @throws SQLException if database error occurs
     */
    GiaoVien authenticateTeacher(String maGV, String password) throws SQLException;

    /**
     * Authenticate a student
     * @param maSV student ID
     * @param password password
     * @return SinhVien object if authentication successful, null otherwise
     * @throws SQLException if database error occurs
     */
    SinhVien authenticateStudent(String maSV, String password) throws SQLException;

    /**
     * Register a new teacher
     * @param giaoVien teacher object
     * @param password password
     * @return true if registration successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean registerTeacher(GiaoVien giaoVien, String password) throws SQLException;

    /**
     * Register a new student
     * @param sinhVien student object
     * @param password password
     * @return true if registration successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean registerStudent(SinhVien sinhVien, String password) throws SQLException;

    /**
     * Change password for a user
     * @param userId user ID (either teacher or student ID)
     * @param oldPassword old password
     * @param newPassword new password
     * @return true if password change successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean changePassword(String userId, String oldPassword, String newPassword) throws SQLException;

    /**
     * Check if a user exists
     * @param userId user ID (either teacher or student ID)
     * @return true if user exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean userExists(String userId) throws SQLException;
}