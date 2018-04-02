package operations;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import miscellaneous.IntFlagMenuItem;
import passableTypes.IOData;
import passableTypes.IntegerFlag;

public class CvtColorOperation extends OpenCVOperation {

    /** Generated serial ID*/
    private static final long serialVersionUID = -2059644508847351793L;
    IntegerFlag selectedColorConversion = new IntegerFlag();
    private final int unselectedFlag = -2;

    public CvtColorOperation(){
        this.setOperationName("Convert Color Operation");
        this.setOutputName("Convert Color Output");
        this.selectedColorConversion.setValue(unselectedFlag);
        this.addDataInput( new IOData.ImageMat(this, IOData.IOType.INPUT));
        this.addDataOutput( new IOData.ImageMat(this, IOData.IOType.OUTPUT));
    }

    @Override
    public JDialog openDialogBox() {

        OperationDialogBox odb = new OperationDialogBox();
        odb.addTextBox("Op Name", "Convert Color Operation", this.getOperationNameObject());

        odb.addSourceMatSelector("Input Operation", this);

        JPopupMenu popupMenu = createPopupMenu();
        odb.addPopUpMenu("Convert Color Flag", selectedColorConversion, popupMenu);

        odb.addTextBox("Output Name", "Convert Color Output", this.getOutputNameObject());
        return odb.getDialog();
    }

    @Override
    public void performOperation() {
        if(this.isValid() == false)
            return; 

        Imgproc.cvtColor(this.getInputOperation().getOutputMat(), this.getOutputMat(), selectedColorConversion.getValue());
    }

    @Override
    public OpenCVOperation newOperationCopy() {
        CvtColorOperation cco = new CvtColorOperation();
        return cco;
    }

    @Override
    public boolean isValid() {
        if(this.getInputOperation() == null || selectedColorConversion.getValue() == unselectedFlag || selectedColorConversion == null)
            return false;
        return true;
    }

    //TODO: Only show menu options that are valid conversion types for the selected input mat.
    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenu mnBayer = new JMenu("BAYER TO");
        popupMenu.add(mnBayer);
        mnBayer.add( createIFMI("COLOR_BayerBG2BGR",Imgproc.COLOR_BayerBG2BGR));
        mnBayer.add( createIFMI("COLOR_BayerBG2BGR_EA",Imgproc.COLOR_BayerBG2BGR_EA));
        mnBayer.add( createIFMI("COLOR_BayerBG2BGR_VNG",Imgproc.COLOR_BayerBG2BGR_VNG));
        mnBayer.add( createIFMI("COLOR_BayerBG2GRAY",Imgproc.COLOR_BayerBG2GRAY));
        mnBayer.add( createIFMI("COLOR_BayerBG2RGB",Imgproc.COLOR_BayerBG2RGB));
        mnBayer.add( createIFMI("COLOR_BayerBG2RGB_EA",Imgproc.COLOR_BayerBG2RGB_EA));
        mnBayer.add( createIFMI("COLOR_BayerBG2RGB_VNG",Imgproc.COLOR_BayerBG2RGB_VNG));
        mnBayer.add( createIFMI("COLOR_BayerGB2BGR",Imgproc.COLOR_BayerGB2BGR));
        mnBayer.add( createIFMI("COLOR_BayerGB2BGR_EA",Imgproc.COLOR_BayerGB2BGR_EA));
        mnBayer.add( createIFMI("COLOR_BayerGB2BGR_VNG",Imgproc.COLOR_BayerGB2BGR_VNG));
        mnBayer.add( createIFMI("COLOR_BayerGB2GRAY",Imgproc.COLOR_BayerGB2GRAY));
        mnBayer.add( createIFMI("COLOR_BayerGB2RGB",Imgproc.COLOR_BayerGB2RGB));
        mnBayer.add( createIFMI("COLOR_BayerGB2RGB_EA",Imgproc.COLOR_BayerGB2RGB_EA));
        mnBayer.add( createIFMI("COLOR_BayerGB2RGB_VNG",Imgproc.COLOR_BayerGB2RGB_VNG));
        mnBayer.add( createIFMI("COLOR_BayerGR2BGR",Imgproc.COLOR_BayerGR2BGR));
        mnBayer.add( createIFMI("COLOR_BayerGR2BGR_EA",Imgproc.COLOR_BayerGR2BGR_EA));
        mnBayer.add( createIFMI("COLOR_BayerGR2BGR_VNG",Imgproc.COLOR_BayerGR2BGR_VNG));
        mnBayer.add( createIFMI("COLOR_BayerGR2GRAY",Imgproc.COLOR_BayerGR2GRAY));
        mnBayer.add( createIFMI("COLOR_BayerGR2RGB",Imgproc.COLOR_BayerGR2RGB));
        mnBayer.add( createIFMI("COLOR_BayerGR2RGB_EA",Imgproc.COLOR_BayerGR2RGB_EA));
        mnBayer.add( createIFMI("COLOR_BayerGR2RGB_VNG",Imgproc.COLOR_BayerGR2RGB_VNG));
        mnBayer.add( createIFMI("COLOR_BayerRG2BGR",Imgproc.COLOR_BayerRG2BGR));
        mnBayer.add( createIFMI("COLOR_BayerRG2BGR_EA",Imgproc.COLOR_BayerRG2BGR_EA));
        mnBayer.add( createIFMI("COLOR_BayerRG2BGR_VNG",Imgproc.COLOR_BayerRG2BGR_VNG));
        mnBayer.add( createIFMI("COLOR_BayerRG2GRAY",Imgproc.COLOR_BayerRG2GRAY));
        mnBayer.add( createIFMI("COLOR_BayerRG2RGB",Imgproc.COLOR_BayerRG2RGB));
        mnBayer.add( createIFMI("COLOR_BayerRG2RGB_EA",Imgproc.COLOR_BayerRG2RGB_EA));
        mnBayer.add( createIFMI("COLOR_BayerRG2RGB_VNG",Imgproc.COLOR_BayerRG2RGB_VNG));

        JMenu mnBGR = new JMenu("BGR TO");
        popupMenu.add(mnBGR);
        mnBGR.add( createIFMI("COLOR_BGR2BGR555",Imgproc.COLOR_BGR2BGR555));
        mnBGR.add( createIFMI("COLOR_BGR2BGR565",Imgproc.COLOR_BGR2BGR565));
        mnBGR.add( createIFMI("COLOR_BGR2BGRA",Imgproc.COLOR_BGR2BGRA));
        mnBGR.add( createIFMI("COLOR_BGR2GRAY",Imgproc.COLOR_BGR2GRAY));
        mnBGR.add( createIFMI("COLOR_BGR2HLS",Imgproc.COLOR_BGR2HLS));
        mnBGR.add( createIFMI("COLOR_BGR2HLS_FULL",Imgproc.COLOR_BGR2HLS_FULL));
        mnBGR.add( createIFMI("COLOR_BGR2HSV",Imgproc.COLOR_BGR2HSV));
        mnBGR.add( createIFMI("COLOR_BGR2HSV_FULL",Imgproc.COLOR_BGR2HSV_FULL));
        mnBGR.add( createIFMI("COLOR_BGR2Lab",Imgproc.COLOR_BGR2Lab));
        mnBGR.add( createIFMI("COLOR_BGR2Luv",Imgproc.COLOR_BGR2Luv));
        mnBGR.add( createIFMI("COLOR_BGR2RGB",Imgproc.COLOR_BGR2RGB));
        mnBGR.add( createIFMI("COLOR_BGR2RGBA",Imgproc.COLOR_BGR2RGBA));
        mnBGR.add( createIFMI("COLOR_BGR2XYZ",Imgproc.COLOR_BGR2XYZ));
        mnBGR.add( createIFMI("COLOR_BGR2YCrCb",Imgproc.COLOR_BGR2YCrCb));
        mnBGR.add( createIFMI("COLOR_BGR2YUV",Imgproc.COLOR_BGR2YUV));
        mnBGR.add( createIFMI("COLOR_BGR2YUV_I420",Imgproc.COLOR_BGR2YUV_I420));
        mnBGR.add( createIFMI("COLOR_BGR2YUV_IYUV",Imgproc.COLOR_BGR2YUV_IYUV));
        mnBGR.add( createIFMI("COLOR_BGR2YUV_YV12",Imgproc.COLOR_BGR2YUV_YV12));
        mnBGR.add( createIFMI("COLOR_BGR5552BGR",Imgproc.COLOR_BGR5552BGR));
        mnBGR.add( createIFMI("COLOR_BGR5552BGRA",Imgproc.COLOR_BGR5552BGRA));
        mnBGR.add( createIFMI("COLOR_BGR5552GRAY",Imgproc.COLOR_BGR5552GRAY));
        mnBGR.add( createIFMI("COLOR_BGR5552RGB",Imgproc.COLOR_BGR5552RGB));
        mnBGR.add( createIFMI("COLOR_BGR5552RGBA",Imgproc.COLOR_BGR5552RGBA));
        mnBGR.add( createIFMI("COLOR_BGR5652BGR",Imgproc.COLOR_BGR5652BGR));
        mnBGR.add( createIFMI("COLOR_BGR5652BGRA",Imgproc.COLOR_BGR5652BGRA));
        mnBGR.add( createIFMI("COLOR_BGR5652GRAY",Imgproc.COLOR_BGR5652GRAY));
        mnBGR.add( createIFMI("COLOR_BGR5652RGB",Imgproc.COLOR_BGR5652RGB));
        mnBGR.add( createIFMI("COLOR_BGR5652RGBA",Imgproc.COLOR_BGR5652RGBA));
        mnBGR.add( createIFMI("COLOR_BGRA2BGR",Imgproc.COLOR_BGRA2BGR));
        mnBGR.add( createIFMI("COLOR_BGRA2BGR555",Imgproc.COLOR_BGRA2BGR555));
        mnBGR.add( createIFMI("COLOR_BGRA2BGR565",Imgproc.COLOR_BGRA2BGR565));
        mnBGR.add( createIFMI("COLOR_BGRA2GRAY",Imgproc.COLOR_BGRA2GRAY));
        mnBGR.add( createIFMI("COLOR_BGRA2RGB",Imgproc.COLOR_BGRA2RGB));
        mnBGR.add( createIFMI("COLOR_BGRA2RGBA",Imgproc.COLOR_BGRA2RGBA));
        mnBGR.add( createIFMI("COLOR_BGRA2YUV_I420",Imgproc.COLOR_BGRA2YUV_I420));
        mnBGR.add( createIFMI("COLOR_BGRA2YUV_IYUV",Imgproc.COLOR_BGRA2YUV_IYUV));
        mnBGR.add( createIFMI("COLOR_BGRA2YUV_YV12",Imgproc.COLOR_BGRA2YUV_YV12));

        JMenu mnGrayTo = new JMenu("GRAY TO");
        popupMenu.add(mnGrayTo);
        mnGrayTo.add( createIFMI("COLOR_GRAY2BGR",Imgproc.COLOR_GRAY2BGR));
        mnGrayTo.add( createIFMI("COLOR_GRAY2BGR555",Imgproc.COLOR_GRAY2BGR555));
        mnGrayTo.add( createIFMI("COLOR_GRAY2BGR565",Imgproc.COLOR_GRAY2BGR565));
        mnGrayTo.add( createIFMI("COLOR_GRAY2BGRA",Imgproc.COLOR_GRAY2BGRA));
        mnGrayTo.add( createIFMI("COLOR_GRAY2RGB",Imgproc.COLOR_GRAY2RGB));
        mnGrayTo.add( createIFMI("COLOR_GRAY2RGBA",Imgproc.COLOR_GRAY2RGBA));

        JMenu mnHlsTo = new JMenu("HLS TO");
        popupMenu.add(mnHlsTo);
        mnHlsTo.add( createIFMI("COLOR_HLS2BGR",Imgproc.COLOR_HLS2BGR));
        mnHlsTo.add( createIFMI("COLOR_HLS2BGR_FULL",Imgproc.COLOR_HLS2BGR_FULL));
        mnHlsTo.add( createIFMI("COLOR_HLS2RGB",Imgproc.COLOR_HLS2RGB));
        mnHlsTo.add( createIFMI("COLOR_HLS2RGB_FULL",Imgproc.COLOR_HLS2RGB_FULL));

        JMenu mnHsvTo = new JMenu("HSV TO");
        popupMenu.add(mnHsvTo);
        mnHsvTo.add( createIFMI("COLOR_HSV2BGR",Imgproc.COLOR_HSV2BGR));
        mnHsvTo.add( createIFMI("COLOR_HSV2BGR_FULL",Imgproc.COLOR_HSV2BGR_FULL));
        mnHsvTo.add( createIFMI("COLOR_HSV2RGB",Imgproc.COLOR_HSV2RGB));
        mnHsvTo.add( createIFMI("COLOR_HSV2RGB_FULL",Imgproc.COLOR_HSV2RGB_FULL));

        JMenu mnLabTo = new JMenu("LAB TO");
        popupMenu.add(mnLabTo);
        mnLabTo.add( createIFMI("COLOR_Lab2LBGR",Imgproc.COLOR_Lab2LBGR));
        mnLabTo.add( createIFMI("COLOR_Lab2LRGB",Imgproc.COLOR_Lab2LRGB));
        mnLabTo.add( createIFMI("COLOR_Lab2RGB",Imgproc.COLOR_Lab2RGB));

        JMenu mnLbgrTo = new JMenu("LBGR/LRGB TO");
        popupMenu.add(mnLbgrTo);
        mnLbgrTo.add( createIFMI("COLOR_LBGR2Lab",Imgproc.COLOR_LBGR2Lab));
        mnLbgrTo.add( createIFMI("COLOR_LBGR2Luv",Imgproc.COLOR_LBGR2Luv));
        mnLbgrTo.add( createIFMI("COLOR_LRGB2Lab",Imgproc.COLOR_LRGB2Lab));
        mnLbgrTo.add( createIFMI("COLOR_LRGB2Luv",Imgproc.COLOR_LRGB2Luv));

        JMenu mnLuvTo = new JMenu("LUV TO");
        popupMenu.add(mnLuvTo);
        mnLuvTo.add( createIFMI("COLOR_Luv2BGR",Imgproc.COLOR_Luv2BGR));
        mnLuvTo.add( createIFMI("COLOR_Luv2LBGR",Imgproc.COLOR_Luv2LBGR));
        mnLuvTo.add( createIFMI("COLOR_Luv2LRGB",Imgproc.COLOR_Luv2LRGB));
        mnLuvTo.add( createIFMI("COLOR_Luv2RGB",Imgproc.COLOR_Luv2RGB));

        JMenu mnRgbTo = new JMenu("RGB TO");
        popupMenu.add(mnRgbTo);
        mnRgbTo.add( createIFMI("COLOR_mRGBA2RGBA",Imgproc.COLOR_mRGBA2RGBA));
        mnRgbTo.add( createIFMI("COLOR_RGB2BGR",Imgproc.COLOR_RGB2BGR));
        mnRgbTo.add( createIFMI("COLOR_RGB2BGR555",Imgproc.COLOR_RGB2BGR555));
        mnRgbTo.add( createIFMI("COLOR_RGB2BGR565",Imgproc.COLOR_RGB2BGR565));
        mnRgbTo.add( createIFMI("COLOR_RGB2BGRA",Imgproc.COLOR_RGB2BGRA));
        mnRgbTo.add( createIFMI("COLOR_RGB2GRAY",Imgproc.COLOR_RGB2GRAY));
        mnRgbTo.add( createIFMI("COLOR_RGB2HLS",Imgproc.COLOR_RGB2HLS));
        mnRgbTo.add( createIFMI("COLOR_RGB2HLS_FULL",Imgproc.COLOR_RGB2HLS_FULL));
        mnRgbTo.add( createIFMI("COLOR_RGB2HSV",Imgproc.COLOR_RGB2HSV));
        mnRgbTo.add( createIFMI("COLOR_RGB2HSV_FULL",Imgproc.COLOR_RGB2HSV_FULL));
        mnRgbTo.add( createIFMI("COLOR_RGB2Lab",Imgproc.COLOR_RGB2Lab));
        mnRgbTo.add( createIFMI("COLOR_RGB2Luv",Imgproc.COLOR_RGB2Luv));
        mnRgbTo.add( createIFMI("COLOR_RGB2RGBA",Imgproc.COLOR_RGB2RGBA));
        mnRgbTo.add( createIFMI("COLOR_RGB2XYZ",Imgproc.COLOR_RGB2XYZ));
        mnRgbTo.add( createIFMI("COLOR_RGB2YCrCb",Imgproc.COLOR_RGB2YCrCb));
        mnRgbTo.add( createIFMI("COLOR_RGB2YUV",Imgproc.COLOR_RGB2YUV));
        mnRgbTo.add( createIFMI("COLOR_RGB2YUV_I420",Imgproc.COLOR_RGB2YUV_I420));
        mnRgbTo.add( createIFMI("COLOR_RGB2YUV_IYUV",Imgproc.COLOR_RGB2YUV_IYUV));
        mnRgbTo.add( createIFMI("COLOR_RGB2YUV_YV12",Imgproc.COLOR_RGB2YUV_YV12));
        mnRgbTo.add( createIFMI("COLOR_RGBA2BGR",Imgproc.COLOR_RGBA2BGR));
        mnRgbTo.add( createIFMI("COLOR_RGBA2BGR555",Imgproc.COLOR_RGBA2BGR555));
        mnRgbTo.add( createIFMI("COLOR_RGBA2BGR565",Imgproc.COLOR_RGBA2BGR565));
        mnRgbTo.add( createIFMI("COLOR_RGBA2BGRA",Imgproc.COLOR_RGBA2BGRA));
        mnRgbTo.add( createIFMI("COLOR_RGBA2GRAY",Imgproc.COLOR_RGBA2GRAY));
        mnRgbTo.add( createIFMI("COLOR_RGBA2mRGBA",Imgproc.COLOR_RGBA2mRGBA));
        mnRgbTo.add( createIFMI("COLOR_RGBA2RGB",Imgproc.COLOR_RGBA2RGB));
        mnRgbTo.add( createIFMI("COLOR_RGBA2YUV_I420",Imgproc.COLOR_RGBA2YUV_I420));
        mnRgbTo.add( createIFMI("COLOR_RGBA2YUV_IYUV",Imgproc.COLOR_RGBA2YUV_IYUV));
        mnRgbTo.add( createIFMI("COLOR_RGBA2YUV_YV12",Imgproc.COLOR_RGBA2YUV_YV12));

        JMenu mnXyzTo = new JMenu("XYZ TO");
        popupMenu.add(mnXyzTo);
        mnXyzTo.add( createIFMI("COLOR_XYZ2BGR",Imgproc.COLOR_XYZ2BGR));
        mnXyzTo.add( createIFMI("COLOR_XYZ2RGB",Imgproc.COLOR_XYZ2RGB));

        JMenu mnYcrcbTo = new JMenu("YCrCb TO");
        popupMenu.add(mnYcrcbTo);
        mnYcrcbTo.add( createIFMI("COLOR_YCrCb2BGR",Imgproc.COLOR_YCrCb2BGR));
        mnYcrcbTo.add( createIFMI("COLOR_YCrCb2RGB",Imgproc.COLOR_YCrCb2RGB));

        JMenu mnYuvTo = new JMenu("YUV TO");
        popupMenu.add(mnYuvTo);
        JMenu mnYuvToBgr = new JMenu("YUV TO BGR");
        mnYuvTo.add(mnYuvToBgr);
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR",Imgproc.COLOR_YUV2BGR));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_I420",Imgproc.COLOR_YUV2BGR_I420));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_IYUV",Imgproc.COLOR_YUV2BGR_IYUV));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_NV12",Imgproc.COLOR_YUV2BGR_NV12));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_NV21",Imgproc.COLOR_YUV2BGR_NV21));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_UYNV",Imgproc.COLOR_YUV2BGR_UYNV));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_UYVY",Imgproc.COLOR_YUV2BGR_UYVY));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_Y422",Imgproc.COLOR_YUV2BGR_Y422));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_YUNV",Imgproc.COLOR_YUV2BGR_YUNV));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_YUY2",Imgproc.COLOR_YUV2BGR_YUY2));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_YUYV",Imgproc.COLOR_YUV2BGR_YUYV));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_YV12",Imgproc.COLOR_YUV2BGR_YV12));
        mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_YVYU",Imgproc.COLOR_YUV2BGR_YVYU));
        JMenu mnYuvToBgra = new JMenu("YUV TO BGRA");
        mnYuvTo.add(mnYuvToBgra);
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_I420",Imgproc.COLOR_YUV2BGRA_I420));
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_IYUV",Imgproc.COLOR_YUV2BGRA_IYUV));
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_NV12",Imgproc.COLOR_YUV2BGRA_NV12));
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_NV21",Imgproc.COLOR_YUV2BGRA_NV21));
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_UYNV",Imgproc.COLOR_YUV2BGRA_UYNV));
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_UYVY",Imgproc.COLOR_YUV2BGRA_UYVY));
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_Y422",Imgproc.COLOR_YUV2BGRA_Y422));
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_YUNV",Imgproc.COLOR_YUV2BGRA_YUNV));
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_YUY2",Imgproc.COLOR_YUV2BGRA_YUY2));
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_YUYV",Imgproc.COLOR_YUV2BGRA_YUYV));
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_YV12",Imgproc.COLOR_YUV2BGRA_YV12));
        mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_YVYU",Imgproc.COLOR_YUV2BGRA_YVYU));
        JMenu mnYuvToGray = new JMenu("YUV TO GRAY");
        mnYuvTo.add(mnYuvToGray);
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_420",Imgproc.COLOR_YUV2GRAY_420));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_I420",Imgproc.COLOR_YUV2GRAY_I420));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_IYUV",Imgproc.COLOR_YUV2GRAY_IYUV));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_NV12",Imgproc.COLOR_YUV2GRAY_NV12));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_NV21",Imgproc.COLOR_YUV2GRAY_NV21));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_UYNV",Imgproc.COLOR_YUV2GRAY_UYNV));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_UYVY",Imgproc.COLOR_YUV2GRAY_UYVY));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_Y422",Imgproc.COLOR_YUV2GRAY_Y422));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_YUNV",Imgproc.COLOR_YUV2GRAY_YUNV));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_YUY2",Imgproc.COLOR_YUV2GRAY_YUY2));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_YUYV",Imgproc.COLOR_YUV2GRAY_YUYV));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_YV12",Imgproc.COLOR_YUV2GRAY_YV12));
        mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_YVYU",Imgproc.COLOR_YUV2GRAY_YVYU));
        JMenu mnYuvToRGB = new JMenu("YUV TO RGB");
        mnYuvTo.add(mnYuvToRGB);
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB",Imgproc.COLOR_YUV2RGB));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_I420",Imgproc.COLOR_YUV2RGB_I420));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_IYUV",Imgproc.COLOR_YUV2RGB_IYUV));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_NV12",Imgproc.COLOR_YUV2RGB_NV12));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_NV21",Imgproc.COLOR_YUV2RGB_NV21));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_UYNV",Imgproc.COLOR_YUV2RGB_UYNV));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_UYVY",Imgproc.COLOR_YUV2RGB_UYVY));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_Y422",Imgproc.COLOR_YUV2RGB_Y422));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_YUNV",Imgproc.COLOR_YUV2RGB_YUNV));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_YUY2",Imgproc.COLOR_YUV2RGB_YUY2));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_YUYV",Imgproc.COLOR_YUV2RGB_YUYV));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_YV12",Imgproc.COLOR_YUV2RGB_YV12));
        mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_YVYU",Imgproc.COLOR_YUV2RGB_YVYU));
        JMenu mnYuvToRgba = new JMenu("YUV TO RGBA");
        mnYuvTo.add(mnYuvToRgba);
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_I420",Imgproc.COLOR_YUV2RGBA_I420));
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_IYUV",Imgproc.COLOR_YUV2RGBA_IYUV));
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_NV12",Imgproc.COLOR_YUV2RGBA_NV12));
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_NV21",Imgproc.COLOR_YUV2RGBA_NV21));
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_UYNV",Imgproc.COLOR_YUV2RGBA_UYNV));
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_UYVY",Imgproc.COLOR_YUV2RGBA_UYVY));
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_Y422",Imgproc.COLOR_YUV2RGBA_Y422));
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_YUNV",Imgproc.COLOR_YUV2RGBA_YUNV));
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_YUY2",Imgproc.COLOR_YUV2RGBA_YUY2));
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_YUYV",Imgproc.COLOR_YUV2RGBA_YUYV));
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_YV12",Imgproc.COLOR_YUV2RGBA_YV12));
        mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_YVYU",Imgproc.COLOR_YUV2RGBA_YVYU));
        JMenu mnYuv420p = new JMenu("YUV420 TO");
        mnYuvTo.add(mnYuv420p);
        mnYuv420p.add( createIFMI("COLOR_YUV420p2BGR",Imgproc.COLOR_YUV420p2BGR));
        mnYuv420p.add( createIFMI("COLOR_YUV420p2BGRA",Imgproc.COLOR_YUV420p2BGRA));
        mnYuv420p.add( createIFMI("COLOR_YUV420p2GRAY",Imgproc.COLOR_YUV420p2GRAY));
        mnYuv420p.add( createIFMI("COLOR_YUV420p2RGB",Imgproc.COLOR_YUV420p2RGB));
        mnYuv420p.add( createIFMI("COLOR_YUV420p2RGBA",Imgproc.COLOR_YUV420p2RGBA));
        mnYuv420p.add( createIFMI("COLOR_YUV420sp2BGR",Imgproc.COLOR_YUV420sp2BGR));
        mnYuv420p.add( createIFMI("COLOR_YUV420sp2BGRA",Imgproc.COLOR_YUV420sp2BGRA));
        mnYuv420p.add( createIFMI("COLOR_YUV420sp2GRAY",Imgproc.COLOR_YUV420sp2GRAY));
        mnYuv420p.add( createIFMI("COLOR_YUV420sp2RGB",Imgproc.COLOR_YUV420sp2RGB));
        mnYuv420p.add( createIFMI("COLOR_YUV420sp2RGBA",Imgproc.COLOR_YUV420sp2RGBA));

        return popupMenu;
    }

    IntFlagMenuItem createIFMI(String name, int value){

        IntFlagMenuItem ifmi = new IntFlagMenuItem(name,value, selectedColorConversion);
        return ifmi;
    }

}
