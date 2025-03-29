package com.exam.controllers.teacher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exam.controllers.BaseTeacherController;
import com.exam.dao.LopDAO;
import com.exam.dao.SinhVienDAO;
import com.exam.dao.impl.LopDAOImpl;
import com.exam.dao.impl.SinhVienDAOImpl;
import com.exam.models.Lop;
import com.exam.models.SinhVien;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClassManagementController extends BaseTeacherController {
    private static final Logger LOGGER = Logger.getLogger(ClassManagementController.class.getName());
    
    private final LopDAO lopDAO = new LopDAOImpl();
    private final SinhVienDAO sinhVienDAO = new SinhVienDAOImpl();
    private final ObservableList<Lop> classList = FXCollections.observableArrayList();
    private final ObservableList<SinhVien> studentList = FXCollections.observableArrayList();
    private Lop selectedClass;

    @FXML private TableView<Lop> classTable;
    @FXML private TableColumn<Lop, String> codeColumn;
    @FXML private TableColumn<Lop, String> nameColumn;
    @FXML private TableColumn<Lop, Integer> studentsColumn;
    @FXML private TableColumn<Lop, Void> actionsColumn;

    @FXML private TableView<SinhVien> studentTable;
    @FXML private TableColumn<SinhVien, String> studentIdColumn;
    @FXML private TableColumn<SinhVien, String> studentNameColumn;
    @FXML private TableColumn<SinhVien, Void> studentActionsColumn;

    @FXML private TextField searchField;
    @FXML private TextField codeField;
    @FXML private TextField nameField;
    @FXML private VBox formSection;
    @FXML private Label messageLabel;
    @FXML private Label selectedClassLabel;
    @FXML private Button addStudentButton;

    @Override
    protected void initialize() {
        configureTableColumns();
        configureSearchField();
        configureTableSelection();
        loadClassData();
        hideForm();
    }

    private void configureTableColumns() {
        // Configure class table
        codeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMaLop()));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTenLop()));
        studentsColumn.setCellValueFactory(data -> {
            try {
                int count = lopDAO.getStudentCount(data.getValue().getMaLop());
                return new SimpleIntegerProperty(count).asObject();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error getting student count", e);
                return new SimpleIntegerProperty(0).asObject();
            }
        });

        // Configure student table
        studentIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMaSV()));
        studentNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHoTen()));
        
        setupClassActionsColumn();
        setupStudentActionsColumn();

        classTable.setItems(classList);
        studentTable.setItems(studentList);
    }

    private void setupClassActionsColumn() {
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox box = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setOnAction(e -> handleEdit(getTableRow().getItem()));
                deleteBtn.setOnAction(e -> handleDelete(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });
    }

    private void setupStudentActionsColumn() {
        studentActionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button removeBtn = new Button("Remove");

            {
                removeBtn.setOnAction(e -> handleRemoveStudent(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : removeBtn);
            }
        });
    }

    private void configureSearchField() {
        searchField.textProperty().addListener((obs, old, text) -> {
            try {
                if (text == null || text.isEmpty()) {
                    loadClassData();
                } else {
                    classList.setAll(lopDAO.searchByName(text));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error searching classes", e);
                showError("Error searching classes");
            }
        });
    }

    private void configureTableSelection() {
        classTable.getSelectionModel().selectedItemProperty().addListener((obs, old, lop) -> {
            selectedClass = lop;
            if (lop != null) {
                selectedClassLabel.setText(lop.getTenLop());
                addStudentButton.setDisable(false);
                loadStudentsForClass(lop);
            } else {
                selectedClassLabel.setText("");
                addStudentButton.setDisable(true);
                studentList.clear();
            }
        });
    }

    private void loadStudentsForClass(Lop lop) {
        studentList.clear();
        if (lop == null) return;

        try {
            studentList.setAll(sinhVienDAO.findByClass(lop.getMaLop()));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading students for class", e);
            showError("Error loading students for class");
        }
    }

    private void loadClassData() {
        try {
            classList.setAll(lopDAO.findAll());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading classes", e);
            showError("Error loading classes");
        }
    }

    @FXML
    private void handleAddNew() {
        selectedClass = null;
        clearForm();
        showForm();
    }

    @FXML
    private void handleSave() {
        if (!validateForm()) return;

        try {
            Lop lop = new Lop(codeField.getText(), nameField.getText());
            
            if (selectedClass == null) {
                if (lopDAO.existsByCode(lop.getMaLop())) {
                    showError("Class code already exists");
                    return;
                }
                lopDAO.insert(lop);
                classList.add(lop);
                showSuccess("Class added successfully");
            } else {
                lop.setMaLop(selectedClass.getMaLop());
                lopDAO.update(lop);
                loadClassData();
                showSuccess("Class updated successfully");
            }
            hideForm();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving class", e);
            showError("Error saving class");
        }
    }

    @FXML
    private void handleCancel() {
        hideForm();
    }

    private void handleEdit(Lop lop) {
        if (lop == null) return;
        selectedClass = lop;
        codeField.setText(lop.getMaLop());
        nameField.setText(lop.getTenLop());
        showForm();
    }

    private void handleDelete(Lop lop) {
        if (lop == null) return;
        
        try {
            if (lopDAO.getStudentCount(lop.getMaLop()) > 0) {
                showError("Cannot delete class with students");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Delete Class");
            alert.setContentText("Delete class: " + lop.getTenLop() + "?");

            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                lopDAO.delete(lop.getMaLop());
                classList.remove(lop);
                showSuccess("Class deleted successfully");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting class", e);
            showError("Error deleting class");
        }
    }

    @FXML
    private void handleAddStudent() {
        if (selectedClass == null) {
            showError("Please select a class first");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacher/add-student-dialog.fxml"));
            Parent root = loader.load();

            AddStudentDialogController controller = loader.getController();
            controller.setData(selectedClass, () -> loadStudentsForClass(selectedClass));

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(addStudentButton.getScene().getWindow());
            dialog.setTitle("Add Students to Class");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error opening add student dialog", e);
            showError("Error opening add student dialog");
        }
    }

    private void handleRemoveStudent(SinhVien student) {
        if (selectedClass == null || student == null) return;
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Remove");
        alert.setHeaderText("Remove Student");
        alert.setContentText("Remove " + student.getHoTen() + " from " + selectedClass.getTenLop() + "?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                sinhVienDAO.removeFromClass(student.getMaSV());
                studentList.remove(student);
                showSuccess("Student removed successfully");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error removing student from class", e);
                showError("Error removing student from class");
            }
        }
    }

    private boolean validateForm() {
        String code = codeField.getText();
        String name = nameField.getText();

        if (code == null || code.trim().isEmpty()) {
            showError("Class code is required");
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            showError("Class name is required");
            return false;
        }

        if (code.length() > 8) {
            showError("Class code must not exceed 8 characters");
            return false;
        }

        if (name.length() > 40) {
            showError("Class name must not exceed 40 characters");
            return false;
        }

        return true;
    }

    private void showForm() {
        formSection.setVisible(true);
        codeField.setDisable(selectedClass != null);
    }

    private void hideForm() {
        formSection.setVisible(false);
        clearForm();
        selectedClass = null;
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