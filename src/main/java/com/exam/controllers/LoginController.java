package com.exam.controllers;

import com.exam.dao.AuthDAO;
import com.exam.dao.impl.AuthDAOImpl;
import com.exam.models.GiaoVien;
import com.exam.models.SinhVien;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private final AuthDAO authDAO = new AuthDAOImpl();
    
    @FXML
    private TextField loginField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin() {
        String login = loginField.getText().trim();
        String password = passwordField.getText();
        
        if (login.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both login and password");
            return;
        }

        try {
            if (login.equals("sv")) {
                // Student login
                SinhVien sinhVien = authDAO.authenticateStudent(loginField.getText(), password);
                if (sinhVien != null) {
                    openStudentDashboard(sinhVien);
                } else {
                    messageLabel.setText("Invalid student ID or password");
                }
            } else {
                // Teacher login
                GiaoVien giaoVien = authDAO.authenticateTeacher(login, password);
                if (giaoVien != null) {
                    openTeacherDashboard(giaoVien);
                } else {
                    messageLabel.setText("Invalid teacher ID or password");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during login", e);
            messageLabel.setText("Error connecting to database. Please try again.");
        }
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Register New User");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error opening registration window", e);
            messageLabel.setText("Error opening registration form");
        }
    }

    private void openTeacherDashboard(GiaoVien giaoVien) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacher/dashboard.fxml"));
            Parent root = loader.load();
            
            TeacherDashboardController controller = loader.getController();
            controller.initData(giaoVien);
            
            Stage currentStage = (Stage) loginField.getScene().getWindow();
            currentStage.setTitle("Teacher Dashboard - " + giaoVien.getHoTen());
            currentStage.setScene(new Scene(root));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error opening teacher dashboard", e);
            messageLabel.setText("Error opening teacher dashboard");
        }
    }

    private void openStudentDashboard(SinhVien sinhVien) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/dashboard.fxml"));
            Parent root = loader.load();
            
            StudentDashboardController controller = loader.getController();
            controller.initData(sinhVien);
            
            Stage currentStage = (Stage) loginField.getScene().getWindow();
            currentStage.setTitle("Student Dashboard - " + sinhVien.getHoTen());
            currentStage.setScene(new Scene(root));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error opening student dashboard", e);
            messageLabel.setText("Error opening student dashboard");
        }
    }
}