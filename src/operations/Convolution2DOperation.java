package operations;

import javax.swing.JDialog;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.IOData;

public class Convolution2DOperation extends OpenCVOperation {

    /** Generated serial ID*/
    private static final long serialVersionUID = 1251931278856836509L;
    transient Mat kernelData = Mat.ones(1, 1, CvType.CV_32F);
    IOData.ImageMat inputImg;
    IOData.ImageMat outputImg;

    public Convolution2DOperation() {
        super();
        this.setOperationName("2D Convolution Operation");
        this.setOutputName("2D Convolution Output");
        inputImg = new IOData.ImageMat(this, "Input Image", IOData.IOType.INPUT);
        outputImg = new IOData.ImageMat(this, "Output Image", IOData.IOType.OUTPUT);
        this.addDataInput( inputImg );
        this.addDataOutput( outputImg );
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new Convolution2DOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Op Name", "2D Convolution Operation", this.getOperationNameObject());

        odb.addSourceMatSelector("Input Operation", this, inputImg);

        odb.addKernelBuilder("Kernel", kernelData);

        odb.addTextBox("Output Name", "2D Convolution Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(isValid() == false)
            return;

        Imgproc.filter2D(this.inputImg.getData(), this.outputImg.getData(), -1, kernelData);
    }

    @Override
    public boolean isValid() {
        if(inputImg.getIOSource() == null )
            return errorMsg( String.format("2D Convolution Op. \"%s\" is not valid because %s data source is null.\n", this.getOperationName(), inputImg.getName()) );
        if(inputImg.getData().empty())
            return errorMsg( String.format("2D Convolution Op. \"%s\" is not valid because %s getData returned empty.\n", this.getOperationName(), inputImg.getName()));
        return true;
    }

}
