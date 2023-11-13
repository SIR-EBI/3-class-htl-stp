import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DictionaryController {

    @FXML
    private ListView<String> lvPermutations;

    @FXML
    private ListView<String> lvSameLetters;

    @FXML
    private TextField textField;

    @FXML
    void initialize() {
        Dictionary dictionary = new Dictionary("java-fx/dictionary/resources/de-DE1.dic");
        textField.textProperty().addListener(event -> {
            lvPermutations.getItems().setAll(dictionary.getPermutations(textField.getText()));
            lvSameLetters.getItems().setAll(dictionary.getWordsWithSameLetters(textField.getText()));
        });
    }

}
