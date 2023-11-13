import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WordleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane pane;

    @FXML
    private TextField textField;

    @FXML
    private Button enter;

    private List<List<Label>> labelList = new ArrayList<>();
    private int rowNow = 0;

    private String word = "hallo";

    @FXML
    void initialize() {
        makeGridFields();

        textField.textProperty().addListener(event -> {
            if (textField.getText().length() > 5) {
                textField.setText(textField.getText().substring(0,5));
            }
            for (int i = 0; i < textField.getText().length(); i++) {
                labelList.get(rowNow).get(i).setText(textField.getText().substring(i, i+1));
            }
        });

        enter.setOnMouseClicked(event -> {
            if (textField.getText().length() == 5) {
                for (int i = 0; i < 5; i++) {
                    if (textField.getText().substring(i, i+1).equals(word.substring(i, i+1))) {
                        labelList.get(rowNow).get(i).setStyle("-fx-border-color: blue");
                    }
                    else if (word.contains(textField.getText().substring(i, i+1))) {
                        labelList.get(rowNow).get(i).setStyle("-fx-border-color: orange");
                    }
                }

                if (rowNow == 5) {
                    if (word.equals(textField.getText())) {
                        winMessage();
                    }
                    else {
                        loseMessage();
                    }
                }
                if (word.equals(textField.getText())) {
                    winMessage();
                }

                textField.setText("");
                rowNow++;
            }
        });
    }
    private void makeGridFields() {
        GridPane grid = new GridPane();
        grid.setMinWidth(400);
        grid.setMinHeight(480);

        Label label;

        for (int row = 0; row < 6; row++) {
            List<Label> rowList = new ArrayList<>();
            for (int col = 0; col < 5; col++) {
                label = new Label();

                label.setAlignment(Pos.CENTER);
                label.setMinWidth(400 / 5);
                label.setMinHeight(480 / 6);
                label.setStyle("-fx-border-color: black");

                rowList.add(label);
                grid.add(label, col, row);
            }
            labelList.add(rowList);
        }

        pane.getChildren().add(grid);
    }
    private void winMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Du hast gewonnen");
        alert.showAndWait();
    }
    private void loseMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Du hast verloren");
        alert.showAndWait();
    }

}
