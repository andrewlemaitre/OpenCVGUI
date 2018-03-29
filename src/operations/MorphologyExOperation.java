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
import miscellaneous.IntFlagItem;
import passableTypes.PassableInt;

public class MorphologyExOperation extends OpenCVOperation {

    /** Generated serial ID */
    private static final long serialVersionUID = -3113605158326090377L;
    IntFlagItem operationType = new IntFlagItem();
    transient Mat kernel = Mat.ones(1,  1, CvType.CV_32F );
    Dimension2D anchorPoint = new Dimension(-1,-1);
    PassableInt iterations = new PassableInt(1);
    
    public MorphologyExOperation() {
        super();
        this.setOperationName("MorphologyEx Operation");
        this.setOutputName("MorphologyEx Output");
        operationType.setValue(new IntFlagItem("MORPH_ERODE",Imgproc.MORPH_ERODE));
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new MorphologyExOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox( "Operation Name", "Erode Operation", this.getOperationNameObject() );
        odb.addSourceMatSelector( "Input Operation", this );
        
        IntFlagItem[] operationTypesList = {
            new IntFlagItem("MORPH_ERODE",Imgproc.MORPH_ERODE),
            new IntFlagItem("MORPH_DILATE",Imgproc.MORPH_DILATE),
            new IntFlagItem("MORPH_OPEN",Imgproc.MORPH_OPEN),
            new IntFlagItem("MORPH_CLOSE",Imgproc.MORPH_CLOSE),
            new IntFlagItem("MORPH_GRADIENT",Imgproc.MORPH_GRADIENT),
            new IntFlagItem("MORPH_TOPHAT",Imgproc.MORPH_TOPHAT),
            new IntFlagItem("MORPH_BLACKHAT",Imgproc.MORPH_BLACKHAT),
            new IntFlagItem("MORPH_HITMISS",Imgproc.MORPH_HITMISS)
        };
        
        odb.addComboBox("Morph type", operationTypesList, operationType);
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
        Imgproc.morphologyEx(getInputMat(), getOutputMat(), operationType.getValue(), kernel, anchor, iterations.getValue());
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
