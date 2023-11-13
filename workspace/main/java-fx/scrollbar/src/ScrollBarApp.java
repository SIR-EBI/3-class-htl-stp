import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;


public class ScrollBarApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GridPane root = new GridPane();

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);

        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setMin(0);
        scrollBar.setMax(100);

        TextField textField = new TextField();

        ComboBox<Boolean> comboBox = new ComboBox();
        comboBox.getItems().addAll(false, true);
        comboBox.getSelectionModel().selectFirst();


        comboBox.valueProperty().addListener(event -> {
            if (!comboBox.getValue()) {
                slider.valueProperty().unbindBidirectional(scrollBar.valueProperty());
                textField.textProperty().unbindBidirectional(slider.valueProperty());
            }
            else if (comboBox.getValue()) {
                slider.valueProperty().bindBidirectional(scrollBar.valueProperty());
                textField.textProperty().bindBidirectional(slider.valueProperty(), new StringConverter<>() {
                    @Override
                    public String toString(Number number) {
                        return "" + number.longValue();
                    }
                    @Override
                    public Number fromString(String s) {
                        if (s.matches("[0-9]+") && s.length() <= 3)
                            return Integer.parseInt(s);
                        return 0;
                    }
                });
            }
        });

        root.add(slider, 0, 0);
        root.add(scrollBar, 0, 1);
        root.add(textField, 0, 2);
        root.add(comboBox, 0, 3);

        root.setAlignment(Pos.CENTER);
        root.setVgap(40);
        root.setHgap(40);

        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Scroll Bar");
        stage.show();
    }

}
