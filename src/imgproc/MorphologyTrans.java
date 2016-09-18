package imgproc;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by dd on 16/9/14.
 */
public class MorphologyTrans {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        Mat dest = new Mat();

        Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(3,3));
        System.out.println(kernel.dump());
        //dst = open( src, element) = dilate( erode( src, element ) )
        Imgproc.morphologyEx(src, dest, Imgproc.MORPH_OPEN, kernel);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-morphology-open.jpg", dest);

        //dst = close( src, element ) = erode( dilate( src, element ) )
        // Useful to remove small holes (dark regions).
        Imgproc.morphologyEx(src, dest, Imgproc.MORPH_CLOSE, kernel);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-morphology-close.jpg", dest);

        //dilate( src, element ) - erode( src, element )
        // It is useful for finding the outline of an object
        Imgproc.morphologyEx(src, dest, Imgproc.MORPH_GRADIENT, kernel);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-morphology-gradient.jpg", dest);

        //src−open(src,element)
        Imgproc.morphologyEx(src, dest, Imgproc.MORPH_TOPHAT, kernel);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-morphology-tophat.jpg", dest);

        //close(src,element)−src
        Imgproc.morphologyEx(src, dest, Imgproc.MORPH_BLACKHAT, kernel);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-morphology-blackhat.jpg", dest);
    }
}
