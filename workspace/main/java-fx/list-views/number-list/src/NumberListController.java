import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberListController {

    @FXML
    private ListView<Integer> leftListView;

    @FXML
    private Button moveToLeft;

    @FXML
    private Button moveToRight;

    @FXML
    private RadioButton multipleSelection;

    @FXML
    private ListView<Integer> rightListView;

    @FXML
    private ToggleGroup selection;

    @FXML
    private RadioButton singleSelection;

    @FXML
    void moveToLeftButtonClicked(ActionEvent event) {
        moveToOtherList(leftListView, rightListView);
    }

    @FXML
    void moveToRightButtonClicked(ActionEvent event) {
        moveToOtherList(rightListView, leftListView);
    }

    private static void moveToOtherList(ListView<Integer> fromList, ListView<Integer> toList) {
        List<Integer> list = new ArrayList<>();
        list.addAll(fromList.getSelectionModel().getSelectedItems());

        fromList.getItems().removeAll(list);

        list.addAll(toList.getItems());
        Collections.sort(list);

        toList.getItems().setAll(list);
    }

    @FXML
    void multipleSelectionSelected(ActionEvent event) {
        leftListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        rightListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void singleSelectionSelected(ActionEvent event) {
        leftListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        rightListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    void initialize() {
        for (int counter = 0; counter <= 100; counter++) {
            leftListView.getItems().add(counter);
        }
    }

}
