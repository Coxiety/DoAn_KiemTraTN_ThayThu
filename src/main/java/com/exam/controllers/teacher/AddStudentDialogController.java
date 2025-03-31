package com.exam.controllers.teacher;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exam.dao.LopDAO;
import com.exam.dao.SinhVienDAO;
import com.exam.dao.impl.LopDAOImpl;
import com.exam.dao.impl.SinhVienDAOImpl;
import com.exam.models.Lop;
import com.exam.models.SinhVien;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller for the Add Student Dialog
 */
public class AddStudentDialogController implements Initializable {

    @FXML private TextField txtMaSV;
    @FXML private TextField txtHo;
    @FXML private TextField txtTen;
    @FXML private DatePicker datePicker;
    @FXML private TextField txtDiaChi;
    @FXML private ComboBox<Lop> comboLop;
    @FXML private Button btnAdd;
    @FXML private Button btnCancel;
    @FXML private TableView<SinhVien> tblStudents;
    @FXML private TableColumn<SinhVien, String> colMaSV;
    @FXML private TableColumn<SinhVien, String> colHo;
    @FXML private TableColumn<SinhVien, String> colTen;
    @FXML private TableColumn<SinhVien, Date> colNgaySinh;
    @FXML private TableColumn<SinhVien, String> colDiaChi;
    @FXML private TableColumn<SinhVien, String> colLop;
    @FXML private Button btnAssign;
    @FXML private Button btnRemove;
    
    private final SinhVienDAO sinhVienDAO = new SinhVienDAOImpl();
    private final LopDAO lopDAO = new LopDAOImpl();
    
    private final ObservableList<SinhVien> studentData = FXCollections.observableArrayList();
    private final ObservableList<Lop> classData = FXCollections.observableArrayList();
    private Lop preselectedClass = null;
    private Runnable onCloseCallback = null;
    
    /**
     * Set data for the dialog
     * @param lop The class to preselect
     * @param callback Callback to run when dialog is closed
     */
    public void setData(Lop lop, Runnable callback) {
        this.preselectedClass = lop;
        this.onCloseCallback = callback;
        
        // If class is already loaded, select it
        if (lop != null && comboLop != null && comboLop.getItems() != null) {
            for (Lop item : comboLop.getItems()) {
                if (item.getMaLop().equals(lop.getMaLop())) {
                    comboLop.getSelectionModel().select(item);
                    break;
                }
            }
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        loadClassData();
        loadStudentData();
        setupButtons();
    }
    
    private void setupTableColumns() {
        colMaSV.setCellValueFactory(new PropertyValueFactory<>("maSV"));
        colHo.setCellValueFactory(new PropertyValueFactory<>("ho"));
        colTen.setCellValueFactory(new PropertyValueFactory<>("ten"));
        colNgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        colLop.setCellValueFactory(cellData -> {
            try {
                String maLop = sinhVienDAO.getStudentClass(cellData.getValue().getMaSV());
                for (Lop lop : classData) {
                    if (lop.getMaLop().equals(maLop)) {
                        return new SimpleStringProperty(lop.getTenLop());
                    }
                }
                return new SimpleStringProperty("");
            } catch (SQLException e) {
                Logger.getLogger(AddStudentDialogController.class.getName())
                      .log(Level.SEVERE, "Error getting student class", e);
                return new SimpleStringProperty("");
            }
        });
        
        tblStudents.setItems(studentData);
    }
    
    private void loadClassData() {
        try {
            classData.clear();
            classData.addAll(lopDAO.findAll());
            comboLop.setItems(classData);
            
            // Apply preselected class if available
            if (preselectedClass != null) {
                for (Lop lop : classData) {
                    if (lop.getMaLop().equals(preselectedClass.getMaLop())) {
                        comboLop.getSelectionModel().select(lop);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(AddStudentDialogController.class.getName())
                  .log(Level.SEVERE, "Error loading class data", e);
            showAlert(AlertType.ERROR, "Database Error", "Could not load class data.");
        }
    }
    
    private void loadStudentData() {
        try {
            studentData.clear();
            studentData.addAll(sinhVienDAO.findAll());
        } catch (SQLException e) {
            Logger.getLogger(AddStudentDialogController.class.getName())
                  .log(Level.SEVERE, "Error loading student data", e);
            showAlert(AlertType.ERROR, "Database Error", "Could not load student data.");
        }
    }
    
    private void setupButtons() {
        btnAdd.setOnAction(e -> handleAddStudent());
        btnCancel.setOnAction(e -> closeDialog());
        btnAssign.setOnAction(e -> handleAssignToClass());
        btnRemove.setOnAction(e -> handleRemoveFromClass());
    }
    
    private void handleAddStudent() {
        // Validate input
        if (txtMaSV.getText().isEmpty() || txtHo.getText().isEmpty() || 
            txtTen.getText().isEmpty() || datePicker.getValue() == null) {
            showAlert(AlertType.WARNING, "Validation Error", 
                     "Please fill in all required fields (ID, Last Name, First Name, Birth Date).");
            return;
        }
        
        try {
            // Check if student ID already exists
            if (sinhVienDAO.exists(txtMaSV.getText().trim())) {
                showAlert(AlertType.WARNING, "Duplicate ID", "A student with this ID already exists.");
                return;
            }
            
            // Create and save new student
            SinhVien sinhVien = new SinhVien();
            sinhVien.setMaSV(txtMaSV.getText().trim());
            sinhVien.setHo(txtHo.getText().trim());
            sinhVien.setTen(txtTen.getText().trim());
            
            // Convert LocalDate to Date
            LocalDate localDate = datePicker.getValue();
            if (localDate != null) {
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                sinhVien.setNgaySinh(date);
            }
            
            sinhVien.setDiaChi(txtDiaChi.getText().trim());
            
            if (comboLop.getValue() != null) {
                sinhVien.setMaLop(comboLop.getValue().getMaLop());
            }
            
            sinhVienDAO.insert(sinhVien);
            
            // Refresh student data and clear form
            loadStudentData();
            clearForm();
            showAlert(AlertType.INFORMATION, "Success", "Student added successfully.");
        } catch (SQLException e) {
            Logger.getLogger(AddStudentDialogController.class.getName())
                  .log(Level.SEVERE, "Error adding student", e);
            showAlert(AlertType.ERROR, "Database Error", "Could not add student: " + e.getMessage());
        }
    }
    
    private void handleAssignToClass() {
        SinhVien selectedStudent = tblStudents.getSelectionModel().getSelectedItem();
        Lop selectedClass = comboLop.getValue();
        
        if (selectedStudent == null) {
            showAlert(AlertType.WARNING, "Selection Error", "Please select a student to assign.");
            return;
        }
        
        if (selectedClass == null) {
            showAlert(AlertType.WARNING, "Selection Error", "Please select a class to assign to.");
            return;
        }
        
        try {
            sinhVienDAO.assignToClass(selectedStudent.getMaSV(), selectedClass.getMaLop());
            loadStudentData(); // Refresh data
            showAlert(AlertType.INFORMATION, "Success", 
                    "Student " + selectedStudent.getHoTen() + " assigned to class " + selectedClass.getTenLop());
        } catch (SQLException e) {
            Logger.getLogger(AddStudentDialogController.class.getName())
                  .log(Level.SEVERE, "Error assigning student to class", e);
            showAlert(AlertType.ERROR, "Database Error", "Could not assign student to class: " + e.getMessage());
        }
    }
    
    private void handleRemoveFromClass() {
        SinhVien selectedStudent = tblStudents.getSelectionModel().getSelectedItem();
        
        if (selectedStudent == null) {
            showAlert(AlertType.WARNING, "Selection Error", "Please select a student to remove from class.");
            return;
        }
        
        try {
            sinhVienDAO.removeFromClass(selectedStudent.getMaSV());
            loadStudentData(); // Refresh data
            showAlert(AlertType.INFORMATION, "Success", 
                    "Student " + selectedStudent.getHoTen() + " removed from class");
        } catch (SQLException e) {
            Logger.getLogger(AddStudentDialogController.class.getName())
                  .log(Level.SEVERE, "Error removing student from class", e);
            showAlert(AlertType.ERROR, "Database Error", "Could not remove student from class: " + e.getMessage());
        }
    }
    
    private void clearForm() {
        txtMaSV.clear();
        txtHo.clear();
        txtTen.clear();
        datePicker.setValue(null);
        txtDiaChi.clear();
        comboLop.getSelectionModel().clearSelection();
    }
    
    private void closeDialog() {
        if (onCloseCallback != null) {
            onCloseCallback.run();
        }
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}