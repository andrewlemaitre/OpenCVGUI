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
import passableTypes.IOData;
import passableTypes.PassableInt;

public class DilateOperation extends OpenCVOperation {

    /** Generated serial ID */
    private static final long serialVersionUID = 726139196898106423L;
    transient Mat kernel = Mat.ones(1,  1, CvType.CV_32F );
    Dimension2D anchorPoint = new Dimension(-1,-1);
    PassableInt iterations = new PassableInt(1);
    
    public DilateOperation() {
        super();
        this.setOperationName("Dilate Operation");
        this.setOutputName("Dilate Output");
        this.addDataInput( new IOData.ImageMat(this, IOData.IOType.INPUT));
        this.addDataOutput( new IOData.ImageMat(this, IOData.IOType.OUTPUT));
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new DilateOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox( "Operation Name", "Dilate Operation", this.getOperationNameObject() );
        odb.addSourceMatSelector( "Input Operation", this );
        
        odb.addKernelBuilder("Kernel", kernel);
        odb.add2DDimension("Anchor", anchorPoint, getAnchorXModel(), getAnchorYModel(), false);
        odb.add1DDimension("Iterations", iterations, getIterationsModel() );
        
        odb.addTextBox("Output Name", "Dilate Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if( isValid() == false )
            return;
        Point anchor = new Point( anchorPoint.getWidth(), anchorPoint.getHeight() );
        Imgproc.dilate(getInputMat(), getOutputMat(), kernel, anchor, iterations.getValue());
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
