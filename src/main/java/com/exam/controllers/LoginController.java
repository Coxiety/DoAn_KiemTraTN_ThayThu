package com.exam.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exam.dao.GiaoVienDAO;
import com.exam.dao.SinhVienDAO;
import com.exam.dao.UserDAO;
import com.exam.dao.impl.GiaoVienDAOImpl;
import com.exam.dao.impl.SinhVienDAOImpl;
import com.exam.dao.impl.UserDAOImpl;
import com.exam.models.GiaoVien;
import com.exam.models.SinhVien;
import com.exam.models.User;
import com.exam.utils.DatabaseConfig;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * Controller for login screen
 */
public class LoginController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    
    private final UserDAO userDAO = new UserDAOImpl();
    private final GiaoVienDAO giaoVienDAO = new GiaoVienDAOImpl();
    private final SinhVienDAO sinhVienDAO = new SinhVienDAOImpl();
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Button loginButton;
    @FXML private ToggleGroup roleGroup;
    @FXML private RadioButton teacherRadio;
    @FXML private RadioButton studentRadio;
    
    /**
     * Initialize controller
     */
    @FXML
    private void initialize() {
        // Test database connection on startup
        boolean connectionOk = DatabaseConfig.testConnection();
        if (!connectionOk) {
            showError("Database connection failed. Please check your configuration.");
            loginButton.setDisable(true);
        }
    }
    
    /**
     * Handle login button click
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showStatus("Please enter username and password", true);
            return;
        }
        
        try {
            User user = userDAO.authenticate(username, password);
            if (user != null) {
                handleSuccessfulLogin(user);
            } else {
                showStatus("Invalid username or password", true);
                passwordField.clear();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during login", e);
            showStatus("Database error occurred. Please try again.", true);
        }
    }
    
    /**
     * Handle successful login based on user role
     * @param user authenticated user
     * @throws SQLException if database error occurs
     */
    private void handleSuccessfulLogin(User user) throws SQLException {
        showStatus("Login successful!", false);
        
        switch (user.getRole()) {
            case PGV:
            case GIANGVIEN:
                loginAsTeacher(user);
                break;
                
            case SINHVIEN:
                loginAsStudent(user);
                break;
                
            default:
                showStatus("Invalid user role.", true);
                break;
        }
    }
    
    /**
     * Log in as a teacher or coordinator
     * @param user authenticated user
     * @throws SQLException if database error occurs
     */
    private void loginAsTeacher(User user) throws SQLException {
        // Get the teacher data
        GiaoVien giaoVien = giaoVienDAO.findById(user.getMaGV());
        if (giaoVien == null) {
            showStatus("Teacher account not found.", true);
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacher/dashboard.fxml"));
            Parent root = loader.load();
            
            // Pass the teacher data to the controller
            BaseTeacherController controller = loader.getController();
            controller.initData(giaoVien);
            
            // Create and show the dashboard scene
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Teacher Dashboard - " + giaoVien.getHoTen());
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading teacher dashboard", e);
            showStatus("Error loading teacher dashboard.", true);
        }
    }
    
    /**
     * Log in as a student
     * @param user authenticated user
     * @throws SQLException if database error occurs
     */
    private void loginAsStudent(User user) throws SQLException {
        // For student login, we need to first select which student to log in as
        // since the account is shared
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/student-select.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Student Selection");
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading student selection", e);
            showStatus("Error loading student selection screen.", true);
        }
    }
    
    /**
     * Show status message
     * @param message status message
     * @param isError true if error message, false otherwise
     */
    private void showStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setStyle(isError
                ? "-fx-text-fill: red;"
                : "-fx-text-fill: green;");
    }
    
    /**
     * Show error dialog
     * @param message error message
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}