package com.exam.controllers.teacher;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exam.controllers.BaseTeacherController;
import com.exam.dao.GiaoVienDangKyDAO;
import com.exam.dao.MonHocDAO;
import com.exam.dao.impl.GiaoVienDangKyDAOImpl;
import com.exam.dao.impl.MonHocDAOImpl;
import com.exam.models.GiaoVienDangKy;
import com.exam.models.MonHoc;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegistrationManagementController extends BaseTeacherController {
    private static final Logger LOGGER = Logger.getLogger(RegistrationManagementController.class.getName());
    
    private final MonHocDAO monHocDAO = new MonHocDAOImpl();
    private final GiaoVienDangKyDAO dangKyDAO = new GiaoVienDangKyDAOImpl();
    private final ObservableList<MonHoc> subjectList = FXCollections.observableArrayList();
    private final ObservableList<GiaoVienDangKy> registrationList = FXCollections.observableArrayList();
    private MonHoc selectedSubject;

    @FXML private TextField searchField;
    @FXML private TableView<MonHoc> subjectTable;
    @FXML private TableColumn<MonHoc, String> codeColumn;
    @FXML private TableColumn<MonHoc, String> nameColumn;
    @FXML private TableColumn<MonHoc, Integer> teachersColumn;
    @FXML private TableColumn<MonHoc, Void> actionsColumn;

    @FXML private VBox registrationForm;
    @FXML private Label selectedSubjectLabel;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label messageLabel;

    @FXML private TableView<GiaoVienDangKy> registrationTable;
    @FXML private TableColumn<GiaoVienDangKy, String> regCodeColumn;
    @FXML private TableColumn<GiaoVienDangKy, String> regNameColumn;
    @FXML private TableColumn<GiaoVienDangKy, String> roleColumn;
    @FXML private TableColumn<GiaoVienDangKy, Void> regActionsColumn;

    @Override
    protected void initialize() {
        configureSubjectTable();
        configureRegistrationTable();
        configureSearchField();
        loadData();
        hideForm();
    }

    private void configureSubjectTable() {
        codeColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getMaMH()));
        nameColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getTenMH()));
        teachersColumn.setCellValueFactory(data -> {
            try {
                int count = dangKyDAO.findBySubject(data.getValue().getMaMH()).size();
                return new SimpleIntegerProperty(count).asObject();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error getting teacher count", e);
                return new SimpleIntegerProperty(0).asObject();
            }
        });

        setupSubjectActionsColumn();
        subjectTable.setItems(subjectList);
    }

    private void configureRegistrationTable() {
        regCodeColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getMaMH()));
        regNameColumn.setCellValueFactory(data -> {
            try {
                MonHoc monHoc = monHocDAO.findById(data.getValue().getMaMH());
                return new SimpleStringProperty(monHoc != null ? monHoc.getTenMH() : "");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error getting subject name", e);
                return new SimpleStringProperty("");
            }
        });
        roleColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getRole()));

        setupRegistrationActionsColumn();
        registrationTable.setItems(registrationList);
    }

    private void setupSubjectActionsColumn() {
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button registerBtn = new Button("Register");

            {
                registerBtn.setOnAction(e -> handleShowRegistrationForm(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    MonHoc subject = getTableRow().getItem();
                    try {
                        boolean registered = subject != null && 
                            dangKyDAO.exists(giaoVien.getMaGV(), subject.getMaMH());
                        setGraphic(registered ? null : registerBtn);
                    } catch (SQLException ex) {
                        LOGGER.log(Level.SEVERE, "Error checking registration", ex);
                        setGraphic(null);
                    }
                }
            }
        });
    }

    private void setupRegistrationActionsColumn() {
        regActionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button unregisterBtn = new Button("Unregister");

            {
                unregisterBtn.getStyleClass().add("cancel-button");
                unregisterBtn.setOnAction(e -> handleUnregister(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : unregisterBtn);
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

    private void loadData() {
        loadSubjects();
        loadRegistrations();
    }

    private void loadSubjects() {
        try {
            subjectList.setAll(monHocDAO.findAll());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading subjects", e);
            showError("Error loading subjects");
        }
    }

    private void loadRegistrations() {
        try {
            registrationList.setAll(dangKyDAO.findByTeacher(giaoVien.getMaGV()));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading registrations", e);
            showError("Error loading registrations");
        }
    }

    private void handleShowRegistrationForm(MonHoc subject) {
        if (subject == null) return;
        selectedSubject = subject;
        selectedSubjectLabel.setText(subject.getTenMH());
        roleComboBox.setValue("GIAOVIEN");
        showForm();
    }

    @FXML
    private void handleRegister() {
        if (!validateForm()) return;

        try {
            dangKyDAO.register(
                giaoVien.getMaGV(),
                selectedSubject.getMaMH(),
                roleComboBox.getValue()
            );
            loadData();
            showSuccess("Registration successful");
            hideForm();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error registering for subject", e);
            showError("Error registering for subject");
        }
    }

    private void handleUnregister(GiaoVienDangKy registration) {
        if (registration == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Unregister");
        alert.setHeaderText("Unregister from Subject");
        alert.setContentText("Are you sure you want to unregister from this subject?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                dangKyDAO.unregister(registration.getMaGV(), registration.getMaMH());
                loadData();
                showSuccess("Successfully unregistered from subject");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error unregistering from subject", e);
                showError("Error unregistering from subject");
            }
        }
    }

    @FXML
    private void handleCancel() {
        hideForm();
    }

    private boolean validateForm() {
        if (selectedSubject == null) {
            showError("No subject selected");
            return false;
        }

        if (roleComboBox.getValue() == null) {
            showError("Please select a role");
            return false;
        }

        return true;
    }

    private void showForm() {
        registrationForm.setVisible(true);
    }

    private void hideForm() {
        registrationForm.setVisible(false);
        selectedSubject = null;
        selectedSubjectLabel.setText("");
        roleComboBox.setValue(null);
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