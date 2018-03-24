package operations;

import javax.swing.JDialog;

import org.opencv.core.Mat;

import passableTypes.PassableString;

/**
 *  This is the abstract class from which all other OpenCV operations are derived.
 */
public abstract class OpenCVOperation {

    /** The name of this operation. */
    private PassableString name = new PassableString();
    /** The output name of this operation. */
    private PassableString outputName = new   PassableString();
    /** The OpenCVOperation that this operation will get it's input data from. */
    private OpenCVOperation inputOperation;
    /** The Mat that this openCVOperation will put output data in. */
    private Mat outputMat = new Mat();

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
        if( this.inputOperation != null )
            return this.inputOperation.getOutputMat();
        else {
            System.err.println("Input operation is null.");
            throw( new NullPointerException() );
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

}
