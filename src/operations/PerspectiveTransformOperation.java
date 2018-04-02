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
import passableTypes.IOData;

public class PerspectiveTransformOperation extends OpenCVOperation {

    /** Generated serial id. */
    private static final long serialVersionUID = 7855071774735900891L;
    Dimension2D point1 = new DoubleDimension();
    Dimension2D point2 = new DoubleDimension();
    Dimension2D point3 = new DoubleDimension();
    Dimension2D point4 = new DoubleDimension();
    Dimension2D point5 = new DoubleDimension();
    Dimension2D point6 = new DoubleDimension();
    Dimension2D point7 = new DoubleDimension();
    Dimension2D point8 = new DoubleDimension();

    public PerspectiveTransformOperation() {
        super();
        this.setOperationName("Perspective Tranform Operation");
        this.setOutputName("Perspective Tranform Output");
        this.addDataInput( new IOData.ImageMat(this, IOData.IOType.INPUT));
        this.addDataOutput( new IOData.ImageMat(this, IOData.IOType.OUTPUT));
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new PerspectiveTransformOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Operation Name", "Perspective Transform Operation", this.getOperationNameObject());
        odb.addSourceMatSelector("Input Operation", this);
        odb.add2DDimension("Point 1", point1, getModel(point1.getWidth()), getModel(point1.getHeight()), false);
        odb.add2DDimension("Point 2", point2, getModel(point2.getWidth()), getModel(point2.getHeight()), false);
        odb.add2DDimension("Point 3", point3, getModel(point3.getWidth()), getModel(point3.getHeight()), false);
        odb.add2DDimension("Point 4", point4, getModel(point4.getWidth()), getModel(point4.getHeight()), false);
        odb.add2DDimension("Point 5", point5, getModel(point5.getWidth()), getModel(point5.getHeight()), false);
        odb.add2DDimension("Point 6", point6, getModel(point6.getWidth()), getModel(point6.getHeight()), false);
        odb.add2DDimension("Point 7", point7, getModel(point7.getWidth()), getModel(point7.getHeight()), false);
        odb.add2DDimension("Point 8", point8, getModel(point8.getWidth()), getModel(point8.getHeight()), false);
        odb.addTextBox("Output Name", "Perspective Transform Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(isValid() == false)
            return;

        Point p1 = dimensionToPoint(point1);
        Point p2 = dimensionToPoint(point2);
        Point p3 = dimensionToPoint(point3);
        Point p4 = dimensionToPoint(point4);
        Point p5 = dimensionToPoint(point5);
        Point p6 = dimensionToPoint(point6);
        Point p7 = dimensionToPoint(point7);
        Point p8 = dimensionToPoint(point8);
        MatOfPoint2f points1 = new MatOfPoint2f(p1, p2, p3, p4);
        MatOfPoint2f points2 = new MatOfPoint2f(p5, p6, p7, p8);

        Mat perspectiveTransform = Imgproc.getPerspectiveTransform(points1, points2);
        Imgproc.warpPerspective(getInputMat(), getOutputMat(), perspectiveTransform, new Size( getInputMat().width(), getInputMat().height()));
    }

    private Point dimensionToPoint(Dimension2D dim) {
        Point p = new Point(dim.getWidth(), dim.getHeight());
        return p;
    }

    @Override
    public boolean isValid() {
        if(getInputOperation() == null)
            return false;
        return true;
    }

    /** @return The SpinnerNumberModel for the point spinners */
    private SpinnerNumberModel getModel(double initialValue) {
        SpinnerNumberModel model = new SpinnerNumberModel( 0d, null, null, 1d);
        model.setValue(initialValue);
        return model;
    }

}
