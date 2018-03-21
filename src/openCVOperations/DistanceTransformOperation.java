package openCVOperations;

import javax.swing.JDialog;

import org.opencv.core.CvType;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import miscellaneous.IntFlagItem;
import passableTypes.PassableIntFlagItem;

public class DistanceTransformOperation extends OpenCVOperation {

	PassableIntFlagItem distanceType = new PassableIntFlagItem();
	PassableIntFlagItem maskSize = new PassableIntFlagItem();
	
	public DistanceTransformOperation()
	{
		this.name.setValue( "Distance Transform Operation ");
		this.outputName.setValue( "Distance Transform Output ");
	}
	
	//TODO: Add controls to enable both version of DistanceTransform. Maybe.
	@Override
	public JDialog openDialogBox() {
		OperationDialogBox odb = new OperationDialogBox( );
		odb.addTextBox("Operation Name", "Distance Transform Name", this.name);
		odb.addSourceMatSelector("Input Operation", this );
	
		IntFlagItem[] distanceTypes = {
				new IntFlagItem("DIST_USER", -1),
				new IntFlagItem("DIST_L1 ", 1),
				new IntFlagItem("DIST_L2 ", 2),
				new IntFlagItem("DIST_C", 3),
				new IntFlagItem("DIST_L12 ", 4),
				new IntFlagItem("DIST_FAIR ", 5),
				new IntFlagItem("DIST_WELSCH ", 6),
				new IntFlagItem("DIST_HUBER ", 7)
		};
		odb.addComboBox("Distance Type", distanceTypes, distanceType);
	
		IntFlagItem[] maskSizes = {
				new IntFlagItem("DIST_MASK_3", 3),
				new IntFlagItem("DIST_MASK_5", 5),
				new IntFlagItem("DIST_MASK_PRECISE", 0),
		};
		odb.addComboBox("Mask Size", maskSizes, maskSize);
	
		odb.addTextBox("Output Name", "Threshold Output", this.outputName);
		return odb;
	}

	@Override
	public void performOperation() {
		if( this.getInputOperation() == null ) {
			System.err.println("No input specified for threshold operation" + this.getOperationName());
			return;
		}
		if( this.getInputOperation().getOutputMat().empty() )
			System.out.println("Input mat for threshold operation \"" + this.getOperationName() +"\" is empty. Did you configure the input operation?");
		
//		getInputOperation().getOutputMat().convertTo(outputMat, CvType.CV_8U, 8);
		
		Imgproc.distanceTransform( 
				this.getInputOperation().getOutputMat(), 
				this.outputMat, 
				distanceType.getValue().getValue(), 
				maskSize.getValue().getValue());
		
//		System.out.println( outputMat.type() );
//		this.outputMat.convertTo(outputMat, org.opencv.core.CvType.CV_8U, 1);
	}

	@Override
	public OpenCVOperation newOperationCopy() {
		DistanceTransformOperation dto = new DistanceTransformOperation();
		return dto;
	}

	@Override
	public boolean isValid() {
		if( inputOperation != null )
			return true;
		return false;
	}

}
