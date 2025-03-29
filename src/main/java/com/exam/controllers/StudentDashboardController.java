package com.exam.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StudentDashboardController extends BaseStudentController {
    private static final Logger LOGGER = Logger.getLogger(StudentDashboardController.class.getName());
    
    @FXML
    private Label welcomeLabel;
    
    @FXML
    private Label studentInfoLabel;

    @Override
    protected void initialize() {
        updateUI();
    }

    private void updateUI() {
        welcomeLabel.setText("Welcome, " + sinhVien.getHoTen());
        studentInfoLabel.setText(String.format("Student ID: %s | Class: %s", 
            sinhVien.getMaSV(), sinhVien.getMaLop()));
    }

    @FXML
    private void handleStartExam() {
        openWindow("/fxml/student/take-exam.fxml", "Take Exam");
    }

    @FXML
    private void handleViewResults() {
        openWindow("/fxml/student/view-results.fxml", "View Results");
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setTitle("Multiple Choice Exam System");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error returning to login screen", e);
        }
    }

    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Pass the student data to the controller
            Object controller = loader.getController();
            if (controller instanceof BaseStudentController) {
                ((BaseStudentController) controller).initData(sinhVien);
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error opening " + title + " window", e);
        }
    }
}