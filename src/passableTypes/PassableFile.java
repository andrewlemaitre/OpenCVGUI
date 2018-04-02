package passableTypes;

import java.io.File;
import java.io.Serializable;

public class PassableFile implements Serializable {

    /** Generated serial id. */
    private static final long serialVersionUID = -1385791334673208194L;
    private File value;

    public void setValue(File f) {
        value = f;
    }

    public File getValue() {
        return value;
    }
}
