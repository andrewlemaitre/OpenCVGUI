package operations;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;

import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import miscellaneous.IntFlagItem;
import passableTypes.DoubleDimension;
import passableTypes.PassableIntFlagItem;

public class ResizeOperation extends OpenCVOperation {

    /**
     * 
     */
    private static final long serialVersionUID = -1284828314506751508L;
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
				new IntFlagItem("INTER_NEAREST",Imgproc.INTER_NEAREST),
				new IntFlagItem("INTER_LINEAR",Imgproc.INTER_LINEAR),
				new IntFlagItem("INTER_CUBIC",Imgproc.INTER_CUBIC),
				new IntFlagItem("INTER_AREA",Imgproc.INTER_AREA),
				new IntFlagItem("INTER_LANCZOS4",Imgproc.INTER_LANCZOS4),
				new IntFlagItem("INTER_LINEAR_EXACT",Imgproc.INTER_LINEAR_EXACT),
//				new IntFlagItem("INTER_MAX ",Imgproc.INTER_MAX ),
//				new IntFlagItem("WARP_FILL_OUTLIERS",Imgproc.WARP_FILL_OUTLIERS),
//				new IntFlagItem("WARP_INVERSE_MAP",Imgproc.WARP_INVERSE_MAP)
		};
		
		odb.addComboBox("Interpolation Type", interpolationFlags, interpolationFlag);
		odb.addTextBox("Output Name", "Resize Output", this.getOutputNameObject());
		return odb.getDialog();
	}

	@Override
	public void performOperation() {
	    if( this.isValid() == false )
	        return;
	    
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
	    //If either absolute width or height is greater than 0 and the other is equal to 0, then return false.
	    if(    ( absoluteResizeDims.getWidth() > 0 || absoluteResizeDims.getHeight() > 0 ) && 
	           ( absoluteResizeDims.getWidth() == 0 || absoluteResizeDims.getHeight() == 0 ) )
	        return false;
	    /* If both the absolute width and height are 0, then the function will use the scale factor values.
	     * So, if either of the scale factor dimensions are equal to 0, return false.
	     */
	    if(    ( absoluteResizeDims.getWidth() == 0 && absoluteResizeDims.getHeight() == 0 ) && 
	            ( scaleFactorDims.getWidth() == 0 || scaleFactorDims.getHeight() == 0 ) )
	        return false;
	    if( interpolationFlag == null )
	        return false;
		return true;
	}

}
