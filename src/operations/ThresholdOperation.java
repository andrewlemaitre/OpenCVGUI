package operations;

import javax.swing.JDialog;

import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.IOData;
import passableTypes.IntegerFlag;
import passableTypes.PassableInt;

public class ThresholdOperation extends OpenCVOperation {

    /** Generated serial ID */
    private static final long serialVersionUID = -1065117793316618879L;
    PassableInt thresholdValue = new PassableInt(20);
    PassableInt maxThresholdValue = new PassableInt(255);
    IntegerFlag threshFlag = new IntegerFlag("THRESH_BINARY", Imgproc.THRESH_BINARY);
    IntegerFlag additionalThreshFlag = new IntegerFlag( "NONE", 0);
    IOData.ImageMat inputImg;
    IOData.ImageMat outputImg;

    public ThresholdOperation() {
        this.setOperationName( "Threshold Operation ");
        this.setOutputName( "Threshold Output ");
        inputImg = new IOData.ImageMat(this, "Input Image", IOData.IOType.INPUT);
        outputImg = new IOData.ImageMat(this, "Output Image", IOData.IOType.OUTPUT);
        this.addDataInput( inputImg );
        this.addDataOutput( outputImg );
    }

    @Override
    public JDialog openDialogBox() {

        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Operation Name", "Threshold Name", this.getOperationNameObject());

        odb.addSourceMatSelector("Input Operation", this, inputImg);
        odb.addSliderSetting("Threshold Min", 0, 255, thresholdValue.getValue(), thresholdValue);
        odb.addSliderSetting("Threshold Max", 0, 255, maxThresholdValue.getValue(), maxThresholdValue);

        IntegerFlag[] threshTypes = {
                new IntegerFlag("THRESH_BINARY",Imgproc.THRESH_BINARY),
                new IntegerFlag("THRESH_BINARY_INV",Imgproc.THRESH_BINARY_INV),
                new IntegerFlag("THRESH_TRUNC",Imgproc.THRESH_TRUNC),
                new IntegerFlag("THRESH_TOZERO",Imgproc.THRESH_TOZERO),
                new IntegerFlag("THRESH_TOZERO_INV",Imgproc.THRESH_TOZERO_INV)
        };
        odb.addComboBox("Threshold Type", threshTypes, threshFlag);

        IntegerFlag[] additionalThreshTypes = {
                new IntegerFlag("NONE",0),
                new IntegerFlag("THRESH_MASK",Imgproc.THRESH_MASK),
                new IntegerFlag("THRESH_OTSU",Imgproc.THRESH_OTSU),
                new IntegerFlag("THRESH_TRIANGLE ",Imgproc.THRESH_TRIANGLE)
        };
        odb.addComboBox("Threshold Type", additionalThreshTypes, additionalThreshFlag);

        odb.addTextBox("Output Name", "Threshold Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(this.isValid() == false)
            return;

        if(this.inputImg.getData().empty())
            System.err.println("Input mat for threshold operation \"" + this.getOperationName() +"\" is empty. Did you configure the input operation?");

        Imgproc.threshold(this.inputImg.getData(), 
                this.outputImg.getData(), 
                thresholdValue.getValue(), 
                maxThresholdValue.getValue(), 
                threshFlag.getValue()+additionalThreshFlag.getValue());
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        ThresholdOperation operation = new ThresholdOperation();
        return operation;
    }

    @Override
    public boolean isValid() {
        if(inputImg.getIOSource() == null )
            return errorMsg( String.format("Threshold Op. \"%s\" is not valid because %s data source is null.\n", this.getOperationName(), inputImg.getName()) );
        if(inputImg.getData().empty())
            return errorMsg( String.format("Threshold Op. \"%s\" is not valid because %s getData returned empty.\n", this.getOperationName(), inputImg.getName()));
        if(threshFlag == null || additionalThreshFlag == null)
            return errorMsg( String.format("Threshold Op. \"%s\" is not valid because one of the thresh flags is null.\n"
                    + "\tThresh Flag:%i\n"
                    + "\tAdditional Thresh Flag:%i\n", this.getOperationName(), threshFlag, additionalThreshFlag ));
        return true;
    }

}
