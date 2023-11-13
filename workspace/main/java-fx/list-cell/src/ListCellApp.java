import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ListCellApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GridPane root = new GridPane();

        ListView listView = new ListView();

        listView.getItems().addAll(
                new Car("auto1", "make1"),
                new Car("auto2", "make2"),
                new Car("auto3", "make3"),
                new Car("auto4", "make4")
        );

        listView.setCellFactory(event -> new CarCell());

        root.getChildren().add(listView);

        stage.setTitle("List Cell");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
