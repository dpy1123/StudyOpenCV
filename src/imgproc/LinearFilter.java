package imgproc;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by dd on 16/9/17.
 */
public class LinearFilter {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        Mat dest = new Mat();

        Mat kernel = new Mat(3, 3, CvType.CV_32F);
        kernel.put(0, 0, new float[]{1/9f,1/9f,1/9f,1/9f,1/9f,1/9f,1/9f,1/9f,1/9f});
        System.out.println(kernel.dump());

        Imgproc.filter2D(src, dest, src.depth(), kernel, new Point(-1,-1), 0);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-filter2d.jpg", dest);
    }
}
