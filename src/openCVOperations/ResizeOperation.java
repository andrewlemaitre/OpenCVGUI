package openCVOperations;

import javax.swing.JDialog;
import dialogs.OperationDialogBox;

public class ResizeOperation extends OpenCVOperation {

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
		//odb.addDimensionSetting("Output Dimensions",outputDimension);
		//odb.addDimensionSetting("Scale Factor",outputScale);
		
		//Add ComboBox InterpolationFlags.
		//odb.addComboBox(label, itemList, passableIntFlagItem);

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
