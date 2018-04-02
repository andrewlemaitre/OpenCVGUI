package miscellaneous;

import javax.swing.JMenuItem;

import operations.OpenCVOperation;

/**
 *
 */
public class OperationMenuItem extends JMenuItem {

    private static final long serialVersionUID = 1L;

    /** The operation associated with this menu item. */
    private OpenCVOperation openCVOperation;

    /**
     * @param openCVOperation The operation that this menu item will be associated with.
     */
    public OperationMenuItem(final OpenCVOperation openCVOperation) {
        super();
        this.openCVOperation = openCVOperation;
    }

    /**
     * @return Returns the openCVOperation associated with this menu item.
     */
    public final OpenCVOperation getOpenCVOperation() {
        return openCVOperation;
    }

    /**
     * @param openCVOperation sets the OpenCVOperation associated with this menu item.
     */
    public final void setOpenCVOperation(final OpenCVOperation openCVOperation) {
        this.openCVOperation = openCVOperation;
    }
}
