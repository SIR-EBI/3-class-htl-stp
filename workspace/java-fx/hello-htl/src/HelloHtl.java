import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class HelloHtl extends Application {

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: aquamarine");
        grid.setAlignment(Pos.CENTER);

        StackPane pane = new StackPane();

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(700), pane);
        rotateTransition.setByAngle(180);
        rotateTransition.setCycleCount(2);
        rotateTransition.setAutoReverse(true);

        pane.setOnMouseClicked(event -> {
            rotateTransition.play();
        });

        Rectangle rectangle = new Rectangle();
        rectangle.setStyle("-fx-text-fill: black; -fx-effect: dropshadow(three-pass-box, white, 1, 100000, 30, 30);");
        rectangle.setWidth(300);
        rectangle.setHeight(200);

        Label label = new Label("Hello HTL");
        label.setStyle("-fx-font-family: sans-serif; -fx-font-size: 30; -fx-text-fill: white");

        pane.getChildren().addAll(rectangle, label);
        grid.getChildren().add(pane);

        stage.setScene(new Scene(grid, 500, 500));
        stage.setTitle("Hello HTL");
        stage.show();
    }

}
