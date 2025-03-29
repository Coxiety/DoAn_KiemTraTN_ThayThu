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

public class TeacherDashboardController extends BaseTeacherController {
    private static final Logger LOGGER = Logger.getLogger(TeacherDashboardController.class.getName());
    
    @FXML
    private Label welcomeLabel;
    
    @FXML
    private Label teacherInfoLabel;

    @Override
    protected void initialize() {
        updateUI();
    }

    private void updateUI() {
        welcomeLabel.setText("Welcome, " + giaoVien.getHoTen());
        teacherInfoLabel.setText("Teacher ID: " + giaoVien.getMaGV());
    }

    @FXML
    private void handleManageSubjects() {
        openWindow("/fxml/teacher/subjects.fxml", "Manage Subjects");
    }

    @FXML
    private void handleManageQuestions() {
        openWindow("/fxml/teacher/questions.fxml", "Manage Questions");
    }

    @FXML
    private void handlePrepareExam() {
        openWindow("/fxml/teacher/exam-prep.fxml", "Prepare Exam");
    }

    @FXML
    private void handleViewResults() {
        openWindow("/fxml/teacher/results.fxml", "View Results");
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
            
            // Get the controller and pass the teacher data if needed
            Object controller = loader.getController();
            if (controller instanceof BaseTeacherController) {
                ((BaseTeacherController) controller).initData(giaoVien);
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