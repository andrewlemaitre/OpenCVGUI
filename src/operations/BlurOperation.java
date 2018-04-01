package operations;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;

import org.opencv.core.Core;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.IOData;
import passableTypes.IntegerFlag;

public class BlurOperation extends OpenCVOperation {

    /** Generated serial ID */
    private static final long serialVersionUID = 4200928567269729796L;
    Dimension2D kernelSize = new Dimension( 1, 1 );
    Dimension2D anchorPoint = new Dimension(-1,-1);
    IntegerFlag borderType = new IntegerFlag();
    
    public BlurOperation() {
        super();
        this.setOperationName("Blur Operation");
        this.setOutputName("Blur Output");
        borderType.setValue( new IntegerFlag("BORDER_CONSTANT",Core.BORDER_CONSTANT));
        this.addDataInput( new IOData.ImageMat(this, IOData.IOType.INPUT));
        this.addDataOutput( new IOData.ImageMat(this, IOData.IOType.OUTPUT));
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new BlurOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Op Name", "Blur Operation", this.getOperationNameObject());

        odb.addSourceMatSelector("Input Operation", this );
        
        odb.add2DDimension("Kernel Size", kernelSize, getKernelSizeXModel(), getKernelSizeYModel(), false );
        odb.add2DDimension("Anchor Point", anchorPoint, getAnchorXModel(), getAnchorYModel(), false);
        
        IntegerFlag[] borderTypeList = {
          new IntegerFlag("BORDER_CONSTANT",Core.BORDER_CONSTANT),
          new IntegerFlag("BORDER_REPLICATE",Core.BORDER_REPLICATE),
          new IntegerFlag("BORDER_REFLECT",Core.BORDER_REFLECT),
//          new IntFlagItem("BORDER_WRAP",Core.BORDER_WRAP), // Not valid for blur. See https://stackoverflow.com/questions/35454351/opencv-border-mode-issue-with-blur-filter
          new IntegerFlag("BORDER_REFLECT_101",Core.BORDER_REFLECT_101),
//          new IntFlagItem("BORDER_TRANSPARENT",Core.BORDER_TRANSPARENT), //Throws an error, possibly unsupported for blur.
          new IntegerFlag("BORDER_REFLECT101",Core.BORDER_REFLECT101), //Same as Reflect_101
          new IntegerFlag("BORDER_DEFAULT",Core.BORDER_DEFAULT), //Same as Reflect_101 and Reflect101.
          new IntegerFlag("BORDER_ISOLATED",Core.BORDER_ISOLATED),
        };
        odb.addComboBox("Border Type", borderTypeList, borderType);
        
        odb.addTextBox("Output Name", "Blur Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if( isValid() == false )
            return;
        Point anchor = new Point(anchorPoint.getWidth(), anchorPoint.getHeight());
        Size ksize = new Size( kernelSize.getWidth(), kernelSize.getHeight() );
        Imgproc.blur(getInputMat(), getOutputMat(), ksize, anchor, borderType.getValue());
    }

    @Override
    public boolean isValid() {
        if( getInputOperation() == null )
            return false;
        return true;
    }
    
    private SpinnerNumberModel getKernelSizeXModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 1, 1, null, 1 );
        model.setValue( (int)kernelSize.getWidth() );
        return model;
    }    
    private SpinnerNumberModel getKernelSizeYModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 1, 1, null, 1 );
        model.setValue( (int)kernelSize.getHeight() );
        return model;
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
}
