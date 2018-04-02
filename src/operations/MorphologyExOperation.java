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
import passableTypes.IntegerFlag;
import passableTypes.PassableInt;

public class MorphologyExOperation extends OpenCVOperation {

    /** Generated serial id. */
    private static final long serialVersionUID = -3113605158326090377L;
    IntegerFlag operationType = new IntegerFlag();
    transient Mat kernel = Mat.ones(1,  1, CvType.CV_32F);
    Dimension2D anchorPoint = new Dimension(-1,-1);
    PassableInt iterations = new PassableInt(1);
    IOData.ImageMat inputImg;
    IOData.ImageMat outputImg;

    public MorphologyExOperation() {
        super();
        this.setOperationName("MorphologyEx Operation");
        this.setOutputName("MorphologyEx Output");
        operationType.setValue(new IntegerFlag("MORPH_ERODE",Imgproc.MORPH_ERODE));
        
        inputImg = new IOData.ImageMat(this, "Input Image", IOData.IOType.INPUT);
        outputImg = new IOData.ImageMat(this, "Output Image", IOData.IOType.OUTPUT);
        this.addDataInput( inputImg );
        this.addDataOutput( outputImg );
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new MorphologyExOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Operation Name", "Erode Operation", this.getOperationNameObject());
        odb.addSourceMatSelector("Input Operation", this, outputImg);

        IntegerFlag[] operationTypesList = {
            new IntegerFlag("MORPH_ERODE",Imgproc.MORPH_ERODE),
            new IntegerFlag("MORPH_DILATE",Imgproc.MORPH_DILATE),
            new IntegerFlag("MORPH_OPEN",Imgproc.MORPH_OPEN),
            new IntegerFlag("MORPH_CLOSE",Imgproc.MORPH_CLOSE),
            new IntegerFlag("MORPH_GRADIENT",Imgproc.MORPH_GRADIENT),
            new IntegerFlag("MORPH_TOPHAT",Imgproc.MORPH_TOPHAT),
            new IntegerFlag("MORPH_BLACKHAT",Imgproc.MORPH_BLACKHAT),
            new IntegerFlag("MORPH_HITMISS",Imgproc.MORPH_HITMISS)
        };

        odb.addComboBox("Morph type", operationTypesList, operationType);
        odb.addKernelBuilder("Kernel", kernel);
        odb.add2DDimension("Anchor", anchorPoint, getAnchorXModel(), getAnchorYModel(), false);
        odb.add1DDimension("Iterations", iterations, getIterationsModel());

        odb.addTextBox("Output Name", "Erode Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(isValid() == false)
            return;
        Point anchor = new Point(anchorPoint.getWidth(), anchorPoint.getHeight());
        Imgproc.morphologyEx(inputImg.getData(), outputImg.getData(), operationType.getValue(), kernel, anchor, iterations.getValue());
    }

    @Override
    public boolean isValid() {
        if(inputImg.getData().empty())
            return false;
        return true;
    }

    private SpinnerNumberModel getAnchorXModel() {
        SpinnerNumberModel model = new SpinnerNumberModel(-1, -1, null, 1);
        model.setValue((int)anchorPoint.getWidth());
        return model;
    }

    private SpinnerNumberModel getAnchorYModel() {
        SpinnerNumberModel model = new SpinnerNumberModel(-1, -1, null, 1);
        model.setValue((int)anchorPoint.getHeight());
        return model;
    }

    private SpinnerNumberModel getIterationsModel() {
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, null, 1);
        model.setValue((int)iterations.getValue());
        return model;
    }

}
