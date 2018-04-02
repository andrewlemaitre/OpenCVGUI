package miscellaneous;

import javax.swing.JMenuItem;

import passableTypes.IOData;

/**
 *
 */
public class OperationMenuItem extends JMenuItem {

    private static final long serialVersionUID = 1L;

    private IOData.ImageMat imageMat;

    public OperationMenuItem( final IOData.ImageMat imageMat ) {
        this.imageMat = imageMat;
    }

    public final IOData.ImageMat getImageMat() {
        return imageMat;
    }

    public final void setImageMat(final IOData.ImageMat imageMat) {
        this.imageMat = imageMat;
    }
}
