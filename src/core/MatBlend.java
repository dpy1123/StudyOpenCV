package core;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Created by dd on 16/9/5.
 */
public class MatBlend {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src1 = Imgcodecs.imread("/Users/dd/OneDrive/图片/bk2.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        Mat src2 = Imgcodecs.imread("/Users/dd/OneDrive/图片/bk.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);


        Mat dest = new Mat();
        Core.addWeighted(src1, 0.3, src2, 0.7, 0, dest);//这里要求src1和src2的size是一样的

        Imgcodecs.imwrite("/Users/dd/Documents/tmp/bk-blend.jpg", dest);
    }
}
