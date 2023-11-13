import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MittelwertController {

    @FXML
    private Pane arithmeticContainer;

    @FXML
    private Pane geometricContainer;

    @FXML
    private Pane harmonicContainer;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddValue;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnRemoveSelected;

    @FXML
    private CheckBox cbArithmetic;

    @FXML
    private CheckBox cbGeometric;

    @FXML
    private CheckBox cbHarmonic;

    @FXML
    private ListView<Double> listView;

    @FXML
    private Slider slider;

    @FXML
    private TextField tfNewValue;

    @FXML
    private Text txtArithmetic;

    @FXML
    private Text txtGeometric;

    @FXML
    private Text txtHarmonic;

    @FXML
    void btnClearAction(ActionEvent event) {
        listView.getItems().clear();
        tfNewValue.clear();
    }

    @FXML
    void btnRemoveAtcion(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedIndex() >= 0) {
            double selected = listView.getSelectionModel().getSelectedItem();
            listView.getItems().remove(selected);
        }
    }

    @FXML
    void cbArithmeticAction(ActionEvent event) {
        txtArithmetic.setVisible(cbArithmetic.isSelected());
        //arithmeticContainer.setVisible(cbArithmetic.isSelected());
    }

    @FXML
    void cbGeometricAction(ActionEvent event) {
        txtGeometric.setVisible(cbGeometric.isSelected());
        //geometricContainer.setVisible(cbGeometric.isSelected());
    }

    @FXML
    void cbHarmonicAction(ActionEvent event) {
        txtHarmonic.setVisible(cbHarmonic.isSelected());
        //harmonicContainer.setVisible(cbHarmonic.isSelected());
    }

    @FXML
    void btnAddAction(ActionEvent event) {
        addToListView();
    }

    @FXML
    void tfAction(ActionEvent event) {
        addToListView();
    }

    @FXML
    void initialize() {
        listView.getItems().addListener(new ListChangeListener<Double>() {
            @Override
            public void onChanged(Change<? extends Double> change) {
                setTexts();
            }
        });
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                setTexts();
            }
        });
    }

    private void addToListView() {
        String text = tfNewValue.getText();

        if (!checkIfDouble(text) || text.equals("NaN")) {
            Alert alert =  new Alert(Alert.AlertType.ERROR, "Liste kann nur Zahlen enthalten.",ButtonType.OK);
            alert.showAndWait();
        }
        else if (!text.trim().isEmpty()) {
            listView.getItems().add(Double.parseDouble(text));
        }
        tfNewValue.clear();
    }

    private void setTexts() {
        boolean containsNegative = false;
        for (double element : listView.getItems()) {
            if (element <= 0) {
                containsNegative = true;
                break;
            }
        }

        DecimalFormat df;
        if (slider.getValue() > 0) {
            df = new DecimalFormat("0.00000".substring(0, (int)slider.getValue() + 2));
        }
        else {
            df = new DecimalFormat("0");
        }

        txtArithmetic.setText(df.format(getArithmetic()));

        if (containsNegative) {
            txtGeometric.setText("Liste darf nur positive Werte enthalten.");
        }
        else {
            txtGeometric.setText(df.format(getGeometric()));
        }

        txtHarmonic.setText(df.format(getHarmonic()));
    }
    private double getArithmetic() {
        if (listView.getItems().isEmpty()) {
            return 0;
        }

        double arithmeticNumber = 0;
        for (double number : listView.getItems())
            arithmeticNumber += number;
        return arithmeticNumber / listView.getItems().size();
    }
    private double getGeometric() {
        if (listView.getItems().isEmpty()) {
            return 0;
        }

        double geometricNumber = listView.getItems().get(0);
        for (double number : listView.getItems())
            geometricNumber *= number;
        geometricNumber /= listView.getItems().get(0);
        return Math.pow(geometricNumber, 1.0 / listView.getItems().size());
    }

    private double getHarmonic() {
        if (listView.getItems().isEmpty()) {
            return 0;
        }

        double harmonicNumber = 0;
        for (double number : listView.getItems())
            harmonicNumber += 1 / number;
        return listView.getItems().size() / harmonicNumber;
    }

    private static boolean checkIfDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
