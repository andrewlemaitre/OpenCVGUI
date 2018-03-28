package operations;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.PassableInt;

public class ErodeOperation extends OpenCVOperation {

    /** Generated serial ID */
    private static final long serialVersionUID = -4773764472442344696L;
    transient Mat kernel = Mat.ones(1,  1, CvType.CV_32F );
    Dimension2D anchorPoint = new Dimension(-1,-1);
    PassableInt iterations = new PassableInt(1);
    
    public ErodeOperation() {
        super();
        this.setOperationName("Erode Operation");
        this.setOutputName("Erode Output");
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new ErodeOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox( "Operation Name", "Erode Operation", this.getOperationNameObject() );
        odb.addSourceMatSelector( "Input Operation", this );
        
        odb.addKernelBuilder("Kernel", kernel);
        odb.add2DDimension("Anchor", anchorPoint, getAnchorXModel(), getAnchorYModel(), false);
        odb.add1DDimension("Iterations", iterations, getIterationsModel() );
        
        odb.addTextBox("Output Name", "Erode Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if( isValid() == false )
            return;
        Point anchor = new Point( anchorPoint.getWidth(), anchorPoint.getHeight() );
        Imgproc.erode(getInputMat(), getOutputMat(), kernel, anchor, iterations.getValue());
    }

    @Override
    public boolean isValid() {
        if( getInputOperation() == null )
            return false;
        return true;
    }

    private SpinnerNumberModel getAnchorXModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( -1, -1, null, 1 );
        model.setValue( (int)anchorPoint.getWidth() );
        return model;
    }
    
    private SpinnerNumberModel getAnchorYModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( -1, -1, null, 1 );
        model.setValue( (int)anchorPoint.getHeight() );
        return model;
    }
    
    private SpinnerNumberModel getIterationsModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 1, 1, null, 1 );
        model.setValue( (int)iterations.getValue() );
        return model;
    }

}
