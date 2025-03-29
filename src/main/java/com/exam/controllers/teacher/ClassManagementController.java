package com.exam.controllers.teacher;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exam.models.Lop;
import com.exam.models.SinhVien;
import com.exam.dao.LopDAO;
import com.exam.dao.SinhVienDAO;
import com.exam.dao.impl.LopDAOImpl;
import com.exam.dao.impl.SinhVienDAOImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Controller for the class management view
 */
public class ClassManagementController implements Initializable {
    
    @FXML private TextField txtMaLop;
    @FXML private TextField txtTenLop;
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;
    @FXML private TableView<Lop> tblClasses;
    @FXML private TableColumn<Lop, String> colMaLop;
    @FXML private TableColumn<Lop, String> colTenLop;
    @FXML private TableColumn<Lop, Void> colStudents;
    @FXML private TableView<SinhVien> tblStudents;
    @FXML private TableColumn<SinhVien, String> colMaSV;
    @FXML private TableColumn<SinhVien, String> colHo;
    @FXML private TableColumn<SinhVien, String> colTen;
    @FXML private Button btnAddStudent;
    @FXML private Button btnRemoveStudent;
    
    private final LopDAO lopDAO = new LopDAOImpl();
    private final SinhVienDAO sinhVienDAO = new SinhVienDAOImpl();
    private ObservableList<Lop> classData = FXCollections.observableArrayList();
    private ObservableList<SinhVien> studentData = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupClassTable();
        setupStudentTable();
        setupButtons();
        loadClassData();
    }
    
    private void setupClassTable() {
        colMaLop.setCellValueFactory(new PropertyValueFactory<>("maLop"));
        colTenLop.setCellValueFactory(new PropertyValueFactory<>("tenLop"));
        
        // Add action button column for viewing students
        Callback<TableColumn<Lop, Void>, TableCell<Lop, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Lop, Void> call(final TableColumn<Lop, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("View");
                    
                    {
                        btn.setOnAction(event -> {
                            Lop data = getTableView().getItems().get(getIndex());
                            loadStudentsForClass(data);
                        });
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };
        
        colStudents.setCellFactory(cellFactory);
        
        tblClasses.setItems(classData);
        
        // Add selection listener
        tblClasses.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayClassDetails(newSelection);
                loadStudentsForClass(newSelection);
            }
        });
    }
    
    private void setupStudentTable() {
        colMaSV.setCellValueFactory(new PropertyValueFactory<>("maSV"));
        colHo.setCellValueFactory(new PropertyValueFactory<>("ho"));
        colTen.setCellValueFactory(new PropertyValueFactory<>("ten"));
        tblStudents.setItems(studentData);
    }
    
    private void setupButtons() {
        btnAdd.setOnAction(e -> handleAddClass());
        btnUpdate.setOnAction(e -> handleUpdateClass());
        btnDelete.setOnAction(e -> handleDeleteClass());
        btnClear.setOnAction(e -> clearForm());
        btnAddStudent.setOnAction(e -> handleAddStudent());
        btnRemoveStudent.setOnAction(e -> handleRemoveStudent());
    }
    
    private void loadClassData() {
        try {
            List<Lop> classes = lopDAO.findAll();
            classData.clear();
            classData.addAll(classes);
        } catch (SQLException e) {
            Logger.getLogger(ClassManagementController.class.getName()).log(Level.SEVERE, "Error loading class data", e);
            showAlert(AlertType.ERROR, "Database Error", "Could not load class data: " + e.getMessage());
        }
    }
    
    private void loadStudentsForClass(Lop selectedClass) {
        try {
            List<SinhVien> students = sinhVienDAO.findByClass(selectedClass.getMaLop());
            studentData.clear();
            studentData.addAll(students);
        } catch (SQLException e) {
            Logger.getLogger(ClassManagementController.class.getName()).log(Level.SEVERE, "Error loading student data", e);
            showAlert(AlertType.ERROR, "Database Error", "Could not load student data: " + e.getMessage());
        }
    }
    
    private void displayClassDetails(Lop lop) {
        txtMaLop.setText(lop.getMaLop());
        txtTenLop.setText(lop.getTenLop());
        txtMaLop.setDisable(true); // Disable ID field when editing
    }
    
    private void clearForm() {
        txtMaLop.clear();
        txtTenLop.clear();
        txtMaLop.setDisable(false); // Enable ID field for new records
    }
    
    private void handleAddClass() {
        if (!validateForm()) {
            return;
        }
        
        try {
            // Check if class already exists
            if (lopDAO.exists(txtMaLop.getText().trim())) {
                showAlert(AlertType.WARNING, "Duplicate ID", "A class with this ID already exists.");
                return;
            }
            
            Lop lop = new Lop();
            lop.setMaLop(txtMaLop.getText().trim());
            lop.setTenLop(txtTenLop.getText().trim());
            
            lopDAO.insert(lop);
            
            loadClassData();
            clearForm();
            showAlert(AlertType.INFORMATION, "Success", "Class added successfully.");
        } catch (SQLException e) {
            Logger.getLogger(ClassManagementController.class.getName()).log(Level.SEVERE, "Error adding class", e);
            showAlert(AlertType.ERROR, "Database Error", "Could not add class: " + e.getMessage());
        }
    }
    
    private void handleUpdateClass() {
        if (txtMaLop.isDisabled() && !validateForm()) {
            return;
        }
        
        Lop selectedClass = tblClasses.getSelectionModel().getSelectedItem();
        if (selectedClass == null) {
            showAlert(AlertType.WARNING, "No Selection", "Please select a class to update.");
            return;
        }
        
        try {
            selectedClass.setTenLop(txtTenLop.getText().trim());
            lopDAO.update(selectedClass);
            
            loadClassData();
            showAlert(AlertType.INFORMATION, "Success", "Class updated successfully.");
        } catch (SQLException e) {
            Logger.getLogger(ClassManagementController.class.getName()).log(Level.SEVERE, "Error updating class", e);
            showAlert(AlertType.ERROR, "Database Error", "Could not update class: " + e.getMessage());
        }
    }
    
    private void handleDeleteClass() {
        Lop selectedClass = tblClasses.getSelectionModel().getSelectedItem();
        if (selectedClass == null) {
            showAlert(AlertType.WARNING, "No Selection", "Please select a class to delete.");
            return;
        }
        
        try {
            // Check if class has students
            List<SinhVien> students = sinhVienDAO.findByClass(selectedClass.getMaLop());
            if (!students.isEmpty()) {
                showAlert(AlertType.WARNING, "Cannot Delete", 
                        "This class has students assigned to it. Please remove all students first.");
                return;
            }
            
            lopDAO.delete(selectedClass.getMaLop());
            
            loadClassData();
            clearForm();
            showAlert(AlertType.INFORMATION, "Success", "Class deleted successfully.");
        } catch (SQLException e) {
            Logger.getLogger(ClassManagementController.class.getName()).log(Level.SEVERE, "Error deleting class", e);
            showAlert(AlertType.ERROR, "Database Error", "Could not delete class: " + e.getMessage());
        }
    }
    
    private void handleAddStudent() {
        Lop selectedClass = tblClasses.getSelectionModel().getSelectedItem();
        if (selectedClass == null) {
            showAlert(AlertType.WARNING, "No Selection", "Please select a class to add students to.");
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacher/add-student-dialog.fxml"));
            Parent root = loader.load();
            
            AddStudentDialogController controller = loader.getController();
            controller.setData(selectedClass, () -> loadStudentsForClass(selectedClass));
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Student to Class " + selectedClass.getTenLop());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tblClasses.getScene().getWindow());
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            
            // Show the dialog and wait for it to close
            dialogStage.showAndWait();
            
            // Reload students after dialog closes
            loadStudentsForClass(selectedClass);
        } catch (IOException e) {
            Logger.getLogger(ClassManagementController.class.getName()).log(Level.SEVERE, "Error loading add student dialog", e);
            showAlert(AlertType.ERROR, "Error", "Could not load add student dialog: " + e.getMessage());
        }
    }
    
    private void handleRemoveStudent() {
        Lop selectedClass = tblClasses.getSelectionModel().getSelectedItem();
        SinhVien selectedStudent = tblStudents.getSelectionModel().getSelectedItem();
        
        if (selectedClass == null || selectedStudent == null) {
            showAlert(AlertType.WARNING, "No Selection", "Please select a class and a student to remove.");
            return;
        }
        
        try {
            sinhVienDAO.removeFromClass(selectedStudent.getMaSV());
            loadStudentsForClass(selectedClass);
            showAlert(AlertType.INFORMATION, "Success", "Student removed from class successfully.");
        } catch (SQLException e) {
            Logger.getLogger(ClassManagementController.class.getName()).log(Level.SEVERE, "Error removing student from class", e);
            showAlert(AlertType.ERROR, "Database Error", "Could not remove student from class: " + e.getMessage());
        }
    }
    
    private boolean validateForm() {
        if (txtMaLop.getText().trim().isEmpty()) {
            showAlert(AlertType.WARNING, "Validation Error", "Please enter a class ID.");
            return false;
        }
        
        if (txtTenLop.getText().trim().isEmpty()) {
            showAlert(AlertType.WARNING, "Validation Error", "Please enter a class name.");
            return false;
        }
        
        // Check length restrictions
        if (txtMaLop.getText().trim().length() > 8) {
            showAlert(AlertType.WARNING, "Validation Error", "Class ID must not exceed 8 characters.");
            return false;
        }
        
        if (txtTenLop.getText().trim().length() > 40) {
            showAlert(AlertType.WARNING, "Validation Error", "Class name must not exceed 40 characters.");
            return false;
        }
        
        return true;
    }
    
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}