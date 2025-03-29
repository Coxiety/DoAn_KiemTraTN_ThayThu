package com.exam.controllers.teacher;

import java.net.URL;
import java.util.ResourceBundle;

import com.exam.models.BoDe;
import com.exam.models.MonHoc;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class QuestionPreviewController implements Initializable {
    @FXML private Label subjectLabel;
    @FXML private Label questionNumberLabel;
    @FXML private TextFlow questionTextFlow;
    @FXML private RadioButton optionA;
    @FXML private RadioButton optionB;
    @FXML private RadioButton optionC;
    @FXML private RadioButton optionD;
    @FXML private Label correctAnswerLabel;

    private ToggleGroup optionsGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        optionsGroup = new ToggleGroup();
        optionA.setToggleGroup(optionsGroup);
        optionB.setToggleGroup(optionsGroup);
        optionC.setToggleGroup(optionsGroup);
        optionD.setToggleGroup(optionsGroup);

        // Disable selection since this is just a preview
        optionA.setDisable(true);
        optionB.setDisable(true);
        optionC.setDisable(true);
        optionD.setDisable(true);
    }

    public void setData(BoDe question, MonHoc subject) {
        subjectLabel.setText(subject.getTenMH());
        questionNumberLabel.setText("Question #" + question.getCauHoi());
        
        // Set question text
        Label questionText = new Label(question.getNoiDung());
        questionText.setWrapText(true);
        questionTextFlow.getChildren().add(questionText);

        // Set options
        optionA.setText("A. " + question.getA());
        optionB.setText("B. " + question.getB());
        optionC.setText("C. " + question.getC());
        optionD.setText("D. " + question.getD());

        // Highlight correct answer
        correctAnswerLabel.setText(question.getDapAn());
        RadioButton correctOption = switch (question.getDapAn()) {
            case "A" -> optionA;
            case "B" -> optionB;
            case "C" -> optionC;
            case "D" -> optionD;
            default -> null;
        };

        if (correctOption != null) {
            correctOption.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        }
    }

    @FXML
    private void handleClose() {
        ((Stage) subjectLabel.getScene().getWindow()).close();
    }
}