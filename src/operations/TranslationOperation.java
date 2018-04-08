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
    IOData.ImageMat inputImg;
    IOData.ImageMat outputImg;

    public TranslationOperation() {
        super();
        this.setOperationName("Translation Operation");
        this.setOutputName("Translation Output");
        inputImg = new IOData.ImageMat(this, "Input Image", IOData.IOType.INPUT);
        outputImg = new IOData.ImageMat(this, "Output Image", IOData.IOType.OUTPUT);
        this.addDataInput( inputImg );
        this.addDataOutput( outputImg );
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new TranslationOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Operation Name", "Translation Operation", this.getOperationNameObject());
        odb.addSourceMatSelector("Input Operation", this, inputImg);
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

        int inputWidth = inputImg.getData().width(),
            inputHeight = inputImg.getData().height();

        Imgproc.warpAffine(inputImg.getData(), outputImg.getData(), translationMatrix, new Size(inputWidth, inputHeight));
    }

    @Override
    public boolean isValid() {
        if(inputImg.getIOSource() == null )
            return errorMsg( String.format("Translation Op. \"%s\" is not valid because %s data source is null.\n", this.getOperationName(), inputImg.getName()) );
        if(inputImg.getData().empty())
            return errorMsg( String.format("Translation Op. \"%s\" is not valid because %s getData returned empty.\n", this.getOperationName(), inputImg.getName()));
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
