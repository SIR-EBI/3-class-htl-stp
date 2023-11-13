import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListDemoController {

    @FXML
    private Button addButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button shuffleButton;

    @FXML
    private Button sortButton;

    @FXML
    private TextField textField;

    @FXML
    void addButtonPressed(ActionEvent event) {
        if (textField.getText().trim().length() != 0) {
            listView.getItems().add(textField.getText());
        }
    }

    @FXML
    void clearButtonPressed(ActionEvent event) {
        listView.getItems().clear();
    }

    @FXML
    void deleteButtonPressed(ActionEvent event) {
        listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void shuffleButtonPressed(ActionEvent event) {
        List<String> list = new ArrayList<>(listView.getItems());
        Collections.shuffle(list);
        listView.getItems().setAll(list);
    }

    @FXML
    void sortButtonPressed(ActionEvent event) {
        List<String> list = new ArrayList<>(listView.getItems());
        Collections.sort(list);
        listView.getItems().setAll(list);
    }

}
