import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Swapper extends Application {

    @Override
    public void start(Stage stage) {
        // create the main pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        // declare the graphic elements
        Button swapButton = new Button("Swap");
        Button clearButton = new Button("Clear");
        Text text1 = new Text("Text 1:");
        Text text2 = new Text("Text 2:");
        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        Text infoText = new Text();

        // swap button action
        swapButton.setOnAction(event -> {
            // check if both text-fields are filled in
            if (!textField1.getText().trim().isEmpty() && !textField2.getText().trim().isEmpty()) {
                // swap the text
                String helper = textField1.getText();
                textField1.setText(textField2.getText());
                textField2.setText(helper);

                // show info text
                infoText.setText("Swapped");
                infoText.setVisible(true);
            }
            else {
                // show error msg
                infoText.setText("Both TextFields must be filled");
                infoText.setVisible(true);
            }
        });

        // clear button action
        clearButton.setOnAction(event -> {
            // clear the text-fields and hide the info text
            textField1.clear();
            textField2.clear();
            infoText.setVisible(false);
        });

        // position the graphic elements
        grid.add(swapButton, 0, 0);
        grid.add(clearButton, 1, 0);
        grid.add(text1, 0, 1);
        grid.add(textField1, 1, 1);
        grid.add(text2, 0, 2);
        grid.add(textField2, 1, 2);
        grid.add(infoText, 0, 3);

        // add pain to scene and show it to the screen
        Scene rootScene = new Scene(grid);
        stage.setTitle("Swapper");
        stage.show();
        stage.setScene(rootScene);
    }

}
