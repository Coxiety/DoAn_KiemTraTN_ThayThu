package com.exam.dao;

import java.sql.SQLException;
import java.util.List;

import com.exam.models.SinhVien;

/**
 * Data Access Object interface for SinhVien entity
 */
public interface SinhVienDAO extends BaseDAO<SinhVien, String> {
    
    /**
     * Find students by class
     * @param maLop class ID
     * @return list of students in the class
     * @throws SQLException if database error occurs
     */
    List<SinhVien> findByClass(String maLop) throws SQLException;
    
    /**
     * Search students by name
     * @param name name to search for
     * @return list of students matching the name
     * @throws SQLException if database error occurs
     */
    List<SinhVien> searchByName(String name) throws SQLException;
    
    /**
     * Find students by exam attempt
     * @param maMH subject ID
     * @param lan attempt number
     * @return list of students who have taken the exam
     * @throws SQLException if database error occurs
     */
    List<SinhVien> findByExamAttempt(String maMH, int lan) throws SQLException;
    
    /**
     * Get the class ID for a student
     * @param maSV student ID
     * @return class ID
     * @throws SQLException if database error occurs
     */
    String getStudentClass(String maSV) throws SQLException;
    
    /**
     * Assign a student to a class
     * @param maSV student ID
     * @param maLop class ID
     * @throws SQLException if database error occurs
     */
    void assignToClass(String maSV, String maLop) throws SQLException;
    
    /**
     * Remove a student from a class
     * @param maSV student ID
     * @throws SQLException if database error occurs
     */
    void removeFromClass(String maSV) throws SQLException;
}