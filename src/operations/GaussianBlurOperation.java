package operations;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;

import org.opencv.core.Core;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.IOData;
import passableTypes.IntegerFlag;
import passableTypes.PassableDouble;

public class GaussianBlurOperation extends OpenCVOperation {

    /**Generated serial ID*/
    private static final long serialVersionUID = -1466707249518722675L;
    Dimension2D kernelSize = new Dimension( 1, 1 );
    PassableDouble sigmaX = new PassableDouble(0d);
    PassableDouble sigmaY = new PassableDouble(0d);
    IntegerFlag borderType = new IntegerFlag();
    
    public GaussianBlurOperation() {
        super();
        this.setOperationName("Gaussian Blur Operation");
        this.setOutputName("Gaussian Blur Output");
        borderType.setValue( new IntegerFlag("BORDER_CONSTANT",Core.BORDER_CONSTANT));
        this.addDataInput( new IOData.ImageMat(this, IOData.IOType.INPUT));
        this.addDataOutput( new IOData.ImageMat(this, IOData.IOType.OUTPUT));
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new GaussianBlurOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Op Name", "Gaussian Blur Operation", this.getOperationNameObject());

        odb.addSourceMatSelector("Input Operation", this );
        
        odb.add2DDimension("Kernel Size", kernelSize, getKernelSizeXModel(), getKernelSizeYModel(), false );
        odb.add1DDimension("Sigma X", sigmaX, getSigmaXModel());
        odb.add1DDimension("Sigma Y", sigmaY, getSigmaYModel());
        
        IntegerFlag[] borderTypeList = {
          new IntegerFlag("BORDER_CONSTANT",Core.BORDER_CONSTANT),
          new IntegerFlag("BORDER_REPLICATE",Core.BORDER_REPLICATE),
          new IntegerFlag("BORDER_REFLECT",Core.BORDER_REFLECT),
//          new IntFlagItem("BORDER_WRAP",Core.BORDER_WRAP), // Not valid for Gaussian blur. See https://stackoverflow.com/questions/35454351/opencv-border-mode-issue-with-blur-filter
          new IntegerFlag("BORDER_REFLECT_101",Core.BORDER_REFLECT_101),
//          new IntFlagItem("BORDER_TRANSPARENT",Core.BORDER_TRANSPARENT), //Throws an error, possibly unsupported for blur.
          new IntegerFlag("BORDER_REFLECT101",Core.BORDER_REFLECT101), //Same as Reflect_101
          new IntegerFlag("BORDER_DEFAULT",Core.BORDER_DEFAULT), //Same as Reflect_101 and Reflect101.
          new IntegerFlag("BORDER_ISOLATED",Core.BORDER_ISOLATED),
        };
        odb.addComboBox("Border Type", borderTypeList, borderType);
        
        odb.addTextBox("Output Name", "Gaussian Blur Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if( isValid() == false )
            return;
        Size ksize = new Size( kernelSize.getWidth(), kernelSize.getHeight() );        
        Imgproc.GaussianBlur(getInputMat(), getOutputMat(), ksize, sigmaX.getValue(), sigmaY.getValue(), borderType.getValue());
    }

    @Override
    public boolean isValid() {
        if( getInputOperation() == null )
            return false;
        return true;
    }

    private SpinnerNumberModel getKernelSizeXModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 1, 1, null, 2 );
        model.setValue( (int)kernelSize.getWidth() );
        model.addChangeListener( evt -> {
            if( (int)model.getValue()%2 == 0 )
                model.setValue( (int)model.getValue() + 1);
        });
        return model;
    }    
    private SpinnerNumberModel getKernelSizeYModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 1, 1, null, 2 );
        model.setValue( (int)kernelSize.getHeight() );
        model.addChangeListener( evt -> {
            if( (int)model.getValue()%2 == 0 )
                model.setValue( (int)model.getValue() + 1);
        });
        return model;
    }
    
    private SpinnerNumberModel getSigmaXModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 0d, 0d, null, 0.1d );
        model.setValue( sigmaX.getValue() );
        return model;
    }
    
    private SpinnerNumberModel getSigmaYModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 0d, 0d, null, 0.1d );
        model.setValue( sigmaY.getValue() );
        return model;
    }
}
