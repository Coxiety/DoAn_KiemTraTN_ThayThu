package com.exam.dao;

import java.sql.SQLException;
import java.util.List;

import com.exam.models.GiaoVienDangKy;

public interface GiaoVienDangKyDAO extends BaseDAO<GiaoVienDangKy, Integer> {
    /**
     * Find registrations by teacher ID
     * @param maGV teacher ID
     * @return list of registrations
     * @throws SQLException if database error occurs
     */
    List<GiaoVienDangKy> findByTeacher(String maGV) throws SQLException;

    /**
     * Find registrations by subject code
     * @param maMH subject code
     * @return list of registrations
     * @throws SQLException if database error occurs
     */
    List<GiaoVienDangKy> findBySubject(String maMH) throws SQLException;

    /**
     * Register teacher for a subject with a specific role
     * @param maGV teacher ID
     * @param maMH subject code
     * @param role role (GIAOVIEN/GIAMTHI)
     * @throws SQLException if database error occurs
     */
    void register(String maGV, String maMH, String role) throws SQLException;

    /**
     * Unregister teacher from a subject
     * @param maGV teacher ID
     * @param maMH subject code
     * @throws SQLException if database error occurs
     */
    void unregister(String maGV, String maMH) throws SQLException;

    /**
     * Check if registration exists
     * @param maGV teacher ID
     * @param maMH subject code
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean exists(String maGV, String maMH) throws SQLException;

    /**
     * Find all registrations by teacher ID and role
     * @param maGV teacher ID
     * @param role role (GIAOVIEN/GIAMTHI)
     * @return list of registrations
     * @throws SQLException if database error occurs
     */
    List<GiaoVienDangKy> findByTeacherAndRole(String maGV, String role) throws SQLException;

    /**
     * Find all registrations by subject code and role
     * @param maMH subject code
     * @param role role (GIAOVIEN/GIAMTHI)
     * @return list of registrations
     * @throws SQLException if database error occurs
     */
    List<GiaoVienDangKy> findBySubjectAndRole(String maMH, String role) throws SQLException;
}