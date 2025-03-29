package com.exam.dao;

import java.sql.SQLException;
import java.util.List;

import com.exam.models.MonHoc;

public interface MonHocDAO extends BaseDAO<MonHoc, String> {
    /**
     * Find subjects by teacher ID
     * @param maGV teacher ID
     * @return list of subjects
     * @throws SQLException if database error occurs
     */
    List<MonHoc> findByTeacher(String maGV) throws SQLException;

    /**
     * Search subjects by name
     * @param searchTerm search term
     * @return list of matching subjects
     * @throws SQLException if database error occurs
     */
    List<MonHoc> searchByName(String searchTerm) throws SQLException;

    /**
     * Check if a subject exists by code
     * @param maMH subject code
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean existsByCode(String maMH) throws SQLException;
}