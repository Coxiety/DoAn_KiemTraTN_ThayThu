package com.exam.dao;

import java.sql.SQLException;
import java.util.List;

import com.exam.models.GiaoVien;

/**
 * Data Access Object interface for GiaoVien entity
 */
public interface GiaoVienDAO extends BaseDAO<GiaoVien, String> {
    
    /**
     * Find teachers by department (khoa)
     * @param maKhoa department ID
     * @return list of teachers in the department
     * @throws SQLException if database error occurs
     */
    List<GiaoVien> findByDepartment(String maKhoa) throws SQLException;
    
    /**
     * Find teachers by subject
     * @param maMH subject ID
     * @return list of teachers teaching the subject
     * @throws SQLException if database error occurs
     */
    List<GiaoVien> findBySubject(String maMH) throws SQLException;
    
    /**
     * Search teachers by name
     * @param name name to search for
     * @return list of teachers matching the name
     * @throws SQLException if database error occurs
     */
    List<GiaoVien> searchByName(String name) throws SQLException;
}