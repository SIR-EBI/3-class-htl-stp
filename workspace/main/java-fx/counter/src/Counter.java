import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Counter {

    private IntegerProperty number;

    public Counter() {
        number = new SimpleIntegerProperty(0);
    }
    public void increase() {
        number.set(number.getValue()+1);
    }

    public IntegerProperty valueProperty() {
        return number;
    }

    @Override
    public String toString() {
        return "" + number.getValue();
    }

}
