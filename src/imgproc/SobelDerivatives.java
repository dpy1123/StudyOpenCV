package imgproc;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 索贝尔
 * Created by dd on 16/9/21.
 */
public class SobelDerivatives {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        Mat dest = new Mat();

        Imgproc.GaussianBlur(src, dest, new Size(3,3), Core.BORDER_DEFAULT);
        Imgproc.cvtColor(dest, dest, Imgproc.COLOR_BGR2GRAY);

        Mat sobel_x = new Mat();
        Mat sobel_y = new Mat();
        Imgproc.Sobel(dest, sobel_x, CvType.CV_16S, 1, 0);//We set it to CV_16S to avoid overflow.
        Imgproc.Sobel(dest, sobel_y, CvType.CV_16S, 1, 0);

        Core.convertScaleAbs(sobel_x, sobel_x);//convert our partial results back to CV_8U
        Core.convertScaleAbs(sobel_y, sobel_y);

        Core.addWeighted(sobel_x, 0.5, sobel_y, 0.5, 1, dest);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-sobel.jpg", dest);
    }
}
