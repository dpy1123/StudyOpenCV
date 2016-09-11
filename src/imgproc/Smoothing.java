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
public class Smoothing {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);

        Mat dest = new Mat();
        //Blurs an image using the normalized box filter. equivalent to boxFilter
        //ksize	    blurring kernel size.
        //anchor	anchor point; default value Point(-1,-1) means that the anchor is at the kernel center.
        Imgproc.blur(src, dest, new Size(3,3), new Point(1,1));
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-boxblur.jpg", dest);

        Imgproc.GaussianBlur(src, dest, new Size(3,3), 0, 0);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-gaussianblur.jpg", dest);

        Imgproc.medianBlur(src, dest, 3);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-medianblur.jpg", dest);

        //Sigma values: For simplicity, you can set the 2 sigma values to be the same.
        //      If they are small (< 10), the filter will not have much effect, whereas if they are large (> 150),
        //      they will have a very strong effect, making the image look "cartoonish".
        //Filter size: Large filters (d > 5) are very slow, so it is recommended to use d=5 for real-time applications,
        //      and perhaps d=9 for offline applications that need heavy noise filtering.
        Imgproc.bilateralFilter(src, dest, 5, 10, 10);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-bilateralblur.jpg", dest);
    }
}
