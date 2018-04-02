package openCVHarness;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import miscellaneous.Helper;
import operations.OpenCVOperation;

public class OpenCVSerializer {

    public OpenCVSerializer() {

    }

    static public void serializeOperations(ArrayList<OpenCVOperation> operationList, String saveFilePath) {
//        try {
//            FileOutputStream fileOut = new FileOutputStream(saveFilePath);
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(operationList);
//            out.close();
//            fileOut.close();
//            System.out.printf("Operations data serialized in " + saveFilePath);
//         } catch (IOException i) {
//            i.printStackTrace();
//         }
    }

    static public void deserializeOperations(String loadFilePath) {
//        ArrayList<OpenCVOperation> operationsList = null;
//        try {
//           FileInputStream fileIn = new FileInputStream(loadFilePath);
//           ObjectInputStream in = new ObjectInputStream(fileIn);
//           operationsList = (ArrayList<OpenCVOperation>)in.readObject();
//           in.close();
//           fileIn.close();
//        } catch (IOException i) {
//           i.printStackTrace();
//           return;
//        } catch (ClassNotFoundException c) {
//           c.printStackTrace();
//           return;
//        }
//
//        System.out.println("Deserialized operations list:");
//        System.out.println(operationsList);
//        System.out.println("Length:" + operationsList.size());
//
//        for(OpenCVOperation op : operationsList) {
//            Helper.getWebcamHarnessWindow().getListManager().addOperation( op);
//            op.createNewOutputMat();
//        }
    }
}
