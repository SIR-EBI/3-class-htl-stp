import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MittelwertApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mittelwert.fxml"));
        stage.setTitle("Mittelwert");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
