package passableTypes;

import java.awt.geom.Dimension2D;

public class DoubleDimension extends Dimension2D {

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
