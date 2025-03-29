package com.exam.models;

/**
 * Model class for User entity
 * Represents a user account in the system with authentication and authorization information
 */
public class User {
    
    /**
     * User roles in the system
     */
    public enum Role {
        PGV,        // Coordinator - full access
        GIANGVIEN,  // Teacher
        SINHVIEN    // Student
    }
    
    private int id;
    private String username;
    private String password;
    private Role role;
    private String maGV;  // Teacher ID (if applicable)
    private String maSV;  // Student ID (if applicable)
    
    /**
     * Default constructor
     */
    public User() {
    }
    
    /**
     * Constructor with fields
     */
    public User(int id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    /**
     * Get user ID
     * @return user ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Set user ID
     * @param id user ID
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Get username
     * @return username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Set username
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Get password (should be hashed in production)
     * @return password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Set password
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Get user role
     * @return role
     */
    public Role getRole() {
        return role;
    }
    
    /**
     * Set user role
     * @param role role
     */
    public void setRole(Role role) {
        this.role = role;
    }
    
    /**
     * Set role from string
     * @param roleStr role name as string
     */
    public void setRole(String roleStr) {
        try {
            this.role = Role.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            this.role = Role.SINHVIEN; // Default role
        }
    }
    
    /**
     * Get teacher ID
     * @return teacher ID
     */
    public String getMaGV() {
        return maGV;
    }
    
    /**
     * Set teacher ID
     * @param maGV teacher ID
     */
    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }
    
    /**
     * Get student ID
     * @return student ID
     */
    public String getMaSV() {
        return maSV;
    }
    
    /**
     * Set student ID
     * @param maSV student ID
     */
    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }
    
    /**
     * Check if user is a coordinator (PGV)
     * @return true if user is a coordinator
     */
    public boolean isPGV() {
        return role == Role.PGV;
    }
    
    /**
     * Check if user is a teacher
     * @return true if user is a teacher
     */
    public boolean isTeacher() {
        return role == Role.GIANGVIEN;
    }
    
    /**
     * Check if user is a student
     * @return true if user is a student
     */
    public boolean isStudent() {
        return role == Role.SINHVIEN;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", maGV='" + maGV + '\'' +
                ", maSV='" + maSV + '\'' +
                '}';
    }
}