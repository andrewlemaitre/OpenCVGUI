package passableTypes;

import java.io.Serializable;

public class PassableDouble implements Serializable {

    /** Generated serial id. */
    private static final long serialVersionUID = -3938763484007618097L;
    private double value;

    public PassableDouble() {}

    public PassableDouble(double value) {
        this.value = value;
    }

    public void setValue(double i) {
        value = i;
    }

    public double getValue() {
        return value;
    }
}
