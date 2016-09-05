package intro;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.font.ImageGraphicAttribute;

public class HelloWorld {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat m = new Mat(2,2, CvType.CV_8UC3, new Scalar(255,255,0));
        System.out.println(m.dump());

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_8);
        Mat dest = new Mat();
        Imgproc.cvtColor(src, dest, Imgproc.COLOR_BGR2GRAY);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4.jpg", dest);
    }
}
