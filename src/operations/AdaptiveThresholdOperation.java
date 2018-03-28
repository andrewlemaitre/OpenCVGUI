package operations;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import miscellaneous.IntFlagItem;
import passableTypes.PassableInt;
import passableTypes.PassableIntFlagItem;

public class AdaptiveThresholdOperation extends OpenCVOperation {

    /** Generated serial ID */
    private static final long serialVersionUID = -1789885617590976187L;
    PassableInt maxValue = new PassableInt( 255 );
    PassableInt blockSize = new PassableInt( 3 );
    PassableInt subtractedConstant = new PassableInt( 1 );
    PassableIntFlagItem adaptiveMethod = new PassableIntFlagItem();
    PassableIntFlagItem thresholdType = new PassableIntFlagItem();
    
    
    public AdaptiveThresholdOperation() {
        super();
        this.setOperationName("Adaptive Threshold Operation");
        this.setOutputName("Adaptive Threshold Output");
        this.adaptiveMethod.setValue( new IntFlagItem("ADAPTIVE_THRESH_MEAN_C",Imgproc.ADAPTIVE_THRESH_MEAN_C) );
        this.thresholdType.setValue( new IntFlagItem("THRESH_BINARY",Imgproc.THRESH_BINARY) );
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new AdaptiveThresholdOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox( "Operation Name", "Adaptive Threshold Operation", this.getOperationNameObject() );

        odb.addSourceMatSelector( "Input Operation", this );
        
        odb.add1DDimension("Max Value", maxValue, getMaxValueModel());

        IntFlagItem[] adaptiveMethodList = {
                new IntFlagItem("ADAPTIVE_THRESH_MEAN_C",Imgproc.ADAPTIVE_THRESH_MEAN_C),
                new IntFlagItem("ADAPTIVE_THRESH_GAUSSIAN_C",Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C)
        };
        odb.addComboBox("Adaptive Method", adaptiveMethodList, adaptiveMethod);

        IntFlagItem[] thresholdTypeList = {
                new IntFlagItem("THRESH_BINARY",Imgproc.THRESH_BINARY),
                new IntFlagItem("THRESH_BINARY_INV)",Imgproc.THRESH_BINARY_INV)
        };
        odb.addComboBox("Threshold Type", thresholdTypeList, thresholdType);

        odb.add1DDimension("Block Size", blockSize, getBlockSizeModel());
        
        odb.add1DDimension("Subtracted Constant", subtractedConstant, getSubtractedConstantModel());
        
        odb.addTextBox("Output Name", "Adaptive Threshold Output", this.getOutputNameObject());
        
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if( isValid() == false )
            return;
        
        Imgproc.adaptiveThreshold(  getInputMat(), 
                                    getOutputMat(), 
                                    maxValue.getValue(), 
                                    adaptiveMethod.getValue().getValue(), 
                                    thresholdType.getValue().getValue(), 
                                    blockSize.getValue(), 
                                    subtractedConstant.getValue());
    }

    @Override
    public boolean isValid() {
        if( getInputOperation() == null )
            return false;
        return true;
    }
    
    private SpinnerNumberModel getMaxValueModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 255, 0, 255, 1 );
        model.setValue( maxValue.getValue() );
        return model;
    }
    
    private SpinnerNumberModel getBlockSizeModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 3, 3, null, 2 );
        model.addChangeListener( evt -> {
            if( (int)model.getValue()%2 == 0 )
                model.setValue( (int)model.getValue() + 1);
        });
        model.setValue( blockSize.getValue() );
        return model;
    }
    
    private SpinnerNumberModel getSubtractedConstantModel() {
        SpinnerNumberModel model = new SpinnerNumberModel( 0, 0, null, 1 );
        model.setValue( subtractedConstant.getValue() );
        return model;
    }

}