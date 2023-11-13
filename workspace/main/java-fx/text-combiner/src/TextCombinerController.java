import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class TextCombinerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button clearButton;

    @FXML
    private RadioButton radioButtonOne;

    @FXML
    private RadioButton radioButtonTwo;

    @FXML
    private TextField resultTextField;

    @FXML
    private TextField textFieldOne;

    @FXML
    private TextField textFieldTwo;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    void clearButtonPressed(ActionEvent event) {
        textFieldOne.clear();
        textFieldTwo.clear();
        resultTextField.setText("Both TextFields must be filled.");
    }

    @FXML
    void initialize() {
        resultTextField.setText("Both TextFields must be filled.");

        toggleGroup.selectedToggleProperty().addListener((obserable, oldToggle, newToggle) -> {
            updateResultTextField();
        });
        textFieldOne.textProperty().addListener(event -> {
            updateResultTextField();
        });
        textFieldTwo.textProperty().addListener(event -> {
            updateResultTextField();
        });
    }

    private void updateResultTextField() {
        String textContentOne = textFieldOne.getText();
        String textContentTwo = textFieldTwo.getText();

        if (textContentOne.trim().isEmpty() || textContentTwo.trim().isEmpty()) {
            resultTextField.setText("Both TextFields must be filled.");
        }
        else if (radioButtonOne.isSelected()) {
            resultTextField.setText("Result: " + textContentOne + textContentTwo);
        }
        else if (radioButtonTwo.isSelected()) {
            resultTextField.setText("Result: " + textContentTwo + textContentOne);
        }
    }

}
