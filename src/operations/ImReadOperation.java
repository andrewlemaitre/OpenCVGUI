package operations;

import java.io.File;

import javax.swing.JDialog;

import org.opencv.imgcodecs.Imgcodecs;

import dialogs.OperationDialogBox;
import passableTypes.IOData;
import passableTypes.IntegerFlag;
import passableTypes.PassableFile;

public class ImReadOperation extends OpenCVOperation {

    /** Generated serial ID */
    private static final long serialVersionUID = 6526718592030838820L;
    PassableFile passableFile = new PassableFile();
    IntegerFlag ifi = new IntegerFlag();
    IOData.ImageMat outputImg;

    public ImReadOperation() {
        this.setOperationName("Imread Name");
        this.setOutputName("Imread Output");
        this.ifi.setValue("No Flags Set", -2);
        outputImg = new IOData.ImageMat(this, "ImRead Output Image", IOData.IOType.OUTPUT);
        this.addDataOutput( outputImg );
    }

    @Override
    public JDialog openDialogBox() {
        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Op Name", "Image Operation", this.getOperationNameObject());

        String[] validFileTypes = {"gif", "jpg", "jpeg"};
        odb.addFileChooser("Load Image", "Images (gif, jpg, jpeg)", passableFile, validFileTypes);

        IntegerFlag[] intFlagItemList = {
            new IntegerFlag("No Flags Set", -2),
            new IntegerFlag("IMREAD_UNCHANGED",Imgcodecs.IMREAD_UNCHANGED),
            new IntegerFlag("IMREAD_GRAYSCALE",Imgcodecs.IMREAD_GRAYSCALE),
            new IntegerFlag("IMREAD_COLOR",Imgcodecs.IMREAD_COLOR),
            new IntegerFlag("IMREAD_ANYDEPTH",Imgcodecs.IMREAD_ANYDEPTH),
            new IntegerFlag("IMREAD_ANYCOLOR",Imgcodecs.IMREAD_ANYCOLOR),
            new IntegerFlag("IMREAD_LOAD_GDAL",Imgcodecs.IMREAD_LOAD_GDAL),
            new IntegerFlag("IMREAD_REDUCED_COLOR_2",Imgcodecs.IMREAD_REDUCED_COLOR_2),
            new IntegerFlag("IMREAD_REDUCED_COLOR_4",Imgcodecs.IMREAD_REDUCED_COLOR_4),
            new IntegerFlag("IMREAD_REDUCED_COLOR_8",Imgcodecs.IMREAD_REDUCED_COLOR_8),
            new IntegerFlag("IMREAD_REDUCED_GRAYSCALE_2",Imgcodecs.IMREAD_REDUCED_GRAYSCALE_2),
            new IntegerFlag("IMREAD_REDUCED_GRAYSCALE_4",Imgcodecs.IMREAD_REDUCED_GRAYSCALE_4),
            new IntegerFlag("IMREAD_REDUCED_GRAYSCALE_8",Imgcodecs.IMREAD_REDUCED_GRAYSCALE_8),
        };
        odb.addComboBox("Flags", intFlagItemList, ifi);
        odb.addTextBox("Output Name", "Image Read Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(this.isValid() == false)
            return;

        File file = null;
        int flag = ifi.getValue();

        if(passableFile.getValue() != null) {
            file = passableFile.getValue();
        }
        if(file == null) {
            System.err.println("Could not perform operation on " + getOperationName() + " because getFile() is null.");
            return;
        }
        if(flag != -2)
            this.outputImg.setData(Imgcodecs.imread( file.getPath(), flag ));
        else
            this.outputImg.setData(Imgcodecs.imread( file.getPath() ));
    }

    public PassableFile getFile() {
        return passableFile;
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        ImReadOperation iro = new ImReadOperation();
        return iro;
    }

    @Override
    public boolean isValid() {
        if(passableFile.getValue() == null || passableFile.getValue().exists() == false)
            return false;
        return true;
    }
}
