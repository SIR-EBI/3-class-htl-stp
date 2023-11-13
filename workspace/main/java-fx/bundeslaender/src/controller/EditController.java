package controller;

import domain.Bundesland;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class EditController {
    @FXML
    private TextField tfHauptstadt;

    @FXML
    private Label lblLandeshauptmann;

    @FXML
    private Label lblSitze;

    @FXML
    private ImageView ivWappen;

    @FXML
    private Button btnOk;

    public void initEditWindow(Bundesland bundesland) {
        File imgFile = new File("main/java-fx/bundeslaender/resource/assets/" + bundesland.getWappenName());
        Image image = new Image(imgFile.toURI().toString());
        ivWappen.setImage(image);

        lblLandeshauptmann.setText(bundesland.getLandeshauptmann());
        tfHauptstadt.setText(bundesland.getHauptstadt());
        lblSitze.setText(""+bundesland.getSitze());

        btnOk.setOnMouseClicked(event -> {
            if (tfHauptstadt.getText().trim().length() != 0) {
                bundesland.setHauptstadt(tfHauptstadt.getText());

                Stage stage = (Stage) btnOk.getScene().getWindow();
                stage.close();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Input empty");
                alert.show();
            }
        });
    }

}
