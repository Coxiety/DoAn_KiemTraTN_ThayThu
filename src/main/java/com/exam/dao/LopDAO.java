package com.exam.dao;

import com.exam.models.Lop;
import java.sql.SQLException;
import java.util.List;

public interface LopDAO extends BaseDAO<Lop, String> {
    /**
     * Find class by name
     * @param tenLop class name
     * @return Lop object if found, null otherwise
     * @throws SQLException if database error occurs
     */
    Lop findByName(String tenLop) throws SQLException;

    /**
     * Find classes by partial name match
     * @param searchTerm search term
     * @return list of matching classes
     * @throws SQLException if database error occurs
     */
    List<Lop> searchByName(String searchTerm) throws SQLException;

    /**
     * Get all classes that have registered students
     * @return list of classes with students
     * @throws SQLException if database error occurs
     */
    List<Lop> getClassesWithStudents() throws SQLException;

    /**
     * Get all classes that have registered exams
     * @param maMH subject code
     * @return list of classes with exams for the given subject
     * @throws SQLException if database error occurs
     */
    List<Lop> getClassesWithExams(String maMH) throws SQLException;

    /**
     * Get classes taught by a specific teacher
     * @param maGV teacher code
     * @return list of classes taught by the teacher
     * @throws SQLException if database error occurs
     */
    List<Lop> getClassesByTeacher(String maGV) throws SQLException;

    /**
     * Check if a class exists by code
     * @param maLop class code
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean existsByCode(String maLop) throws SQLException;

    /**
     * Check if a class exists by name
     * @param tenLop class name
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean existsByName(String tenLop) throws SQLException;

    /**
     * Get student count for a class
     * @param maLop class code
     * @return number of students in the class
     * @throws SQLException if database error occurs
     */
    int getStudentCount(String maLop) throws SQLException;
}