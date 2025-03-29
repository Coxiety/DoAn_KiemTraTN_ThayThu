package com.exam.controllers.teacher;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exam.dao.SinhVienDAO;
import com.exam.dao.impl.SinhVienDAOImpl;
import com.exam.models.Lop;
import com.exam.models.SinhVien;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStudentDialogController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(AddStudentDialogController.class.getName());
    
    private final SinhVienDAO sinhVienDAO = new SinhVienDAOImpl();
    private final ObservableList<SinhVien> studentList = FXCollections.observableArrayList();
    private Lop selectedClass;
    private Runnable onStudentsAdded;

    @FXML private Label classInfoLabel;
    @FXML private TextField searchField;
    @FXML private TableView<SinhVien> studentTable;
    @FXML private TableColumn<SinhVien, String> studentIdColumn;
    @FXML private TableColumn<SinhVien, String> nameColumn;
    @FXML private TableColumn<SinhVien, String> currentClassColumn;
    @FXML private Label messageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        setupSearch();
        loadStudents();
    }

    private void setupTable() {
        studentIdColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getMaSV()));
            
        nameColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getHoTen()));
            
        currentClassColumn.setCellValueFactory(data -> {
            try {
                String maLop = sinhVienDAO.getStudentClass(data.getValue().getMaSV());
                return new SimpleStringProperty(maLop != null ? maLop : "Not Assigned");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error getting student class", e);
                return new SimpleStringProperty("Error");
            }
        });

        studentTable.setItems(studentList);
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void setupSearch() {
        searchField.textProperty().addListener((obs, old, text) -> {
            try {
                if (text == null || text.isEmpty()) {
                    loadStudents();
                } else {
                    studentList.setAll(sinhVienDAO.searchByName(text));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error searching students", e);
                showError("Error searching students");
            }
        });
    }

    private void loadStudents() {
        try {
            studentList.setAll(sinhVienDAO.findAll());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading students", e);
            showError("Error loading students");
        }
    }

    public void setData(Lop lop, Runnable callback) {
        this.selectedClass = lop;
        this.onStudentsAdded = callback;
        classInfoLabel.setText("Adding students to class: " + lop.getTenLop());
    }

    @FXML
    private void handleAddSelected() {
        ObservableList<SinhVien> selectedStudents = studentTable.getSelectionModel().getSelectedItems();
        
        if (selectedStudents.isEmpty()) {
            showError("Please select students to add");
            return;
        }

        try {
            for (SinhVien student : selectedStudents) {
                sinhVienDAO.assignToClass(student.getMaSV(), selectedClass.getMaLop());
            }
            
            if (onStudentsAdded != null) {
                onStudentsAdded.run();
            }
            
            closeDialog();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding students to class", e);
            showError("Error adding students to class");
        }
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    private void closeDialog() {
        ((Stage) studentTable.getScene().getWindow()).close();
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: red;");
    }
}