package operations;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;

import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.IOData;
import passableTypes.PassableInt;

public class MedianBlurOperation extends OpenCVOperation {

    /** Generated serial id. */
    private static final long serialVersionUID = -7996500677291677791L;
    PassableInt kernelSize = new PassableInt(1);
    IOData.ImageMat inputImg;
    IOData.ImageMat outputImg;

    public MedianBlurOperation() {
        super();
        this.setOperationName("Median Blur Operation");
        this.setOutputName("Median Blur Output");
        inputImg = new IOData.ImageMat(this, "Input Image", IOData.IOType.INPUT);
        outputImg = new IOData.ImageMat(this, "Output Image", IOData.IOType.OUTPUT);
        this.addDataInput( inputImg );
        this.addDataOutput( outputImg );
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new MedianBlurOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Op Name", "Median Blur Operation", this.getOperationNameObject());

        odb.addSourceMatSelector("Input Operation", this, outputImg);

        odb.add1DDimension("Kernel Size", kernelSize, getKernelSizeModel());

        odb.addTextBox("Output Name", "Median Blur Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(isValid() == false)
            return;     
        Imgproc.medianBlur(inputImg.getData(), outputImg.getData(), kernelSize.getValue());
    }

    @Override
    public boolean isValid() {
        if(inputImg.getData().empty())
            return false;
        return true;
    }

    private SpinnerNumberModel getKernelSizeModel() {
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, null, 2);
        model.setValue(kernelSize.getValue());
        model.addChangeListener( evt -> {
            if((int)model.getValue()%2 == 0)
                model.setValue( (int)model.getValue() + 1);
        });
        return model;
    }
}
