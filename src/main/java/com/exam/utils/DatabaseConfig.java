package com.exam.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database configuration and connection provider class
 */
public class DatabaseConfig {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConfig.class.getName());
    
    // Database connection parameters - update these to match your environment
    private static final String DB_URL = "jdbc:sqlserver://localhost;databaseName=THI_TRAC_NGHIEM;encrypt=false";
    private static final String USER = "admin"; // Replace with your actual SQL Server username
    private static final String PASSWORD = "admin"; // Replace with your actual SQL Server password

    static {
        try {
            // Load the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            LOGGER.info("SQL Server JDBC driver loaded successfully");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "SQL Server JDBC driver not found", e);
        }
    }

    /**
     * Get a database connection
     * @return Connection to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            LOGGER.fine("Database connection established");
            return conn;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to establish database connection", e);
            throw e;
        }
    }

    /**
     * Test the database connection
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection test failed", e);
            return false;
        }
    }

    /**
     * Get the current database URL
     * @return database URL
     */
    public static String getDbUrl() {
        return DB_URL;
    }

    /**
     * Get the current database user
     * @return database user
     */
    public static String getUser() {
        return USER;
    }
}