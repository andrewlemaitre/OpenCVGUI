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

    /** Generated serial id. */
    private static final long serialVersionUID = 726139196898106423L;
    transient Mat kernel = Mat.ones(1,  1, CvType.CV_32F);
    Dimension2D anchorPoint = new Dimension(-1,-1);
    PassableInt iterations = new PassableInt(1);
    IOData.ImageMat inputImg;
    IOData.ImageMat outputImg;

    public DilateOperation() {
        super();
        this.setOperationName("Dilate Operation");
        this.setOutputName("Dilate Output");
        inputImg = new IOData.ImageMat(this, "Input Image", IOData.IOType.INPUT);
        outputImg = new IOData.ImageMat(this, "Output Image", IOData.IOType.OUTPUT);
        this.addDataInput( inputImg );
        this.addDataOutput( outputImg );
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new DilateOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Operation Name", "Dilate Operation", this.getOperationNameObject());
        odb.addSourceMatSelector("Input Operation", this, inputImg);

        odb.addKernelBuilder("Kernel", kernel);
        odb.add2DDimension("Anchor", anchorPoint, getAnchorXModel(), getAnchorYModel(), false);
        odb.add1DDimension("Iterations", iterations, getIterationsModel());

        odb.addTextBox("Output Name", "Dilate Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(isValid() == false)
            return;
        Point anchor = new Point(anchorPoint.getWidth(), anchorPoint.getHeight());
        Imgproc.dilate(inputImg.getData(), outputImg.getData(), kernel, anchor, iterations.getValue());
    }

    @Override
    public boolean isValid() {
        if(inputImg.getIOSource() == null )
            return errorMsg( String.format("Dilate Op. \"%s\" is not valid because %s data source is null.\n", this.getOperationName(), inputImg.getName()) );
        if(inputImg.getData().empty())
            return errorMsg( String.format("Dilate Op. \"%s\" is not valid because %s getData returned empty.\n", this.getOperationName(), inputImg.getName()));
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
