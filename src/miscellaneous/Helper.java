package miscellaneous;

import openCVHarness.OpenCVHarnessWindow;

public class Helper {

	static private OpenCVHarnessWindow webcamHarnessWindow;
	
	public Helper( OpenCVHarnessWindow w )
	{
		Helper.setWebcamHarnessWindow(w);
	}
	
	static public OpenCVHarnessWindow getWebcamHarnessWindow()
	{
		return webcamHarnessWindow;
	}
	
	static public void setWebcamHarnessWindow( OpenCVHarnessWindow w )
	{
		Helper.webcamHarnessWindow = w;
	}
}
