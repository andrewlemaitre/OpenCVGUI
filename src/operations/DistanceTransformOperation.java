package operations;

import javax.swing.JDialog;

import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import passableTypes.IOData;
import passableTypes.IntegerFlag;

public class DistanceTransformOperation extends OpenCVOperation {

    /** Generated serial ID*/
    private static final long serialVersionUID = 1058056779285391129L;
    IntegerFlag distanceType = new IntegerFlag();
    IntegerFlag maskSize = new IntegerFlag();
    IOData.ImageMat inputImg;
    IOData.ImageMat outputImg;

    public DistanceTransformOperation() {
        this.setOperationName( "Distance Transform Operation ");
        this.setOutputName( "Distance Transform Output ");
        inputImg = new IOData.ImageMat(this, "Input Image", IOData.IOType.INPUT);
        outputImg = new IOData.ImageMat(this, "Output Image", IOData.IOType.OUTPUT);
        this.addDataInput( inputImg );
        this.addDataOutput( outputImg );
    }

    //TODO: Add controls to enable both version of DistanceTransform. Maybe.
    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Operation Name", "Distance Transform Name", this.getOperationNameObject());
        odb.addSourceMatSelector("Input Operation", this, inputImg);

        IntegerFlag[] distanceTypes = {
                new IntegerFlag("DIST_USER",Imgproc.DIST_USER),
                new IntegerFlag("DIST_L1 ",Imgproc.DIST_L1),
                new IntegerFlag("DIST_L2 ",Imgproc.DIST_L2),
                new IntegerFlag("DIST_C",Imgproc.DIST_C),
                new IntegerFlag("DIST_L12 ",Imgproc.DIST_L12),
                new IntegerFlag("DIST_FAIR ",Imgproc.DIST_FAIR),
                new IntegerFlag("DIST_WELSCH ",Imgproc.DIST_WELSCH),
                new IntegerFlag("DIST_HUBER ",Imgproc.DIST_HUBER)
        };
        odb.addComboBox("Distance Type", distanceTypes, distanceType);

        IntegerFlag[] maskSizes = {
                new IntegerFlag("DIST_MASK_3",Imgproc.DIST_MASK_3),
                new IntegerFlag("DIST_MASK_5",Imgproc.DIST_MASK_5),
                new IntegerFlag("DIST_MASK_PRECISE",Imgproc.DIST_MASK_PRECISE),
        };
        odb.addComboBox("Mask Size", maskSizes, maskSize);

        odb.addTextBox("Output Name", "Threshold Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(this.isValid() == false)
            return;

        if(this.inputImg.getData().empty())
            System.err.println("Input mat for threshold operation \"" + this.getOperationName() +"\" is empty. Did you configure the input operation?");

        Imgproc.distanceTransform( 
                this.inputImg.getData(), 
                this.outputImg.getData(), 
                distanceType.getValue(), 
                maskSize.getValue());

//        this.outputMat.convertTo(outputMat, org.opencv.core.CvType.CV_8U, 1);
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        DistanceTransformOperation dto = new DistanceTransformOperation();
        return dto;
    }

    @Override
    public boolean isValid() {
        if(inputImg.getIOSource() == null )
            return errorMsg( String.format("Distance Transform Op. \"%s\" is not valid because %s data source is null.\n", this.getOperationName(), inputImg.getName()) );
        if(inputImg.getData().empty())
            return errorMsg( String.format("Distance Transform Op. \"%s\" is not valid because %s getData returned empty.\n", this.getOperationName(), inputImg.getName()));
        if(distanceType == null || maskSize == null)
            return errorMsg( String.format("Distance Transform Op. \"%s\" is not valid because distance type is null.\n", this.getOperationName()));
        if(maskSize == null)
            return errorMsg( String.format("Distance Transform Op. \"%s\" is not valid because mask size is null.\n", this.getOperationName()));
        return true;
    }

}
