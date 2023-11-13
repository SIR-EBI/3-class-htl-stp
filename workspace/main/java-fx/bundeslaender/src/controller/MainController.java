package controller;

import domain.Bundesland;
import domain.Cell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainController {
    @FXML
    private ListView<Bundesland> listView;

    private ObservableList<Bundesland> list;

    @FXML
    void initialize() throws FileNotFoundException {
        list = FXCollections.observableArrayList(Bundesland.readFile(new FileInputStream("main/java-fx/bundeslaender/resource/bundeslaender.csv")));

        listView.getItems().addAll(list);

        listView.setCellFactory(event -> new Cell());

        listView.getSelectionModel().selectedItemProperty().addListener(event -> {
            Bundesland selected = listView.getSelectionModel().getSelectedItem();
            try {
                Stage editWindow = new Stage();
                editWindow.setTitle(selected.getName());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/edit.fxml"));
                editWindow.setScene(new Scene(loader.load()));

                EditController controller = loader.getController();
                controller.initEditWindow(selected);

                editWindow.showAndWait();
                listView.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
