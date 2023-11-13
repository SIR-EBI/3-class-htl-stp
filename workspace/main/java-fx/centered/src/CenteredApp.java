import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CenteredApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = new AnchorPane();

        Rectangle rect = new Rectangle();
        rect.setFill(Color.RED);

        ObjectBinding<Color> colorBinding = new ObjectBinding<>() {
            {
                super.bind(rect.widthProperty());
                super.bind(rect.heightProperty());
            }
            @Override
            protected Color computeValue() {
                return rect.getWidth()*rect.getHeight() >= 50_000 ? Color.GREEN : Color.RED;
            }
        };
        rect.fillProperty().bind(colorBinding);

        rect.widthProperty().bind(root.widthProperty().divide(2));
        rect.heightProperty().bind(root.heightProperty().divide(2));

        rect.xProperty().bind(root.widthProperty().divide(4));
        rect.yProperty().bind(root.heightProperty().divide(4));


        root.getChildren().add(rect);

        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Rectangle");
        stage.show();
    }

}
