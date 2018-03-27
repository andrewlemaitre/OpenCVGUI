package operations;

import javax.swing.JDialog;
import javax.swing.JPopupMenu;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;

public class Convolution2DOperation extends OpenCVOperation {

    transient Mat kernelData = Mat.ones(1, 1, CvType.CV_32F);
    
    public Convolution2DOperation() {
        super();
        this.setOperationName("2D Convolution Operation");
        this.setOutputName("2D Convolution Output");
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new Convolution2DOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Op Name", "2D Convolution Operation", this.getOperationNameObject());

        odb.addSourceMatSelector("Input Operation", this );
        
        odb.addKernelBuilder( "Kernel", kernelData );
        
        odb.addTextBox("Output Name", "2D Convolution Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if( isValid() == false )
            return;
        
        Imgproc.filter2D(this.getInputMat(), this.getOutputMat(), -1, kernelData);
    }

    @Override
    public boolean isValid() {
        if( getInputOperation() == null )
            return false;
        return true;
    }

}
