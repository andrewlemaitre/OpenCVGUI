package passableTypes;

import java.awt.geom.Dimension2D;
import java.io.Serializable;

/**
 * Encapsulates the width and height of an object in double precision. It is not meant for use with Components.
 * @author Andrew LeMaitre
 */
public final class DoubleDimension extends Dimension2D implements Serializable {

    /** Generated serial id. */
    private static final long serialVersionUID = 48830963091945994L;
    /** The width value of the DoubleDimension. */
    private double width;
    /** The height value of the DoubleDimension. */
    private double height;

    /** Creates a DoubleDimension with 0 width and 0 height. */
    public DoubleDimension() {
        this.width = 0;
        this.height = 0;
    }

    /**
     * Creates a DoubleDimension with preinitialized width and height values.
     * @param width The width or 'x' value of the dimension.
     * @param height The height or 'y' value of the dimension.
     */
    public DoubleDimension(final double width, final double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public void setSize(final double width, final double height) {
        this.width = width;
        this.height = height;
    }

}
