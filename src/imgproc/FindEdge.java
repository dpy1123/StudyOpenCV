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
public class FindEdge {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/bk2.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        Mat preProcess = new Mat();
        Imgproc.GaussianBlur(src, preProcess, new Size(3,3), Core.BORDER_DEFAULT);
        Imgproc.cvtColor(preProcess, preProcess, Imgproc.COLOR_BGR2GRAY);
        Mat dest = new Mat();

        //SobelDerivatives  基础
        Mat sobel_x = new Mat();
        Mat sobel_y = new Mat();
        Imgproc.Sobel(preProcess, sobel_x, CvType.CV_16S, 1, 0);//We set it to CV_16S to avoid overflow.
        Imgproc.Sobel(preProcess, sobel_y, CvType.CV_16S, 1, 0);
        Core.convertScaleAbs(sobel_x, sobel_x);//convert our partial results back to CV_8U
        Core.convertScaleAbs(sobel_y, sobel_y);
        Core.addWeighted(sobel_x, 0.5, sobel_y, 0.5, 1, dest);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/bk2-sobel.jpg", dest);

        //Laplace Operator 当ksize>1时,也是用的sobel算子
        Imgproc.Laplacian(preProcess, dest, CvType.CV_16S, 3, 1, 0);
        Core.convertScaleAbs(dest, dest);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/bk2-laplace.jpg", dest);

        //Canny Edge Detector
        int lowThresh = 20;
        int highThresh = lowThresh * 3;//upper:lower ratio between 2:1 and 3:1
        Imgproc.Canny(preProcess, dest, lowThresh, highThresh);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/bk2-canny.jpg", dest);

    }
}
