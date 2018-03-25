package operations;

import javax.swing.JDialog;

import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import miscellaneous.IntFlagItem;
import passableTypes.PassableInt;
import passableTypes.PassableIntFlagItem;

public class ThresholdOperation extends OpenCVOperation {

	PassableInt thresholdValue = new PassableInt( 20 );
	PassableInt maxThresholdValue = new PassableInt( 255 );
	PassableIntFlagItem threshFlag = new PassableIntFlagItem( "THRESH_BINARY", Imgproc.THRESH_BINARY );
	PassableIntFlagItem additionalThreshFlag = new PassableIntFlagItem( "NONE", 0);
	
	public ThresholdOperation()
	{
		this.setOperationName( "Threshold Operation ");
		this.setOutputName( "Threshold Output ");
	}
	
	@Override
	public JDialog openDialogBox() {
		
		OperationDialogBox odb = new OperationDialogBox();
		odb.addTextBox("Operation Name", "Threshold Name", this.getOperationNameObject());

		odb.addSourceMatSelector("Input Operation", this );
		odb.addSliderSetting("Threshold Min", 0, 255, thresholdValue.getValue(), thresholdValue);
		odb.addSliderSetting("Threshold Max", 0, 255, maxThresholdValue.getValue(), maxThresholdValue);
		
		IntFlagItem[] threshTypes = {
				new IntFlagItem("THRESH_BINARY",Imgproc.THRESH_BINARY),
				new IntFlagItem("THRESH_BINARY_INV",Imgproc.THRESH_BINARY_INV),
				new IntFlagItem("THRESH_TRUNC",Imgproc.THRESH_TRUNC),
				new IntFlagItem("THRESH_TOZERO",Imgproc.THRESH_TOZERO),
				new IntFlagItem("THRESH_TOZERO_INV",Imgproc.THRESH_TOZERO_INV)
		};
		odb.addComboBox("Threshold Type", threshTypes, threshFlag);
		
		IntFlagItem[] additionalThreshTypes = {
				new IntFlagItem("NONE",0),
				new IntFlagItem("THRESH_MASK",Imgproc.THRESH_MASK),
				new IntFlagItem("THRESH_OTSU",Imgproc.THRESH_OTSU),
				new IntFlagItem("THRESH_TRIANGLE ",Imgproc.THRESH_TRIANGLE)
		};
		odb.addComboBox("Threshold Type", additionalThreshTypes, additionalThreshFlag);
		
		odb.addTextBox("Output Name", "Threshold Output", this.getOutputNameObject());
		return odb.getDialog();
	}

	@Override
	public void performOperation() {
		if( this.getInputOperation() == null ) {
			System.err.println("No input specified for threshold operation" + this.getOperationName());
			return;
		}
		if( this.getInputOperation().getOutputMat().empty() )
			System.err.println("Input mat for threshold operation \"" + this.getOperationName() +"\" is empty. Did you configure the input operation?");
		
		Imgproc.threshold(this.getInputOperation().getOutputMat(), 
				this.getOutputMat(), 
				thresholdValue.getValue(), 
				maxThresholdValue.getValue(), 
				threshFlag.getValue().getValue()+additionalThreshFlag.getValue().getValue());
	}

	@Override
	public OpenCVOperation newOperationCopy() {
		ThresholdOperation operation = new ThresholdOperation();
		return operation;
	}

	@Override
	public boolean isValid() {
	    if( getInputOperation() == null || threshFlag == null || additionalThreshFlag == null )
	        return false;
		return true;
	}

}
