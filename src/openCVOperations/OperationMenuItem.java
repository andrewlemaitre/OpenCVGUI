package openCVOperations;

import javax.swing.JMenuItem;

public class OperationMenuItem extends JMenuItem {
	
	private static final long serialVersionUID = 1L;
	private OpenCVOperation openCVOperation;

	public OperationMenuItem( OpenCVOperation openCVOperation )
	{
		super();
		this.openCVOperation = openCVOperation;
	}

	public OpenCVOperation getOpenCVOperation() {
		return openCVOperation;
	}

	public void setOpenCVOperation(OpenCVOperation openCVOperation) {
		this.openCVOperation = openCVOperation;
	}
}
