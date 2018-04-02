package operations;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.IOData;

public class TranslationOperation extends OpenCVOperation {

    /** Generated serial id. */
    private static final long serialVersionUID = -2603659127294991627L;
    Dimension offset = new Dimension();

    public TranslationOperation() {
        super();
        this.setOperationName("Translation Operation");
        this.setOutputName("Translation Output");
        this.addDataInput( new IOData.ImageMat(this, IOData.IOType.INPUT));
        this.addDataOutput( new IOData.ImageMat(this, IOData.IOType.OUTPUT));
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new TranslationOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Operation Name", "Translation Operation", this.getOperationNameObject());
        odb.addSourceMatSelector("Input Operation", this);
        odb.add2DDimension("Displacement", offset, getXModel(), getYModel(), false);
        odb.addTextBox("Output Name", "Translation Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(isValid() == false)
            return;

        Mat translationMatrix = new Mat(2, 3, CvType.CV_32F);
        translationMatrix.put(0, 0, 1f, 0f, offset.getWidth());
        translationMatrix.put(1, 0, 0f, 1f, offset.getHeight());

        int inputWidth = getInputMat().width(),
            inputHeight = getInputMat().height();

        Imgproc.warpAffine(getInputMat(), getOutputMat(), translationMatrix, new Size(inputWidth, inputHeight));
    }

    @Override
    public boolean isValid() {
        if(this.getInputOperation() == null)
            return false;
        return true;
    }

    private SpinnerNumberModel getXModel() {
        SpinnerNumberModel xModel = new SpinnerNumberModel( 0, null, null, 1);
        xModel.setValue((int)offset.getWidth());
        return xModel;
    }

    private SpinnerNumberModel getYModel() {
        SpinnerNumberModel yModel = new SpinnerNumberModel( 0, null, null, 1);
        yModel.setValue((int)offset.getHeight());
        return yModel;
    }

}
