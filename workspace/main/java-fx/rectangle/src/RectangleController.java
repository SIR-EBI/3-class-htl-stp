import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RectangleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Color> dropbox;

    @FXML
    private Text rectangeSize;

    @FXML
    private Rectangle rectangle;

    @FXML
    private Slider slideBarHeight;

    @FXML
    private Slider slideBarWidth;

    private List<Color> colorList;
    {
        colorList = new ArrayList<>();
        colorList.add(Color.valueOf("#9cce2b"));
        colorList.add(Color.valueOf("#9eeeee"));
        colorList.add(Color.valueOf("#9eee2b"));
        colorList.add(Color.valueOf("#93be41"));
        colorList.add(Color.valueOf("#ffb600"));
        colorList.add(Color.valueOf("#b6fcd5"));
    }

    @FXML
    void initialize() {
        dropbox.getItems().addAll(colorList);
        dropbox.setValue(colorList.get(0));
        rectangle.setFill(colorList.get(0));

        rectangle.heightProperty().setValue(100);
        rectangle.widthProperty().setValue(100);
        calcArea();

        dropbox.valueProperty().addListener(event -> {
            rectangle.setFill(dropbox.getSelectionModel().getSelectedItem());
        });
        slideBarWidth.valueProperty().addListener(event -> {
            rectangle.setWidth(slideBarWidth.getValue());
            calcArea();
        });
        slideBarHeight.valueProperty().addListener(event -> {
            rectangle.setHeight(slideBarHeight.getValue());
            calcArea();
        });
    }
    private void calcArea() {
        rectangeSize.setText((int) (slideBarWidth.getValue() * slideBarHeight.getValue()) + " E²");
    }

}
