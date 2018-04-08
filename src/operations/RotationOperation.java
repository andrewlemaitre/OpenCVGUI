package operations;

import java.awt.geom.Dimension2D;

import javax.swing.JDialog;
import javax.swing.SpinnerNumberModel;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.DoubleDimension;
import passableTypes.IOData;
import passableTypes.PassableDouble;

public class RotationOperation extends OpenCVOperation {

    /** The generated serialization ID */
    private static final long serialVersionUID = -1919920180943712873L;
    Dimension2D centerOfRotation = new DoubleDimension();
    PassableDouble angle = new PassableDouble();
    PassableDouble scale = new PassableDouble();
    IOData.ImageMat inputImg;
    IOData.ImageMat outputImg;

    public RotationOperation() {
        super();
        this.setOperationName("Rotation Operation");
        this.setOutputName("Rotation Output");
        inputImg = new IOData.ImageMat(this, "Input Image", IOData.IOType.INPUT);
        outputImg = new IOData.ImageMat(this, "Output Image", IOData.IOType.OUTPUT);
        this.addDataInput( inputImg );
        this.addDataOutput( outputImg );
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        return new RotationOperation();
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Operation Name", "Rotation Operation", this.getOperationNameObject());
        odb.addSourceMatSelector("Input Operation", this, inputImg);
        odb.add1DDimension("Angle", angle, getAngleModel());
        odb.add1DDimension("Scale", scale, getScaleModel());
        odb.add2DDimension("Center of Rotation", centerOfRotation, getCRXModel(), getCRYModel(), false);
        odb.addTextBox("Output Name", "Rotation Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(isValid() == false)
            return;
        Point COR = new Point(centerOfRotation.getWidth(), centerOfRotation.getHeight());
        Mat inputMat = this.inputImg.getData();
        Mat rotationMatrix = Imgproc.getRotationMatrix2D(COR, angle.getValue(), scale.getValue());
        Imgproc.warpAffine(inputMat, this.outputImg.getData(), rotationMatrix, new Size(inputMat.width(), inputMat.height()));
    }

    @Override
    public boolean isValid() {
        if(inputImg.getIOSource() == null )
            return errorMsg( String.format("Rotation Op. \"%s\" is not valid because %s data source is null.\n", this.getOperationName(), inputImg.getName()) );
        if(inputImg.getData().empty())
            return errorMsg( String.format("Rotation Op. \"%s\" is not valid because %s getData returned empty.\n", this.getOperationName(), inputImg.getName()));
        return true;
    }

    /** @return The SpinnerNumberModel for the angle spinner */
    private SpinnerNumberModel getAngleModel() {
        SpinnerNumberModel xModel = new SpinnerNumberModel( 0d, null, null, 0.1d);
        xModel.setValue((double)angle.getValue());
        return xModel;
    }

    /** @return The SpinnerNumberModel for the scale spinner */
    private SpinnerNumberModel getScaleModel() {
        SpinnerNumberModel xModel = new SpinnerNumberModel( 0d, 0d, null, 0.1d);
        xModel.setValue((double)scale.getValue());
        return xModel;
    }

    /** @return The SpinnerNumberModel for the X spinner of the center of rotation */
    private SpinnerNumberModel getCRXModel() {
        SpinnerNumberModel xModel = new SpinnerNumberModel( 0d, null, null, 1d);
        xModel.setValue((double)centerOfRotation.getWidth());
        return xModel;
    }

    /** @return The SpinnerNumberModel for the Y spinner of the center of rotation */
    private SpinnerNumberModel getCRYModel() {
        SpinnerNumberModel yModel = new SpinnerNumberModel( 0d, null, null, 1d);
        yModel.setValue((double)centerOfRotation.getHeight());
        return yModel;
    }

}
