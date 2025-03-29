package com.exam.dao;

import java.sql.SQLException;
import java.util.List;

import com.exam.models.SinhVien;

public interface SinhVienDAO extends BaseDAO<SinhVien, String> {
    /**
     * Find students by class code
     * @param maLop class code
     * @return list of students in the class
     * @throws SQLException if database error occurs
     */
    List<SinhVien> findByClass(String maLop) throws SQLException;

    /**
     * Find students not assigned to any class
     * @return list of unassigned students
     * @throws SQLException if database error occurs
     */
    List<SinhVien> findUnassignedStudents() throws SQLException;

    /**
     * Assign student to a class
     * @param maSV student ID
     * @param maLop class code
     * @throws SQLException if database error occurs
     */
    void assignToClass(String maSV, String maLop) throws SQLException;

    /**
     * Remove student from a class
     * @param maSV student ID
     * @throws SQLException if database error occurs
     */
    void removeFromClass(String maSV) throws SQLException;

    /**
     * Check if a student exists by ID
     * @param maSV student ID
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean existsByCode(String maSV) throws SQLException;

    /**
     * Search students by name
     * @param searchTerm search term
     * @return list of matching students
     * @throws SQLException if database error occurs
     */
    List<SinhVien> searchByName(String searchTerm) throws SQLException;

    /**
     * Get student's class code
     * @param maSV student ID
     * @return class code if assigned, null if not assigned
     * @throws SQLException if database error occurs
     */
    String getStudentClass(String maSV) throws SQLException;
}