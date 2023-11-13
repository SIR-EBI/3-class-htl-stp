import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class TextCombiner extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // create the main pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        // declare the graphic elements
        ToggleGroup group = new ToggleGroup();
        RadioButton radiobutton1 = new RadioButton("Text1 Text2");
        radiobutton1.setToggleGroup(group);
        RadioButton radiobutton2 = new RadioButton("Text2 Text1");
        radiobutton2.setToggleGroup(group);
        Text text1 = new Text("Text 1:");
        Text text2 = new Text("Text 2:");
        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        TextField resultTextField = new TextField();
        Button applyButton = new Button("Apply");
        Button clearButton = new Button("Clear");

        // apply button action
        applyButton.setOnAction(event -> {
            if (textField1.getText().trim().isEmpty() || textField2.getText().trim().isEmpty()) {
                resultTextField.setText("Both TextFields must be filled");
            }
            else if (radiobutton1.isSelected()) {
                resultTextField.setText("Result: " + textField1.getText() + textField2.getText());
            }
            else if (radiobutton2.isSelected()) {
                resultTextField.setText("Result: " + textField2.getText() + textField1.getText());
            }
        });

        // clear button action
        clearButton.setOnAction(event -> {
            textField1.clear();
            textField2.clear();
            resultTextField.clear();
            radiobutton1.setSelected(true);
        });

        // position the graphic elements
        grid.add(radiobutton1, 0,0);
        grid.add(radiobutton2, 1,0);
        grid.add(text1, 0, 1);
        grid.add(textField1, 1, 1);
        grid.add(text2, 0, 2);
        grid.add(textField2, 1, 2);
        grid.add(resultTextField, 0, 3);
        grid.add(applyButton,0,4);
        grid.add(clearButton,1,4);

        // add pain to scene and show it to the screen
        Scene rootScene = new Scene(grid);
        stage.setTitle("Text Combiner");
        stage.show();
        stage.setScene(rootScene);
    }

}
