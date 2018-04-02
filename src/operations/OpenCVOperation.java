package operations;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JDialog;
import org.opencv.core.Mat;

import passableTypes.PassableString;
import passableTypes.IOData;

/**
 *  This is the abstract class from which all other OpenCV operations are derived.
 */
public abstract class OpenCVOperation implements Serializable {

    /** Serialization ID */
    private static final long serialVersionUID = 3811274352537025874L;
    /** The name of this operation. */
    private PassableString name = new PassableString();
    /** The output name of this operation. */
    private PassableString outputName = new   PassableString();
    /** The OpenCVOperation that this operation will get it's input data from. */
    private OpenCVOperation inputOperation;
    /** The Mat that this openCVOperation will put output data in. */
    private long ID;
    transient private Mat outputMat = new Mat();
    transient private static Random r = new Random();
    private ArrayList<IOData<?>> inputs = new ArrayList<IOData<?>>();
    private ArrayList<IOData<?>> outputs = new ArrayList<IOData<?>>();
    private ArrayList<OpenCVOperation> parentOperations = new ArrayList<>();
    private ArrayList<OpenCVOperation> childOperations = new ArrayList<>();

    public OpenCVOperation() {
        ID = r.nextLong();
    }

    final public long getID() {
        return ID;
    }

    public void createNewOutputMat() {
        outputMat = new Mat();
    }

    protected final void addDataInput(IOData<?> ioData) {
        inputs.add(ioData);
    }

    protected final void addDataOutput(IOData<?> ioData) {
        outputs.add(ioData);
    }

    public final ArrayList<IOData<?>> getInputs() {
        return inputs;
    }

    public final ArrayList<IOData<?>> getOutputs() {
        return outputs;
    }

    public final ArrayList<OpenCVOperation> getParentOperations() {
        return parentOperations;
    }

    public final ArrayList<OpenCVOperation> getChildOperations() {
        return childOperations;
    }

    /**
     * @return A new instance (not a clone) of this operation type.
     */
    public abstract OpenCVOperation newOperationCopy();

    /**
     * @return Creates a JDialog that contains controls to adjust the settings of an operation.
     */
    public abstract JDialog openDialogBox();

    /** Runs the OpenCV function that this operation is associated with. */
    public abstract void performOperation();

    /**
     * @return Returns true if the operation is properly configured.
     */
    public abstract boolean isValid();

    /**
     * @return The OpenCVOperation that supplies the input information to this operation.
     */
    public final OpenCVOperation getInputOperation() {
        return inputOperation;
    }

    /**
     * @param inputOperation The OpenCVOperation that will be used to supply the input information to this operation.
     */
    public final void setInputOperation(final OpenCVOperation inputOperation) {
        this.inputOperation = inputOperation;
    }

    /**
     * @return The output data from this operation.
     */
    public final Mat getOutputMat() {
        return outputMat;
    }

    /**
     * @param m The Mat that this operation's output Mat will be set to.
     */
    protected final void setOutputMat(final Mat m) {
        this.outputMat = m;
    }

    /**
     * @return The Mat from the OpenCVOperation that supplies this operation with data.
     */
    public final Mat getInputMat() {
        if(this.inputOperation != null)
            return this.inputOperation.getOutputMat();
        else {
            System.err.println("Input operation is null.");
            throw(new NullPointerException());
        }
    }

    /**
     * @param outputName The String that this operation's output name will be set to.
     */
    public final void setOutputName(final String outputName) {
        this.outputName.setValue(outputName);
    }

    /**
     * @return A String of this operation's output name.
     */
    public final String getOutputName() {
        return outputName.getValue();
    }

    /**
     * @return The PassableString object that holds the output name of this operation.
     */
    public final PassableString getOutputNameObject() {
        return this.outputName;
    }

    /**
     * @param name The String that this operations name will be set to.
     */
    public final void setOperationName(final String name) {
        this.name.setValue(name);
    }

    /**
     * @return A String of this operation's name.
     */
    public final String getOperationName() {
        return name.getValue();
    }

    /**
     * @return The PassableString object that holds the name of this operation.
     */
    public final PassableString getOperationNameObject() {
        return this.name;
    }

    public OpenCVOperationTransferable getTransferable(int originalIndex) {
        OpenCVOperationTransferable item = new OpenCVOperationTransferable(this, originalIndex);
        return item;
    }

    public static class OpenCVOperationTransferable implements Transferable {

        public static final DataFlavor OPENCV_OPERATION_DATA_FLAVOR = new DataFlavor(OpenCVOperation.class, "Java/OpenCVOperation");
        private OpenCVOperation openCVOperation;
        private int originalIndex;

        public OpenCVOperationTransferable(OpenCVOperation operation, int originalIndex) {
            this.openCVOperation = operation;
            this.originalIndex = originalIndex;
        }

        public int getOriginalIndex() {
            return originalIndex;
        }

        public OpenCVOperation getOriginalOperation() {
            return openCVOperation;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            return openCVOperation;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{OPENCV_OPERATION_DATA_FLAVOR};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(OPENCV_OPERATION_DATA_FLAVOR);
        }

    }

}
