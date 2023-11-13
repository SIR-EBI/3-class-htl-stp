import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TextCombinerApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("textCombiner.fxml"));
        stage.setTitle("Text Combiner");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
