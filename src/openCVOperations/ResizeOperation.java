package openCVOperations;

import java.awt.Dimension;

import javax.swing.JDialog;
import dialogs.OperationDialogBox;
import miscellaneous.IntFlagItem;
import passableTypes.PassableIntFlagItem;

import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ResizeOperation extends OpenCVOperation {

	Dimension absoluteResizeDims = new Dimension(0,0);
	Dimension scaleFactorDims = new Dimension(0,0);
	PassableIntFlagItem interpolationFlag = new PassableIntFlagItem();
	
	public ResizeOperation() {
		super();
		this.name.setValue("Resize Operation");
		this.outputName.setValue("Resize Output");
		
	}

	@Override
	public OpenCVOperation newOperationCopy() {
		ResizeOperation ro = new ResizeOperation();
		return ro;
	}

	@Override
	public JDialog openDialogBox() {
		OperationDialogBox odb = new OperationDialogBox();
		odb.addTextBox("Operation Name", "Resize Operation", this.name);
		
		odb.addSourceMatSelector("Input Operation", this);
//		odb.add2DDimension("Absolute Size", absoluteResizeDims, 1, 10000, 1, 1, 1, 10000, 1, 1, false );
//		odb.add2DDimension("Scale Factor", scaleFactorDims, 1, 100, 1, 1, 1, 10000, 1, 1, false );
		
		IntFlagItem[] interpolationFlags = {
				new IntFlagItem("INTER_NEAREST",0),
				new IntFlagItem("INTER_LINEAR",1),
				new IntFlagItem("INTER_CUBIC",2),
				new IntFlagItem("INTER_AREA",3),
				new IntFlagItem("INTER_LANCZOS4",4),
				new IntFlagItem("INTER_LINEAR_EXACT",5),
				new IntFlagItem("INTER_MAX ",7),
				new IntFlagItem("WARP_FILL_OUTLIERS",8),
				new IntFlagItem("WARP_INVERSE_MAP",16)
		};
		
		odb.addComboBox("Interpolation Type", interpolationFlags, interpolationFlag);
//		Imgproc.resize(this.getInputOperation().getOutputMat(), this.getOutputMat(), new Size( absoluteResizeDims.getWidth(), absoluteResizeDims.getHeight() ));

		odb.addTextBox("Output Name", "Resize Output", outputName);
		return odb.getDialog();
	}

	@Override
	public void performOperation() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

}
