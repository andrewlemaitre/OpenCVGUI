package openCVOperations;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.opencv.imgproc.Imgproc;

import dialogs.OperationDialogBox;
import miscellaneous.IntFlagItem;
import miscellaneous.IntFlagMenuItem;

public class CvtColorOperation extends OpenCVOperation {

	IntFlagItem selectedColorConversion = new IntFlagItem();
	private final int unselectedFlag = -2;
	
	public CvtColorOperation(){
		this.name.setValue("Convert Color Name");
		this.outputName.setValue("Convert Color Name");
		this.selectedColorConversion.setValue( unselectedFlag );
	}
	
	@Override
	public JDialog openDialogBox() {
		
		OperationDialogBox odb = new OperationDialogBox();
		odb.addTextBox("Op Name", "Convert Color Operation", this.name);

		odb.addSourceMatSelector("Input Operation", this );

		JPopupMenu popupMenu = createPopupMenu();
		odb.addPopUpMenu("Convert Color Flag", selectedColorConversion, popupMenu);
		
		odb.addTextBox("Output Name", "Convert Color Output", outputName);
		return odb.getDialog();
	}
	
	@Override
	public void performOperation() {
		Imgproc.cvtColor(this.getInputOperation().getOutputMat(), outputMat, selectedColorConversion.getValue());
	}

	@Override
	public OpenCVOperation newOperationCopy() {
		CvtColorOperation cco = new CvtColorOperation();
		return cco;
	}

	@Override
	public boolean isValid() {
			if( inputOperation != null && selectedColorConversion.getValue() != unselectedFlag )
				return true;
		return false;
	}

	//TODO: Only show menu options that are valid conversion types for the selected input mat.
	private JPopupMenu createPopupMenu()
	{
		JPopupMenu popupMenu = new JPopupMenu();
		
		JMenu mnBayer = new JMenu("BAYER TO");
		popupMenu.add(mnBayer);
		mnBayer.add( createIFMI("COLOR_BayerBG2BGR",46));
		mnBayer.add( createIFMI("COLOR_BayerBG2BGR_EA",135));
		mnBayer.add( createIFMI("COLOR_BayerBG2BGR_VNG",62));
		mnBayer.add( createIFMI("COLOR_BayerBG2GRAY",86));
		mnBayer.add( createIFMI("COLOR_BayerBG2RGB",48));
		mnBayer.add( createIFMI("COLOR_BayerBG2RGB_EA",137));
		mnBayer.add( createIFMI("COLOR_BayerBG2RGB_VNG",64));
		mnBayer.add( createIFMI("COLOR_BayerGB2BGR",47));
		mnBayer.add( createIFMI("COLOR_BayerGB2BGR_EA",136));
		mnBayer.add( createIFMI("COLOR_BayerGB2BGR_VNG",63));
		mnBayer.add( createIFMI("COLOR_BayerGB2GRAY",87));
		mnBayer.add( createIFMI("COLOR_BayerGB2RGB",49));
		mnBayer.add( createIFMI("COLOR_BayerGB2RGB_EA",138));
		mnBayer.add( createIFMI("COLOR_BayerGB2RGB_VNG",65));
		mnBayer.add( createIFMI("COLOR_BayerGR2BGR",49));
		mnBayer.add( createIFMI("COLOR_BayerGR2BGR_EA",138));
		mnBayer.add( createIFMI("COLOR_BayerGR2BGR_VNG",65));
		mnBayer.add( createIFMI("COLOR_BayerGR2GRAY",89));
		mnBayer.add( createIFMI("COLOR_BayerGR2RGB",47));
		mnBayer.add( createIFMI("COLOR_BayerGR2RGB_EA",136));
		mnBayer.add( createIFMI("COLOR_BayerGR2RGB_VNG",63));
		mnBayer.add( createIFMI("COLOR_BayerRG2BGR",48));
		mnBayer.add( createIFMI("COLOR_BayerRG2BGR_EA",137));
		mnBayer.add( createIFMI("COLOR_BayerRG2BGR_VNG",64));
		mnBayer.add( createIFMI("COLOR_BayerRG2GRAY",88));
		mnBayer.add( createIFMI("COLOR_BayerRG2RGB",46));
		mnBayer.add( createIFMI("COLOR_BayerRG2RGB_EA",135));
		mnBayer.add( createIFMI("COLOR_BayerRG2RGB_VNG",62));
	
		
		JMenu mnBGR = new JMenu("BGR TO");
		popupMenu.add(mnBGR);
		mnBGR.add( createIFMI("COLOR_BGR2BGR555",22));
		mnBGR.add( createIFMI("COLOR_BGR2BGR565",12));
		mnBGR.add( createIFMI("COLOR_BGR2BGRA",0));
		mnBGR.add( createIFMI("COLOR_BGR2GRAY",6));
		mnBGR.add( createIFMI("COLOR_BGR2HLS",52));
		mnBGR.add( createIFMI("COLOR_BGR2HLS_FULL",68));
		mnBGR.add( createIFMI("COLOR_BGR2HSV",40));
		mnBGR.add( createIFMI("COLOR_BGR2HSV_FULL",66));
		mnBGR.add( createIFMI("COLOR_BGR2Lab",44));
		mnBGR.add( createIFMI("COLOR_BGR2Luv",50));
		mnBGR.add( createIFMI("COLOR_BGR2RGB",4));
		mnBGR.add( createIFMI("COLOR_BGR2RGBA",2));
		mnBGR.add( createIFMI("COLOR_BGR2XYZ",32));
		mnBGR.add( createIFMI("COLOR_BGR2YCrCb",36));
		mnBGR.add( createIFMI("COLOR_BGR2YUV",82));
		mnBGR.add( createIFMI("COLOR_BGR2YUV_I420",128));
		mnBGR.add( createIFMI("COLOR_BGR2YUV_IYUV",128));
		mnBGR.add( createIFMI("COLOR_BGR2YUV_YV12",132));
		mnBGR.add( createIFMI("COLOR_BGR5552BGR",24));
		mnBGR.add( createIFMI("COLOR_BGR5552BGRA",28));
		mnBGR.add( createIFMI("COLOR_BGR5552GRAY",31));
		mnBGR.add( createIFMI("COLOR_BGR5552RGB",25));
		mnBGR.add( createIFMI("COLOR_BGR5552RGBA",29));
		mnBGR.add( createIFMI("COLOR_BGR5652BGR",14));
		mnBGR.add( createIFMI("COLOR_BGR5652BGRA",18));
		mnBGR.add( createIFMI("COLOR_BGR5652GRAY",21));
		mnBGR.add( createIFMI("COLOR_BGR5652RGB",15));
		mnBGR.add( createIFMI("COLOR_BGR5652RGBA",19));
		mnBGR.add( createIFMI("COLOR_BGRA2BGR",1));
		mnBGR.add( createIFMI("COLOR_BGRA2BGR555",26));
		mnBGR.add( createIFMI("COLOR_BGRA2BGR565",16));
		mnBGR.add( createIFMI("COLOR_BGRA2GRAY",10));
		mnBGR.add( createIFMI("COLOR_BGRA2RGB",3));
		mnBGR.add( createIFMI("COLOR_BGRA2RGBA",5));
		mnBGR.add( createIFMI("COLOR_BGRA2YUV_I420",130));
		mnBGR.add( createIFMI("COLOR_BGRA2YUV_IYUV",130));
		mnBGR.add( createIFMI("COLOR_BGRA2YUV_YV12",134));
	
		
		JMenu mnGrayTo = new JMenu("GRAY TO");
		popupMenu.add(mnGrayTo);
		mnGrayTo.add( createIFMI("COLOR_GRAY2BGR",8));
		mnGrayTo.add( createIFMI("COLOR_GRAY2BGR555",30));
		mnGrayTo.add( createIFMI("COLOR_GRAY2BGR565",20));
		mnGrayTo.add( createIFMI("COLOR_GRAY2BGRA",9));
		mnGrayTo.add( createIFMI("COLOR_GRAY2RGB",8));
		mnGrayTo.add( createIFMI("COLOR_GRAY2RGBA",9));
	
		
		JMenu mnHlsTo = new JMenu("HLS TO");
		popupMenu.add(mnHlsTo);
		mnHlsTo.add( createIFMI("COLOR_HLS2BGR",60));
		mnHlsTo.add( createIFMI("COLOR_HLS2BGR_FULL",72));
		mnHlsTo.add( createIFMI("COLOR_HLS2RGB",61));
		mnHlsTo.add( createIFMI("COLOR_HLS2RGB_FULL",73));
	
		
		JMenu mnHsvTo = new JMenu("HSV TO");
		popupMenu.add(mnHsvTo);
		mnHsvTo.add( createIFMI("COLOR_HSV2BGR",54));
		mnHsvTo.add( createIFMI("COLOR_HSV2BGR_FULL",70));
		mnHsvTo.add( createIFMI("COLOR_HSV2RGB",55));
		mnHsvTo.add( createIFMI("COLOR_HSV2RGB_FULL",71));
		
		
		JMenu mnLabTo = new JMenu("LAB TO");
		popupMenu.add(mnLabTo);
		mnLabTo.add( createIFMI("COLOR_Lab2LBGR",78));
		mnLabTo.add( createIFMI("COLOR_Lab2LRGB",79));
		mnLabTo.add( createIFMI("COLOR_Lab2RGB",57));
	
		
		JMenu mnLbgrTo = new JMenu("LBGR/LRGB TO");
		popupMenu.add(mnLbgrTo);
		mnLbgrTo.add( createIFMI("COLOR_LBGR2Lab",74));
		mnLbgrTo.add( createIFMI("COLOR_LBGR2Luv",76));
		mnLbgrTo.add( createIFMI("COLOR_LRGB2Lab",75));
		mnLbgrTo.add( createIFMI("COLOR_LRGB2Luv",77));
	
		
		JMenu mnLuvTo = new JMenu("LUV TO");
		popupMenu.add(mnLuvTo);
		mnLuvTo.add( createIFMI("COLOR_Luv2BGR",58));
		mnLuvTo.add( createIFMI("COLOR_Luv2LBGR",80));
		mnLuvTo.add( createIFMI("COLOR_Luv2LRGB",81));
		mnLuvTo.add( createIFMI("COLOR_Luv2RGB",59));
	
		
		JMenu mnRgbTo = new JMenu("RGB TO");
		popupMenu.add(mnRgbTo);
		mnRgbTo.add( createIFMI("COLOR_mRGBA2RGBA",126));
		mnRgbTo.add( createIFMI("COLOR_RGB2BGR",4));
		mnRgbTo.add( createIFMI("COLOR_RGB2BGR555",23));
		mnRgbTo.add( createIFMI("COLOR_RGB2BGR565",13));
		mnRgbTo.add( createIFMI("COLOR_RGB2BGRA",2));
		mnRgbTo.add( createIFMI("COLOR_RGB2GRAY",7));
		mnRgbTo.add( createIFMI("COLOR_RGB2HLS",53));
		mnRgbTo.add( createIFMI("COLOR_RGB2HLS_FULL",69));
		mnRgbTo.add( createIFMI("COLOR_RGB2HSV",41));
		mnRgbTo.add( createIFMI("COLOR_RGB2HSV_FULL",67));
		mnRgbTo.add( createIFMI("COLOR_RGB2Lab",45));
		mnRgbTo.add( createIFMI("COLOR_RGB2Luv",51));
		mnRgbTo.add( createIFMI("COLOR_RGB2RGBA",0));
		mnRgbTo.add( createIFMI("COLOR_RGB2XYZ",33));
		mnRgbTo.add( createIFMI("COLOR_RGB2YCrCb",37));
		mnRgbTo.add( createIFMI("COLOR_RGB2YUV",83));
		mnRgbTo.add( createIFMI("COLOR_RGB2YUV_I420",127));
		mnRgbTo.add( createIFMI("COLOR_RGB2YUV_IYUV",127));
		mnRgbTo.add( createIFMI("COLOR_RGB2YUV_YV12",131));
		mnRgbTo.add( createIFMI("COLOR_RGBA2BGR",3));
		mnRgbTo.add( createIFMI("COLOR_RGBA2BGR555",27));
		mnRgbTo.add( createIFMI("COLOR_RGBA2BGR565",17));
		mnRgbTo.add( createIFMI("COLOR_RGBA2BGRA",5));
		mnRgbTo.add( createIFMI("COLOR_RGBA2GRAY",11));
		mnRgbTo.add( createIFMI("COLOR_RGBA2mRGBA",125));
		mnRgbTo.add( createIFMI("COLOR_RGBA2RGB",1));
		mnRgbTo.add( createIFMI("COLOR_RGBA2YUV_I420",129));
		mnRgbTo.add( createIFMI("COLOR_RGBA2YUV_IYUV",129));
		mnRgbTo.add( createIFMI("COLOR_RGBA2YUV_YV12",133));
	
		
		JMenu mnXyzTo = new JMenu("XYZ TO");
		popupMenu.add(mnXyzTo);
		mnXyzTo.add( createIFMI("COLOR_XYZ2BGR",34));
		mnXyzTo.add( createIFMI("COLOR_XYZ2RGB",35));
	
		
		JMenu mnYcrcbTo = new JMenu("YCrCb TO");
		popupMenu.add(mnYcrcbTo);
		mnYcrcbTo.add( createIFMI("COLOR_YCrCb2BGR",38));
		mnYcrcbTo.add( createIFMI("COLOR_YCrCb2RGB",39));
	
	
		JMenu mnYuvTo = new JMenu("YUV TO");
		popupMenu.add(mnYuvTo);
		JMenu mnYuvToBgr = new JMenu("YUV TO BGR");
		mnYuvTo.add(mnYuvToBgr);
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR",84));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_I420",101));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_IYUV",101));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_NV12",91));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_NV21",93));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_UYNV",108));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_UYVY",108));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_Y422",108));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_YUNV",116));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_YUY2",116));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_YUYV",116));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_YV12",99));
		mnYuvToBgr.add( createIFMI("COLOR_YUV2BGR_YVYU",118));
		JMenu mnYuvToBgra = new JMenu("YUV TO BGRA");
		mnYuvTo.add(mnYuvToBgra);
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_I420",105));
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_IYUV",105));
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_NV12",95));
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_NV21",97));
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_UYNV",112));
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_UYVY",112));
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_Y422",112));
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_YUNV",120));
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_YUY2",120));
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_YUYV",120));
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_YV12",103));
		mnYuvToBgra.add( createIFMI("COLOR_YUV2BGRA_YVYU",122));
		JMenu mnYuvToGray = new JMenu("YUV TO GRAY");
		mnYuvTo.add(mnYuvToGray);
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_420",106));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_I420",106));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_IYUV",106));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_NV12",106));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_NV21",106));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_UYNV",123));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_UYVY",123));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_Y422",123));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_YUNV",124));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_YUY2",124));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_YUYV",124));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_YV12",106));
		mnYuvToGray.add( createIFMI("COLOR_YUV2GRAY_YVYU",124));
		JMenu mnYuvToRGB = new JMenu("YUV TO RGB");
		mnYuvTo.add(mnYuvToRGB);
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB",85));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_I420",100));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_IYUV",100));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_NV12",90));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_NV21",92));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_UYNV",107));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_UYVY",107));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_Y422",107));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_YUNV",115));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_YUY2",115));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_YUYV",115));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_YV12",98));
		mnYuvToRGB.add( createIFMI("COLOR_YUV2RGB_YVYU",117));
		JMenu mnYuvToRgba = new JMenu("YUV TO RGBA");
		mnYuvTo.add(mnYuvToRgba);
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_I420",104));
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_IYUV",104));
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_NV12",94));
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_NV21",96));
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_UYNV",111));
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_UYVY",111));
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_Y422",111));
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_YUNV",119));
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_YUY2",119));
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_YUYV",119));
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_YV12",102));
		mnYuvToRgba.add( createIFMI("COLOR_YUV2RGBA_YVYU",121));
		JMenu mnYuv420p = new JMenu("YUV420 TO");
		mnYuvTo.add(mnYuv420p);
		mnYuv420p.add( createIFMI("COLOR_YUV420p2BGR",99));
		mnYuv420p.add( createIFMI("COLOR_YUV420p2BGRA",103));
		mnYuv420p.add( createIFMI("COLOR_YUV420p2GRAY",106));
		mnYuv420p.add( createIFMI("COLOR_YUV420p2RGB",98));
		mnYuv420p.add( createIFMI("COLOR_YUV420p2RGBA",102));
		mnYuv420p.add( createIFMI("COLOR_YUV420sp2BGR",93));
		mnYuv420p.add( createIFMI("COLOR_YUV420sp2BGRA",97));
		mnYuv420p.add( createIFMI("COLOR_YUV420sp2GRAY",106));
		mnYuv420p.add( createIFMI("COLOR_YUV420sp2RGB",92));
		mnYuv420p.add( createIFMI("COLOR_YUV420sp2RGBA",96));
		
		return popupMenu;
	}

	IntFlagMenuItem createIFMI( String name, int value ){
	
		IntFlagMenuItem ifmi = new IntFlagMenuItem(name,value, selectedColorConversion);
		return ifmi;
	}

}
