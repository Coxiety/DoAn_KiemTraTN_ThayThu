package com.exam.dao;

import java.sql.SQLException;
import java.util.List;

public interface BaseDAO<T, ID> {
    // Create
    void insert(T entity) throws SQLException;
    
    // Read
    T findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    
    // Update
    void update(T entity) throws SQLException;
    
    // Delete
    void delete(ID id) throws SQLException;
    
    // Additional common operations
    boolean exists(ID id) throws SQLException;
    long count() throws SQLException;
}