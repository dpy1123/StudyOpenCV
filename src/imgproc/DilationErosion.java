package imgproc;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by dd on 16/9/11.
 */
public class DilationErosion {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        Mat dest = new Mat();

        Mat eroKernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_CROSS, new Size(3,3), new Point(-1,-1));
        Imgproc.erode(src, dest, eroKernel);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-erosion.jpg", dest);

        Mat dilaKernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_CROSS, new Size(3,3), new Point(-1,-1));
        Imgproc.dilate(src, dest, dilaKernel);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-dilation.jpg", dest);
    }
}
