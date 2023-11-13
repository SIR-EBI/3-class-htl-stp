import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

class CarCell extends ListCell<Car> {

    private HBox hbox;
    private Label model;
    private Label make;

    private boolean initialized = false;

    @Override
    protected void updateItem(Car item, boolean empty) {
        super.updateItem(item, empty); // always call this!
        if (!initialized) {
            initialize();
        }
        if (empty) {
            model.setText(null);
            make.setText(null);
        }
        else {
            model.setText(item.getName());
            make.setText(item.getMake());
        }
        setGraphic(hbox);
    }

    private void initialize(){
        hbox = new HBox(5);
        model = new Label();
        make = new Label();
        hbox.getChildren().addAll(model, make);

        initialized = true;

        hbox.setOnMouseClicked(event -> {
            System.out.println("name: " +model.getText() + "; make: " + make.getText());
        });
    }

}