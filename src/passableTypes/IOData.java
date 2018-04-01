package passableTypes;

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
    private OpenCVOperation origin;
    private IOType ioType;

    public IOData(final OpenCVOperation origin, String name, IOType ioType ) {
        this.setOrigin(origin);
        this.setName(name);
        this.ioType = ioType;
    }

    public T getData() {
        return data;
    }

    public OpenCVOperation getOrigin() {
        return origin;
    }
    
    public String getName() {
        return name;
    }
    
    public IOType getIOType() {
        return ioType;
    }

    private void setOrigin(final OpenCVOperation origin) {
        this.origin = origin;
    }
    
    private void setName( final String name ) {
        this.name = name;
    }
    
    protected void setData( T data ) {
        this.data = data;
    }

    static public class ImageMat extends IOData<Mat> {
        public ImageMat(final OpenCVOperation origin, IOType ioType ) {
            super(origin, "Image Mat", ioType );
        }
    }
    
    static public class ContoursList extends IOData<List<MatOfPoint>> {
        public ContoursList(final OpenCVOperation origin, IOType ioType) {
            super(origin, "Contours List", ioType );
        }
    }
}
