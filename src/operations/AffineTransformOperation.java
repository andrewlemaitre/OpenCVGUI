package operations;

import java.awt.geom.Dimension2D;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.DoubleDimension;

public class AffineTransformOperation extends OpenCVOperation {

    /** Generated serial ID */
    private static final long serialVersionUID = -9114348354967838690L;
    Dimension2D point1 = new DoubleDimension();
    Dimension2D point2 = new DoubleDimension();
    Dimension2D point3 = new DoubleDimension();
    Dimension2D point4 = new DoubleDimension();
    Dimension2D point5 = new DoubleDimension();
    Dimension2D point6 = new DoubleDimension();
    
    public AffineTransformOperation() {
        super();
        this.setOperationName("Affine Tranform Operation");
        this.setOutputName("Affine Tranform Output");
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new AffineTransformOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox( "Operation Name", "Affine Transform Operation", this.getOperationNameObject() );
        odb.addSourceMatSelector( "Input Operation", this );
        odb.add2DDimension("Point 1", point1, getModel(point1.getWidth()), getModel(point1.getHeight()), false);
        odb.add2DDimension("Point 2", point2, getModel(point2.getWidth()), getModel(point2.getHeight()), false);
        odb.add2DDimension("Point 3", point3, getModel(point3.getWidth()), getModel(point3.getHeight()), false);
        odb.add2DDimension("Point 4", point4, getModel(point4.getWidth()), getModel(point4.getHeight()), false);
        odb.add2DDimension("Point 5", point5, getModel(point5.getWidth()), getModel(point5.getHeight()), false);
        odb.add2DDimension("Point 6", point6, getModel(point6.getWidth()), getModel(point6.getHeight()), false);
        odb.addTextBox( "Output Name", "Affine Transform Output", this.getOutputNameObject() );
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if( isValid() == false )
            return;
        
        Point p1 = dimensionToPoint(point1);
        Point p2 = dimensionToPoint(point2);
        Point p3 = dimensionToPoint(point3);
        Point p4 = dimensionToPoint(point4);
        Point p5 = dimensionToPoint(point5);
        Point p6 = dimensionToPoint(point6);
        MatOfPoint2f points1 = new MatOfPoint2f( p1, p2, p3 );
        MatOfPoint2f points2 = new MatOfPoint2f( p4, p5, p6 );
        
        Mat affineTransform = Imgproc.getAffineTransform(points1, points2);
        Imgproc.warpAffine(getInputMat(), getOutputMat(), affineTransform, new Size( getInputMat().width(), getInputMat().height() ));
    }
    
    private Point dimensionToPoint( Dimension2D dim ) {
        Point p = new Point( dim.getWidth(), dim.getHeight() );
        return p;
    }

    @Override
    public boolean isValid() {
        if( getInputOperation() == null )
            return false;
        
        return true;
    }
    
    /** @return The SpinnerNumberModel for the point spinners */
    private SpinnerNumberModel getModel( double initialValue ) {
        SpinnerNumberModel model = new SpinnerNumberModel( 0d, null, null, 1d);
        model.setValue( initialValue );
        return model;
    }

}
