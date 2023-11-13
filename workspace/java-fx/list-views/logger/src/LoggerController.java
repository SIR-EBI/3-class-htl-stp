import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private CheckBox checkBox;

    @FXML
    private ListView<LogEntry> listView;

    @FXML
    private RadioButton optionOne;

    @FXML
    private RadioButton optionThree;

    @FXML
    private RadioButton optionTow;

    @FXML
    private ToggleGroup options;

    @FXML
    void buttonPressed(ActionEvent event) {
        listView.getItems().add(new LogEntry("Button pressed"));
    }

    @FXML
    void checkBoxSelected(ActionEvent event) {
        listView.getItems().add(new LogEntry("CheckBox checked"));
    }

    @FXML
    void initialize() {
        options.selectedToggleProperty().addListener((obserable, oldToggle, newToggle) -> {
            if (newToggle == optionOne) {
                listView.getItems().add(new LogEntry("Option 1 selected"));
            }
            else if (newToggle == optionTow) {
                listView.getItems().add(new LogEntry("Option 2 selected"));
            }
            else if (newToggle == optionThree) {
                listView.getItems().add(new LogEntry("Option 3 selected"));
            }
        });
    }

}
