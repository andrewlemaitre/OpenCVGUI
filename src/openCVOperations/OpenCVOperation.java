package openCVOperations;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.opencv.core.Mat;

import passableTypes.*;

public abstract class OpenCVOperation {

	JFrame parent;
	PassableString name = new PassableString();
	PassableString outputName = new PassableString();
	OpenCVOperation inputOperation;
	protected Mat outputMat = new Mat();

	public OpenCVOperation( JFrame parent, String name )
	{
		this.parent = parent;
		this.name.setValue(name);
	}

	public OpenCVOperation( JFrame parent )
	{
		this.parent = parent;
	}
	
	public OpenCVOperation( )
	{
	}

	public abstract OpenCVOperation newOperationCopy();

	public abstract JDialog openDialogBox( );

	public abstract void performOperation();
	
	public abstract boolean isValid();

	public String getOperationName() {
		return name.getValue();
	}

	public OpenCVOperation getInputOperation() {
		return inputOperation;
	}

	public void setInputOperation( OpenCVOperation inputOperation ) {
		this.inputOperation = inputOperation;
	}

	public Mat getOutputMat() {
		return outputMat;
	}

	public String getOutputName() {
		return outputName.getValue();
	}

	public void setOutputName(String outputName) {
		this.outputName.setValue( outputName );
	}
	
}