package controller;

import domain.Bundesland;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

public class CellController {

    @FXML
    private Label einwohner;

    @FXML
    private Label flaeche;

    @FXML
    private GridPane grid;

    @FXML
    private Label hauptstadt;

    @FXML
    private Label name;

    @FXML
    private ImageView wappen;

    public void initCell(Bundesland bundesland) {
        name.setText(bundesland.getName());

        File imgFile = new File("java-fx/bundeslaender/resource/assets/" + bundesland.getWappenName());
        Image image = new Image(imgFile.toURI().toString());
        wappen.setImage(image);

        hauptstadt.setText(bundesland.getHauptstadt());
        einwohner.setText("" + bundesland.getEinwohner());
        flaeche.setText("" + bundesland.getFlaeche());
    }

}
