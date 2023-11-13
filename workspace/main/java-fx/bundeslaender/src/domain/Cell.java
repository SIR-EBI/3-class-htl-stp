package domain;

import controller.CellController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class Cell extends ListCell<Bundesland> {

    private Parent root;
    private CellController controller;

    public Cell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/cell.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(Bundesland item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        }
        else {
            controller.initCell(item);
            setGraphic(root);
        }
    }

}
