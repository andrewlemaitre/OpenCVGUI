package operations;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;

import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import miscellaneous.IntFlagItem;
import passableTypes.DoubleDimension;
import passableTypes.PassableIntFlagItem;

public class ResizeOperation extends OpenCVOperation {

    Dimension2D absoluteResizeDims = new Dimension(0,0);
    Dimension2D scaleFactorDims = new DoubleDimension(0,0);
	PassableIntFlagItem interpolationFlag = new PassableIntFlagItem();
	
	public ResizeOperation() {
		super();
		this.setOperationName("Resize Operation");
		this.setOutputName("Resize Output");
	}

	@Override
	public OpenCVOperation newOperationCopy() {
		ResizeOperation ro = new ResizeOperation();
		return ro;
	}
	
    private SpinnerNumberModel getAbsXNumberModel() {
        SpinnerNumberModel absDimX = new SpinnerNumberModel( 0, 0, null, 1);
        absDimX.setValue( (int)absoluteResizeDims.getWidth() );
        return absDimX;
    }
    
    private SpinnerNumberModel getAbsYNumberModel() {
        SpinnerNumberModel absDimY = new SpinnerNumberModel( 0, 0, null, 1 );
        absDimY.setValue( (int)absoluteResizeDims.getHeight() );
        return absDimY;
    }

    private SpinnerNumberModel getScaleXNumberModel() {
        SpinnerNumberModel scaleX = new SpinnerNumberModel(0d, 0d, null, 0.1d);
        scaleX.setValue( (double)scaleFactorDims.getWidth() );
        return scaleX;
    }

    private SpinnerNumberModel getScaleYNumberModel() {
        SpinnerNumberModel scaleY = new SpinnerNumberModel(0d,0d, null,0.1d);
        scaleY.setValue( (double)scaleFactorDims.getHeight() );
        return scaleY;
    }

	@Override
	public JDialog openDialogBox() {
		OperationDialogBox odb = new OperationDialogBox();
		odb.addTextBox("Operation Name", "Resize Operation", this.getOutputNameObject());
		
		odb.addSourceMatSelector("Input Operation", this);
		odb.add2DDimension("Absolute Size", absoluteResizeDims, getAbsXNumberModel(), getAbsYNumberModel(), false );
		odb.add2DDimension("Scale Factor", scaleFactorDims, getScaleXNumberModel(), getScaleYNumberModel(), false );
		
		/* TODO: The INTER_MAX, WARP_FILL_OUTLIERS and WARP_INVERSE_MAP flags have been commented out 
		 * because they are not valid interpolation flags even though they are in the documentation.
		*/
		IntFlagItem[] interpolationFlags = {
				new IntFlagItem("INTER_NEAREST",0),
				new IntFlagItem("INTER_LINEAR",1),
				new IntFlagItem("INTER_CUBIC",2),
				new IntFlagItem("INTER_AREA",3),
				new IntFlagItem("INTER_LANCZOS4",4),
				new IntFlagItem("INTER_LINEAR_EXACT",5),
//				new IntFlagItem("INTER_MAX ",7),
//				new IntFlagItem("WARP_FILL_OUTLIERS",8),
//				new IntFlagItem("WARP_INVERSE_MAP",16)
		};
		
		JComboBox<IntFlagItem> comboBox = odb.addComboBox("Interpolation Type", interpolationFlags, interpolationFlag);
		odb.addTextBox("Output Name", "Resize Output", this.getOutputNameObject());
		return odb.getDialog();
	}

	@Override
	public void performOperation() {
        try {
            if( this.getInputOperation().getOutputMat().empty() ) {
                System.err.println("Input mat for resize operation \"" + this.getOperationName() +"\" is empty. Did you configure the input operation?");
                return;
            }
            Imgproc.resize(
                    this.getInputOperation().getOutputMat(), 
                    this.getOutputMat(), 
                    new Size( absoluteResizeDims.getWidth(), absoluteResizeDims.getHeight() ), 
                    scaleFactorDims.getWidth(), 
                    scaleFactorDims.getHeight(),
                    interpolationFlag.getValue().getValue() );
        } catch ( NullPointerException e ) {
            e.printStackTrace();
        }
	}

	@Override
	public boolean isValid() {
	    if( this.getInputOperation() == null )
	        return false;
		return true;
	}

}
