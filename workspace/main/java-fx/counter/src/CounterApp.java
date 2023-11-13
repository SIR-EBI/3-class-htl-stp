import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class CounterApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);

        Button button = new Button("click me");

        Counter counter = new Counter();

        button.setOnMouseClicked(event -> {
            counter.increase();
        });

        Label label = new Label(counter.toString());

        counter.valueProperty().addListener(event -> {
            label.setText(counter.toString());
        });

        root.add(button,0,0);
        root.add(label,0,1);

        stage.setTitle("Counter");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
