package openCVHarness;
import java.awt.EventQueue;

import org.opencv.core.Core;

public class OpenCVHarness {

    OpenCVHarnessWindow openCVHarnessWindow;

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); //Load OpenCV or whatever.

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    @SuppressWarnings("unused")
                    OpenCVHarness harness = new OpenCVHarness();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public OpenCVHarness() {
        openCVHarnessWindow = new OpenCVHarnessWindow( this);
    }

}
