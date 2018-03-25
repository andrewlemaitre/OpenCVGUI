package operations;

import java.io.File;

import javax.swing.JDialog;

import org.opencv.imgcodecs.Imgcodecs;

import dialogs.OperationDialogBox;
import miscellaneous.IntFlagItem;
import passableTypes.PassableFile;
import passableTypes.PassableIntFlagItem;

public class ImReadOperation extends OpenCVOperation {

	PassableFile passableFile = new PassableFile();
	PassableIntFlagItem ifi = new PassableIntFlagItem();
	
	public ImReadOperation()
	{
		this.setOperationName("Imread Name");
		this.setOutputName("Imread Output");
	}

	@Override
	public JDialog openDialogBox() {
		OperationDialogBox odb = new OperationDialogBox( );
		odb.addTextBox("Op Name", "Image Operation", this.getOperationNameObject());
		
		String[] validFileTypes = {"gif", "jpg", "jpeg"};
		odb.addFileChooser("Load Image", "Images (gif, jpg, jpeg)", passableFile, validFileTypes);
		
		IntFlagItem[] intFlagItemList = {
			new IntFlagItem("No Flags Set", -2),
			new IntFlagItem("IMREAD_UNCHANGED", -1),
			new IntFlagItem("IMREAD_GRAYSCALE", 0),
			new IntFlagItem("IMREAD_COLOR", 1),
			new IntFlagItem("IMREAD_ANYDEPTH", 2),
			new IntFlagItem("IMREAD_ANYCOLOR", 4),
			new IntFlagItem("IMREAD_LOAD_GDAL", 8),
			new IntFlagItem("IMREAD_REDUCED_COLOR_2", 17),
			new IntFlagItem("IMREAD_REDUCED_COLOR_4", 33),
			new IntFlagItem("IMREAD_REDUCED_COLOR_8", 65),
			new IntFlagItem("IMREAD_REDUCED_GRAYSCALE_2", 16),
			new IntFlagItem("IMREAD_REDUCED_GRAYSCALE_4", 32),
			new IntFlagItem("IMREAD_REDUCED_GRAYSCALE_8", 64),
		};
		odb.addComboBox("Flags", intFlagItemList, ifi);
		odb.addTextBox("Output Name", "Image Read Output", this.getOutputNameObject());
		return odb.getDialog();
	}

	@Override
	public void performOperation() {
		if( this.isValid() == false )
		    return;
		
		File file = null;
		int flag = -2;
		
		if( ifi.getValue() != null ) {
			flag = ifi.getValue().getValue();
		}
		if( passableFile.getValue() != null ) {
			file = passableFile.getValue();
		}
		if( file == null ) {
			System.err.println("Could not perform operation on " + getOperationName() + " because getFile() is null.");
			return;
		}
		if( flag != -2 )
			this.setOutputMat( Imgcodecs.imread( file.getPath(), flag ) );
		else
			this.setOutputMat( Imgcodecs.imread( file.getPath() ) );
	}
	
	public PassableFile getFile()
	{
        return passableFile;
	}

	@Override
	public OpenCVOperation newOperationCopy() {
		ImReadOperation iro = new ImReadOperation();
		return iro;
	}

	@Override
	public boolean isValid() {
	    if( passableFile.getValue() == null || passableFile.getValue().exists() == false )
	        return false;
		return true;
	}
}
