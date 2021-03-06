package operations;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;

import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.DoubleDimension;
import passableTypes.IOData;
import passableTypes.IntegerFlag;

public class ResizeOperation extends OpenCVOperation {

    /** Generated serial id. */
    private static final long serialVersionUID = -1284828314506751508L;
    Dimension2D absoluteResizeDims = new Dimension(0,0);
    Dimension2D scaleFactorDims = new DoubleDimension(0,0);
    IntegerFlag interpolationFlag = new IntegerFlag();
    IOData.ImageMat inputImg;
    IOData.ImageMat outputImg;

    public ResizeOperation() {
        super();
        this.setOperationName("Resize Operation");
        this.setOutputName("Resize Output");
        interpolationFlag.setValue("INTER_NEAREST",Imgproc.INTER_NEAREST);
        inputImg = new IOData.ImageMat(this, "Input Image", IOData.IOType.INPUT);
        outputImg = new IOData.ImageMat(this, "Output Image", IOData.IOType.OUTPUT);
        this.addDataInput( inputImg );
        this.addDataOutput( outputImg );
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        ResizeOperation ro = new ResizeOperation();
        return ro;
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Operation Name", "Resize Operation", this.getOutputNameObject());

        odb.addSourceMatSelector("Input Operation", this, inputImg);
        odb.add2DDimension("Absolute Size", absoluteResizeDims, getAbsXNumberModel(), getAbsYNumberModel(), false);
        odb.add2DDimension("Scale Factor", scaleFactorDims, getScaleXNumberModel(), getScaleYNumberModel(), false);

        /* TODO: The INTER_MAX, WARP_FILL_OUTLIERS and WARP_INVERSE_MAP flags have been commented out 
         * because they are not valid interpolation flags even though they are in the documentation.
        */
        IntegerFlag[] interpolationFlags = {
                new IntegerFlag("INTER_NEAREST",Imgproc.INTER_NEAREST),
                new IntegerFlag("INTER_LINEAR",Imgproc.INTER_LINEAR),
                new IntegerFlag("INTER_CUBIC",Imgproc.INTER_CUBIC),
                new IntegerFlag("INTER_AREA",Imgproc.INTER_AREA),
                new IntegerFlag("INTER_LANCZOS4",Imgproc.INTER_LANCZOS4),
                new IntegerFlag("INTER_LINEAR_EXACT",Imgproc.INTER_LINEAR_EXACT),
//                new IntFlagItem("INTER_MAX ",Imgproc.INTER_MAX),
//                new IntFlagItem("WARP_FILL_OUTLIERS",Imgproc.WARP_FILL_OUTLIERS),
//                new IntFlagItem("WARP_INVERSE_MAP",Imgproc.WARP_INVERSE_MAP)
        };

        odb.addComboBox("Interpolation Type", interpolationFlags, interpolationFlag);
        odb.addTextBox("Output Name", "Resize Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(this.isValid() == false)
            return;

        try {
            if(this.inputImg.getData().empty()) {
                System.err.println("Input mat for resize operation \"" + this.getOperationName() +"\" is empty. Did you configure the input operation?");
                return;
            }
            Imgproc.resize(
                    this.inputImg.getData(), 
                    this.outputImg.getData(), 
                    new Size(absoluteResizeDims.getWidth(), absoluteResizeDims.getHeight()), 
                    scaleFactorDims.getWidth(), 
                    scaleFactorDims.getHeight(),
                    interpolationFlag.getValue());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValid() {
        if(inputImg.getIOSource() == null )
            return errorMsg( String.format("Resize Op. \"%s\" is not valid because %s data source is null.\n", this.getOperationName(), inputImg.getName()) );
        if(inputImg.getData().empty())
            return errorMsg( String.format("Resize Op. \"%s\" is not valid because %s getData returned empty.\n", this.getOperationName(), inputImg.getName()));
        //If either absolute width or height is greater than 0 and the other is equal to 0, then return false.
        if(( absoluteResizeDims.getWidth() > 0 || absoluteResizeDims.getHeight() > 0) && 
               (absoluteResizeDims.getWidth() == 0 || absoluteResizeDims.getHeight() == 0 ))
            return errorMsg( String.format("Resize Op. \"%s\" is not valid because one of the absolute size dimensions is greater than 0, "
                    + "but the other is still 0.\n"
                    + "\twidth=%d"
                    + "\theight=%d", this.getOperationName(), absoluteResizeDims.getWidth(), absoluteResizeDims.getHeight()));
        /* If both the absolute width and height are 0, then the function will use the scale factor values.
         * So, if either of the scale factor dimensions are equal to 0, return false.
         */
        if(( absoluteResizeDims.getWidth() == 0 && absoluteResizeDims.getHeight() == 0) && 
                (scaleFactorDims.getWidth() == 0 || scaleFactorDims.getHeight() == 0 ))
            return false;
        if(interpolationFlag == null)
            return false;
        return true;
    }

    private SpinnerNumberModel getAbsXNumberModel() {
        SpinnerNumberModel absDimX = new SpinnerNumberModel( 0, 0, null, 1);
        absDimX.setValue((int)absoluteResizeDims.getWidth());
        return absDimX;
    }

    private SpinnerNumberModel getAbsYNumberModel() {
        SpinnerNumberModel absDimY = new SpinnerNumberModel(0, 0, null, 1);
        absDimY.setValue((int)absoluteResizeDims.getHeight());
        return absDimY;
    }

    private SpinnerNumberModel getScaleXNumberModel() {
        SpinnerNumberModel scaleX = new SpinnerNumberModel(0d, 0d, null, 0.1d);
        scaleX.setValue((double)scaleFactorDims.getWidth());
        return scaleX;
    }

    private SpinnerNumberModel getScaleYNumberModel() {
        SpinnerNumberModel scaleY = new SpinnerNumberModel(0d,0d, null,0.1d);
        scaleY.setValue((double)scaleFactorDims.getHeight());
        return scaleY;
    }

}
