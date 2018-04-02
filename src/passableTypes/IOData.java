package passableTypes;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import operations.OpenCVOperation;

public abstract class IOData<T> {

    public enum IOType {
        INPUT, OUTPUT
    }

    private T data;
    private String name;
    private OpenCVOperation dataParent;
    private IOType ioType;
    private OpenCVOperation dataSource;

    public IOData(final OpenCVOperation origin, String name, IOType ioType, T data) {
        this.setParent(origin);
        this.setName(name);
        this.ioType = ioType;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    /** Gets the OpenCVOperation that this data belongs to.
     * @return The operation that this io data belongs to.
     */
    public OpenCVOperation getParent() {
        return dataParent;
    }

    /** Gets the OpenCVOperation that supplies the data.
     * @return The operation that provides the input for this.
     */
    public OpenCVOperation getSource() {
        return dataSource;
    }

    public String getName() {
        return name;
    }

    public IOType getIOType() {
        return ioType;
    }

    private void setParent(final OpenCVOperation origin) {
        this.dataParent = origin;
    }

    private void setName(final String name) {
        this.name = name;
    }
    
    public void setData(T data) {
        this.data = data;
    }

    public void setData(T data, OpenCVOperation source) {
        this.data = data;
        this.dataSource = source;
    }

    public static class ImageMat extends IOData<Mat> {
        public ImageMat(final OpenCVOperation origin, final String name, IOType ioType) {
            super(origin, name, ioType, new Mat());
        }
    }

    public static class ContoursList extends IOData<List<MatOfPoint>> {
        public ContoursList(final OpenCVOperation origin, final String name, IOType ioType) {
            super(origin, name, ioType, new ArrayList<MatOfPoint>());
        }
    }
}
