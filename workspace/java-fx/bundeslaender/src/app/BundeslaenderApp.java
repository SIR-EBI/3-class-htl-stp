package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BundeslaenderApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../layout/main.fxml"));
        stage.setTitle("Bundeslaender");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
