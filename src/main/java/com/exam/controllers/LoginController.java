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
import javafx.scene.control.Alert.AlertType;
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
    
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
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
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Database connection failed. Please check your configuration.");
            alert.showAndWait();
            
            if (loginButton != null) {
                loginButton.setDisable(true);
            }
        }
        
        // Set default message
        if (messageLabel != null) {
            messageLabel.setText("Please log in");
        }
    }
    
    /**
     * Handle login button click
     */
    @FXML
    private void handleLogin() {
        String username = loginField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            if (messageLabel != null) {
                messageLabel.setText("Please enter username and password");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
            return;
        }
        
        try {
            User user = userDAO.authenticate(username, password);
            if (user != null) {
                handleSuccessfulLogin(user);
            } else {
                if (messageLabel != null) {
                    messageLabel.setText("Invalid username or password");
                    messageLabel.setStyle("-fx-text-fill: red;");
                }
                passwordField.clear();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during login", e);
            if (messageLabel != null) {
                messageLabel.setText("Database error occurred. Please try again.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }
    
    /**
     * Handle register button click
     */
    @FXML
    private void handleRegister() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Registration");
        alert.setHeaderText(null);
        alert.setContentText("Registration functionality is not yet implemented.\n\n" +
                "Use the following test accounts:\n" +
                "Admin: admin/password\n" +
                "Teacher: teacher1/password\n" +
                "Student: student/password");
        alert.showAndWait();
    }
    
    /**
     * Handle successful login based on user role
     * @param user authenticated user
     * @throws SQLException if database error occurs
     */
    private void handleSuccessfulLogin(User user) throws SQLException {
        if (messageLabel != null) {
            messageLabel.setText("Login successful!");
            messageLabel.setStyle("-fx-text-fill: green;");
        }
        
        switch (user.getRole()) {
            case PGV:
            case GIANGVIEN:
                loginAsTeacher(user);
                break;
                
            case SINHVIEN:
                loginAsStudent(user);
                break;
                
            default:
                if (messageLabel != null) {
                    messageLabel.setText("Invalid user role.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                }
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
            if (messageLabel != null) {
                messageLabel.setText("Teacher account not found.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacher/dashboard.fxml"));
            Parent root = loader.load();
            
            // Pass the teacher data to the controller
            BaseTeacherController controller = loader.getController();
            if (controller != null) {
                controller.initData(giaoVien);
            }
            
            // Create and show the dashboard scene
            if (loginButton != null) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Teacher Dashboard - " + giaoVien.getHoTen());
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading teacher dashboard", e);
            if (messageLabel != null) {
                messageLabel.setText("Error loading teacher dashboard.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }
    
    /**
     * Log in as a student
     * @param user authenticated user
     * @throws SQLException if database error occurs
     */
    private void loginAsStudent(User user) throws SQLException {
        // Get the student data using the maSV from the user account
        SinhVien sinhVien = sinhVienDAO.findById(user.getMaSV());
        if (sinhVien == null) {
            if (messageLabel != null) {
                messageLabel.setText("Student account not found.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
            return;
        }
        
        try {
            // Load the student dashboard directly
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/dashboard.fxml"));
            Parent root = loader.load();
            
            // Pass the student data to the controller
            StudentDashboardController controller = loader.getController();
            if (controller != null) {
                controller.initData(sinhVien);
            }
            
            // Create and show the dashboard scene
            if (loginButton != null) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Student Dashboard - " + sinhVien.getHoTen());
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading student dashboard", e);
            if (messageLabel != null) {
                messageLabel.setText("Error loading student dashboard.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }
}