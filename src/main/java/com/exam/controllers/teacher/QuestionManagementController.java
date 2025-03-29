package com.exam.controllers.teacher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exam.controllers.BaseTeacherController;
import com.exam.dao.BoDeDAO;
import com.exam.dao.MonHocDAO;
import com.exam.dao.impl.BoDeDAOImpl;
import com.exam.dao.impl.MonHocDAOImpl;
import com.exam.models.BoDe;
import com.exam.models.MonHoc;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class QuestionManagementController extends BaseTeacherController {
    private static final Logger LOGGER = Logger.getLogger(QuestionManagementController.class.getName());
    
    private final BoDeDAO boDeDAO = new BoDeDAOImpl();
    private final MonHocDAO monHocDAO = new MonHocDAOImpl();
    private final ObservableList<BoDe> questionList = FXCollections.observableArrayList();
    private final ObservableList<MonHoc> subjectList = FXCollections.observableArrayList();
    private BoDe selectedQuestion;

    @FXML private ComboBox<MonHoc> subjectComboBox;
    @FXML private TextField searchField;
    @FXML private TableView<BoDe> questionTable;
    @FXML private TableColumn<BoDe, String> questionIdColumn;
    @FXML private TableColumn<BoDe, String> questionTextColumn;
    @FXML private TableColumn<BoDe, String> levelColumn;
    @FXML private TableColumn<BoDe, Void> actionsColumn;

    @FXML private VBox formSection;
    @FXML private ComboBox<String> levelComboBox;
    @FXML private TextArea questionField;
    @FXML private TextField optionAField;
    @FXML private TextField optionBField;
    @FXML private TextField optionCField;
    @FXML private TextField optionDField;
    @FXML private ComboBox<String> answerComboBox;
    @FXML private Label messageLabel;

    @Override
    protected void initialize() {
        configureSubjectComboBox();
        configureQuestionTable();
        configureSearchField();
        loadSubjects();
        hideForm();
    }

    private void configureSubjectComboBox() {
        StringConverter<MonHoc> converter = new StringConverter<>() {
            @Override
            public String toString(MonHoc monHoc) {
                return monHoc != null ? monHoc.getTenMH() : "";
            }

            @Override
            public MonHoc fromString(String string) {
                return null; // Not needed for ComboBox
            }
        };

        subjectComboBox.setConverter(converter);
        subjectComboBox.getSelectionModel().selectedItemProperty().addListener((obs, old, subject) -> {
            if (subject != null) {
                loadQuestions(subject.getMaMH());
            } else {
                questionList.clear();
            }
        });
    }

    private void configureQuestionTable() {
        questionIdColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(String.valueOf(data.getValue().getCauHoi())));
        questionTextColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getNoiDung()));
        levelColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getTrinhDo()));

        setupActionsColumn();
        questionTable.setItems(questionList);

        // Initialize level options
        levelComboBox.setItems(FXCollections.observableArrayList("A", "B", "C"));
        answerComboBox.setItems(FXCollections.observableArrayList("A", "B", "C", "D"));
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button previewBtn = new Button("Preview");
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox container = new HBox(5, previewBtn, editBtn, deleteBtn);

            {
                previewBtn.setOnAction(e -> handlePreview(getTableRow().getItem()));
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
            MonHoc selectedSubject = subjectComboBox.getValue();
            if (selectedSubject == null) {
                showError("Please select a subject first");
                return;
            }

            try {
                if (text == null || text.isEmpty()) {
                    loadQuestions(selectedSubject.getMaMH());
                } else {
                    questionList.setAll(boDeDAO.searchByContent(text));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error searching questions", e);
                showError("Error searching questions");
            }
        });
    }

    private void loadSubjects() {
        try {
            subjectList.setAll(monHocDAO.findAll());
            subjectComboBox.setItems(subjectList);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading subjects", e);
            showError("Error loading subjects");
        }
    }

    private void loadQuestions(String maMH) {
        try {
            questionList.setAll(boDeDAO.findBySubject(maMH));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading questions", e);
            showError("Error loading questions");
        }
    }

    private void handlePreview(BoDe question) {
        if (question == null) return;
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacher/question-preview.fxml"));
            Parent root = loader.load();

            QuestionPreviewController controller = loader.getController();
            controller.setData(question, subjectComboBox.getValue());

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(questionTable.getScene().getWindow());
            dialog.setTitle("Question Preview");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error showing question preview", e);
            showError("Error showing question preview");
        }
    }

    private void handleEdit(BoDe question) {
        if (question == null) return;
        selectedQuestion = question;
        levelComboBox.setValue(question.getTrinhDo());
        questionField.setText(question.getNoiDung());
        optionAField.setText(question.getA());
        optionBField.setText(question.getB());
        optionCField.setText(question.getC());
        optionDField.setText(question.getD());
        answerComboBox.setValue(question.getDapAn());
        showForm();
    }

    private void handleDelete(BoDe question) {
        if (question == null) return;

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Question");
        alert.setContentText("Are you sure you want to delete this question?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                boDeDAO.delete(question.getCauHoi());
                questionList.remove(question);
                showSuccess("Question deleted successfully");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error deleting question", e);
                showError("Error deleting question");
            }
        }
    }

    @FXML
    private void handleSave() {
        if (!validateForm()) return;

        try {
            MonHoc selectedSubject = subjectComboBox.getValue();
            BoDe question = new BoDe();
            question.setMaMH(selectedSubject.getMaMH());
            question.setMaGV(giaoVien.getMaGV());
            question.setTrinhDo(levelComboBox.getValue());
            question.setNoiDung(questionField.getText().trim());
            question.setA(optionAField.getText().trim());
            question.setB(optionBField.getText().trim());
            question.setC(optionCField.getText().trim());
            question.setD(optionDField.getText().trim());
            question.setDapAn(answerComboBox.getValue());

            if (selectedQuestion == null) {
                // Add new question
                if (boDeDAO.existsSimilarQuestion(question.getNoiDung(), question.getMaMH())) {
                    showError("Similar question already exists");
                    return;
                }
                boDeDAO.insert(question);
                questionList.add(question);
                showSuccess("Question added successfully");
            } else {
                // Update existing question
                question.setCauHoi(selectedQuestion.getCauHoi());
                boDeDAO.update(question);
                loadQuestions(selectedSubject.getMaMH());
                showSuccess("Question updated successfully");
            }
            hideForm();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving question", e);
            showError("Error saving question");
        }
    }

    @FXML
    private void handleCancel() {
        hideForm();
    }

    @FXML
    private void handleAddNew() {
        MonHoc selectedSubject = subjectComboBox.getValue();
        if (selectedSubject == null) {
            showError("Please select a subject first");
            return;
        }

        selectedQuestion = null;
        clearForm();
        showForm();
    }

    private boolean validateForm() {
        if (levelComboBox.getValue() == null) {
            showError("Please select a level");
            return false;
        }

        String question = questionField.getText().trim();
        if (question.isEmpty()) {
            showError("Question text is required");
            return false;
        }

        if (optionAField.getText().trim().isEmpty() ||
            optionBField.getText().trim().isEmpty() ||
            optionCField.getText().trim().isEmpty() ||
            optionDField.getText().trim().isEmpty()) {
            showError("All options are required");
            return false;
        }

        if (answerComboBox.getValue() == null) {
            showError("Please select the correct answer");
            return false;
        }

        return true;
    }

    private void showForm() {
        formSection.setVisible(true);
    }

    private void hideForm() {
        formSection.setVisible(false);
        clearForm();
        selectedQuestion = null;
    }

    private void clearForm() {
        levelComboBox.setValue(null);
        questionField.clear();
        optionAField.clear();
        optionBField.clear();
        optionCField.clear();
        optionDField.clear();
        answerComboBox.setValue(null);
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