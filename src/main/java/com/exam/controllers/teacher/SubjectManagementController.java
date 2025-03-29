package com.exam.controllers.teacher;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exam.controllers.BaseTeacherController;
import com.exam.dao.BoDeDAO;
import com.exam.dao.MonHocDAO;
import com.exam.dao.impl.BoDeDAOImpl;
import com.exam.dao.impl.MonHocDAOImpl;
import com.exam.models.MonHoc;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SubjectManagementController extends BaseTeacherController {
    private static final Logger LOGGER = Logger.getLogger(SubjectManagementController.class.getName());
    
    private final MonHocDAO monHocDAO = new MonHocDAOImpl();
    private final BoDeDAO boDeDAO = new BoDeDAOImpl();
    private final ObservableList<MonHoc> subjectList = FXCollections.observableArrayList();
    private MonHoc selectedSubject;

    @FXML private TextField searchField;
    @FXML private TableView<MonHoc> subjectTable;
    @FXML private TableColumn<MonHoc, String> codeColumn;
    @FXML private TableColumn<MonHoc, String> nameColumn;
    @FXML private TableColumn<MonHoc, Integer> questionsColumn;
    @FXML private TableColumn<MonHoc, Void> actionsColumn;

    @FXML private VBox formSection;
    @FXML private TextField codeField;
    @FXML private TextField nameField;
    @FXML private Label messageLabel;

    @Override
    protected void initialize() {
        configureTable();
        configureSearchField();
        loadSubjects();
        hideForm();
    }

    private void configureTable() {
        codeColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getMaMH()));
        nameColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getTenMH()));
        questionsColumn.setCellValueFactory(data -> {
            try {
                int count = boDeDAO.countBySubjectAndLevel(data.getValue().getMaMH(), null);
                return new SimpleIntegerProperty(count).asObject();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error getting question count", e);
                return new SimpleIntegerProperty(0).asObject();
            }
        });

        setupActionsColumn();
        subjectTable.setItems(subjectList);
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox container = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setOnAction(e -> handleEdit(getTableRow().getItem()));
                deleteBtn.setOnAction(e -> handleDelete(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });
    }

    private void configureSearchField() {
        searchField.textProperty().addListener((obs, old, text) -> {
            try {
                if (text == null || text.isEmpty()) {
                    loadSubjects();
                } else {
                    subjectList.setAll(monHocDAO.searchByName(text));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error searching subjects", e);
                showError("Error searching subjects");
            }
        });
    }

    private void loadSubjects() {
        try {
            subjectList.setAll(monHocDAO.findAll());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading subjects", e);
            showError("Error loading subjects");
        }
    }

    @FXML
    private void handleAddNew() {
        selectedSubject = null;
        clearForm();
        showForm();
    }

    @FXML
    private void handleSave() {
        if (!validateForm()) return;

        try {
            MonHoc subject = new MonHoc();
            subject.setMaMH(codeField.getText().trim());
            subject.setTenMH(nameField.getText().trim());

            if (selectedSubject == null) {
                // Add new subject
                if (monHocDAO.existsByCode(subject.getMaMH())) {
                    showError("Subject code already exists");
                    return;
                }
                monHocDAO.insert(subject);
                subjectList.add(subject);
                showSuccess("Subject added successfully");
            } else {
                // Update existing subject
                subject.setMaMH(selectedSubject.getMaMH()); // Keep original code
                monHocDAO.update(subject);
                loadSubjects(); // Reload to refresh the list
                showSuccess("Subject updated successfully");
            }
            hideForm();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving subject", e);
            showError("Error saving subject");
        }
    }

    @FXML
    private void handleCancel() {
        hideForm();
    }

    private void handleEdit(MonHoc subject) {
        if (subject == null) return;
        selectedSubject = subject;
        codeField.setText(subject.getMaMH());
        nameField.setText(subject.getTenMH());
        showForm();
    }

    private void handleDelete(MonHoc subject) {
        if (subject == null) return;

        try {
            // Check if subject has questions
            int questionCount = boDeDAO.countBySubjectAndLevel(subject.getMaMH(), null);
            if (questionCount > 0) {
                showError("Cannot delete subject with questions");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Delete Subject");
            alert.setContentText("Delete subject: " + subject.getTenMH() + "?");

            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                monHocDAO.delete(subject.getMaMH());
                subjectList.remove(subject);
                showSuccess("Subject deleted successfully");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting subject", e);
            showError("Error deleting subject");
        }
    }

    private boolean validateForm() {
        String code = codeField.getText().trim();
        String name = nameField.getText().trim();

        if (code.isEmpty()) {
            showError("Subject code is required");
            return false;
        }

        if (name.isEmpty()) {
            showError("Subject name is required");
            return false;
        }

        if (code.length() > 5) {
            showError("Subject code must not exceed 5 characters");
            return false;
        }

        if (name.length() > 40) {
            showError("Subject name must not exceed 40 characters");
            return false;
        }

        return true;
    }

    private void showForm() {
        formSection.setVisible(true);
        codeField.setDisable(selectedSubject != null);
    }

    private void hideForm() {
        formSection.setVisible(false);
        clearForm();
        selectedSubject = null;
    }

    private void clearForm() {
        codeField.clear();
        nameField.clear();
        codeField.setDisable(false);
        messageLabel.setText("");
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: red;");
    }

    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: green;");
    }
}