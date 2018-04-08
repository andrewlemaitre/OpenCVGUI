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

public class ErodeOperation extends OpenCVOperation {

    /** Generated serial id. */
    private static final long serialVersionUID = -4773764472442344696L;
    transient Mat kernel = Mat.ones(1,  1, CvType.CV_32F);
    Dimension2D anchorPoint = new Dimension(-1,-1);
    PassableInt iterations = new PassableInt(1);
    IOData.ImageMat inputImg;
    IOData.ImageMat outputImg;

    public ErodeOperation() {
        super();
        this.setOperationName("Erode Operation");
        this.setOutputName("Erode Output");
        inputImg = new IOData.ImageMat(this, "Input Image", IOData.IOType.INPUT);
        outputImg = new IOData.ImageMat(this, "Output Image", IOData.IOType.OUTPUT);
        this.addDataInput( inputImg );
        this.addDataOutput( outputImg );
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new ErodeOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Operation Name", "Erode Operation", this.getOperationNameObject());
        odb.addSourceMatSelector("Input Operation", this, inputImg);

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
        Imgproc.erode(inputImg.getData(), outputImg.getData(), kernel, anchor, iterations.getValue());
    }

    @Override
    public boolean isValid() {
        if(inputImg.getIOSource() == null )
            return errorMsg( String.format("Erode Op. \"%s\" is not valid because %s data source is null.\n", this.getOperationName(), inputImg.getName()) );
        if(inputImg.getData().empty())
            return errorMsg( String.format("Erode Op. \"%s\" is not valid because %s getData returned empty.\n", this.getOperationName(), inputImg.getName()));
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
