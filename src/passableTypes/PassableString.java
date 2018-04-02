package passableTypes;

import java.io.Serializable;

public class PassableString implements Serializable {

    /** Generated serial id. */
    private static final long serialVersionUID = 1420008240453896588L;
    private String value = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
