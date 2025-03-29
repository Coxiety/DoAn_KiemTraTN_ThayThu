package com.exam.dao;

import java.sql.SQLException;
import java.util.List;

import com.exam.models.BoDe;

public interface BoDeDAO extends BaseDAO<BoDe, Integer> {
    /**
     * Find questions by subject code
     * @param maMH subject code
     * @return list of questions
     * @throws SQLException if database error occurs
     */
    List<BoDe> findBySubject(String maMH) throws SQLException;

    /**
     * Find questions by subject code and level
     * @param maMH subject code
     * @param trinhDo level
     * @return list of questions
     * @throws SQLException if database error occurs
     */
    List<BoDe> findBySubjectAndLevel(String maMH, String trinhDo) throws SQLException;

    /**
     * Find questions by teacher
     * @param maGV teacher code
     * @return list of questions
     * @throws SQLException if database error occurs
     */
    List<BoDe> findByTeacher(String maGV) throws SQLException;

    /**
     * Search questions by content
     * @param searchTerm search term
     * @return list of matching questions
     * @throws SQLException if database error occurs
     */
    List<BoDe> searchByContent(String searchTerm) throws SQLException;

    /**
     * Get number of questions by subject and level
     * @param maMH subject code
     * @param trinhDo level
     * @return number of questions
     * @throws SQLException if database error occurs
     */
    int countBySubjectAndLevel(String maMH, String trinhDo) throws SQLException;

    /**
     * Get random questions for exam
     * @param maMH subject code
     * @param trinhDo level
     * @param soCau number of questions
     * @return list of random questions
     * @throws SQLException if database error occurs
     */
    List<BoDe> getRandomQuestions(String maMH, String trinhDo, int soCau) throws SQLException;

    /**
     * Check if a question exists by ID
     * @param cauHoi question ID
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean existsByQuestion(int cauHoi) throws SQLException;

    /**
     * Check if similar question exists
     * @param noiDung question content
     * @param maMH subject code
     * @return true if exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean existsSimilarQuestion(String noiDung, String maMH) throws SQLException;
}