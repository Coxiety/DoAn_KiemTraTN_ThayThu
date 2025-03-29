package com.exam.dao;

import java.sql.SQLException;
import java.util.List;

import com.exam.models.User;

/**
 * Data Access Object interface for User entity
 */
public interface UserDAO extends BaseDAO<User, Integer> {
    
    /**
     * Authenticate a user by checking username and password
     * @param username username
     * @param password password
     * @return User if authentication successful, null otherwise
     * @throws SQLException if database error occurs
     */
    User authenticate(String username, String password) throws SQLException;
    
    /**
     * Find a user by username
     * @param username username
     * @return User if found, null otherwise
     * @throws SQLException if database error occurs
     */
    User findByUsername(String username) throws SQLException;
    
    /**
     * Find all users with a specific role
     * @param role user role
     * @return list of users with the specified role
     * @throws SQLException if database error occurs
     */
    List<User> findByRole(User.Role role) throws SQLException;
    
    /**
     * Find a user associated with a teacher
     * @param maGV teacher ID
     * @return User if found, null otherwise
     * @throws SQLException if database error occurs
     */
    User findByTeacher(String maGV) throws SQLException;
    
    /**
     * Find a user associated with a student
     * @param maSV student ID
     * @return User if found, null otherwise
     * @throws SQLException if database error occurs
     */
    User findByStudent(String maSV) throws SQLException;
    
    /**
     * Change a user's password
     * @param userId user ID
     * @param newPassword new password
     * @return true if successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean changePassword(int userId, String newPassword) throws SQLException;
    
    /**
     * Check if a username is already taken
     * @param username username to check
     * @return true if username exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean usernameExists(String username) throws SQLException;
}