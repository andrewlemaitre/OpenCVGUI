package passableTypes;

import java.awt.geom.Dimension2D;
import java.io.Serializable;

public class DoubleDimension extends Dimension2D implements Serializable {

    /** Generated serial id */
    private static final long serialVersionUID = 48830963091945994L;
    double width;
    double height;
    
    public DoubleDimension() {}
    
    public DoubleDimension( double width, double height ) {
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
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

}
