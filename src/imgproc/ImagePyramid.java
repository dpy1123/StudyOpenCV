package imgproc;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by dd on 16/9/15.
 */
public class ImagePyramid {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        Mat dest = new Mat();

        Imgproc.pyrUp(src, dest, new Size(src.width()*2, src.height()*2));
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-pyrup.jpg", dest);

        Imgproc.pyrDown(src, dest, new Size(src.width()/2, src.height()/2));
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-pyrdown.jpg", dest);
    }
}
