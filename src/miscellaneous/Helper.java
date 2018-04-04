package miscellaneous;

import java.awt.Dimension;
import java.awt.Toolkit;

import openCVHarness.OpenCVHarnessWindow;

public class Helper {

    static private OpenCVHarnessWindow webcamHarnessWindow;

    public Helper(OpenCVHarnessWindow w) {
        Helper.setWebcamHarnessWindow(w);
    }

    static public OpenCVHarnessWindow getWebcamHarnessWindow() {
        return webcamHarnessWindow;
    }

    static public void setWebcamHarnessWindow(OpenCVHarnessWindow w) {
        Helper.webcamHarnessWindow = w;
    }
    
    /** Checks if an (x, y) rectangle fits into the screen if its 
     * position is specified with respect to the top left of the rectangle.
     * @param originalPoint The location of the top left point of the rectangle.
     * @param rectDimension The size of the rectangle.
     * @return {@link java.awt.Point} The location of the rectangle. 
     * If the rectangle would exceed the bounds of the screen, then it adjusts the position to abut the screen edge.
     */
    static public java.awt.Point fitRectangleInScreen(java.awt.Point originalPoint, Dimension rectDimension) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x, y;

        if(originalPoint.x + rectDimension.getWidth() > screenSize.getWidth()) {
            x = (int) (screenSize.getWidth()-rectDimension.getWidth());
        } else if(originalPoint.x < 0) {
            x = 0;
        } else {
            x = originalPoint.x;
        }

        if(originalPoint.y + rectDimension.getHeight() > screenSize.getHeight()) {
            y = (int) (screenSize.getHeight()-rectDimension.getHeight());
        } else if ( originalPoint.y < 0 ) {
            y = 0;
        } else {
            y = originalPoint.y;
        }
        
        return new java.awt.Point(x,y);
    }
}
