import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Collections;

public class ObservableApp extends Application {

    final ObservableList<Integer> list = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = new AnchorPane();
        stage.setTitle("Observable");
        stage.setScene(new Scene(root));
        //stage.show();


        list.addListener((ListChangeListener<Integer>) change -> {
            while (change.next()) {
                if (change.wasAdded())
                    System.out.println("    added");
                if (change.wasPermutated())
                    System.out.println("    permutated");
                if (change.wasRemoved())
                    System.out.println("    removed");
                if (change.wasReplaced())
                    System.out.println("    replaced");
                if (change.wasUpdated())
                    System.out.println("    updated");
            }
        });

        System.out.println("Add element to list");
        list.addAll(1,2,3,4,5,6,7);

        System.out.println("Sort the list");
        Collections.sort(list);

        System.out.println("Remove element from list");
        list.remove(1);

        System.out.println("Replace element of list");
        list.set(2,10);
    }

}
