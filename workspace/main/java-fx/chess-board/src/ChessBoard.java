import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ChessBoard extends Application {
    @Override
    public void start(Stage stage) {
        GridPane root = new GridPane();

        int size = 8;
        Rectangle rect;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                rect = new Rectangle();

                Rectangle eventRect = rect;

                rect.setOnMouseClicked(event -> {
                    if (eventRect.getFill() == Color.RED) {
                        int rectRow = GridPane.getRowIndex(eventRect);
                        int rectCol = GridPane.getColumnIndex(eventRect);
                        eventRect.setFill( ((rectRow + rectCol) % 2 == 0) ? Color.GRAY : Color.WHITE );
                    }
                    else
                        eventRect.setFill(Color.RED);
                });
                rect.setFill( ((row + col) % 2 == 0) ? Color.GRAY : Color.WHITE );

                root.add(rect, col, row);
                rect.widthProperty().bind(root.widthProperty().divide(size));
                rect.heightProperty().bind(root.heightProperty().divide(size));
            }
        }

        stage.setScene(new Scene(root,400,400));
        stage.setTitle("ChessApp");
        stage.show();
    }

}
