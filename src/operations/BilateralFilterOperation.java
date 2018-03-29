package operations;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;

import org.opencv.core.Core;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.IntegerFlag;
import passableTypes.PassableDouble;
import passableTypes.PassableInt;

public class BilateralFilterOperation extends OpenCVOperation {

    /** Generated serial ID */
    private static final long serialVersionUID = -1227250091069625529L;
    PassableInt filterSize = new PassableInt(1);
    PassableDouble sigmaColor = new PassableDouble(0d);
    PassableDouble sigmaSpace = new PassableDouble(0d);
    IntegerFlag borderType = new IntegerFlag();
    
    public BilateralFilterOperation() {
        super();
        this.setOperationName("Bilateral Filter Operation");
        this.setOutputName("Bilateral Filter Output");
        borderType.setValue( new IntegerFlag("BORDER_CONSTANT",Core.BORDER_CONSTANT));
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new BilateralFilterOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Op Name", "Bilateral Filter Operation", this.getOperationNameObject());

        odb.addSourceMatSelector("Input Operation", this );
        
        odb.add1DDimension("Filter Size", filterSize, getFilterSizeModel() );
        odb.add1DDimension("Sigma Color", sigmaColor, getSigmaColorModel() );
        odb.add1DDimension("Sigma Space", sigmaSpace, getSigmaSpaceModel() );
        
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
        
        odb.addTextBox("Output Name", "Bilateral Filter Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if( isValid() == false )
            return;
        
        Imgproc.bilateralFilter(getInputMat(), 
                                getOutputMat(), 
                                filterSize.getValue(), 
                                sigmaColor.getValue(), 
                                sigmaSpace.getValue() );
    }

    @Override
    public boolean isValid() {
        if( getInputOperation() == null )
            return false;
        return true;
    }
    
    private SpinnerNumberModel getFilterSizeModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 1, 1, null, 1 );
        model.setValue( (int)filterSize.getValue() );
        return model;
    }    
    
    private SpinnerNumberModel getSigmaColorModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 0d, 0d, null, 1d );
        model.setValue( sigmaColor.getValue() );
        return model;
    }
    
    private SpinnerNumberModel getSigmaSpaceModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 0d, 0d, null, 1d );
        model.setValue( sigmaSpace.getValue() );
        return model;
    }

}
