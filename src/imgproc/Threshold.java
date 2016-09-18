package imgproc;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by dd on 16/9/17.
 */
public class Threshold {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        Mat dest = new Mat();
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);

        //大于thresh的变成maxval, 小于等于的变成0
        Imgproc.threshold(src, dest, 128, 255, Imgproc.THRESH_BINARY);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-thresh-binary.jpg", dest);

        //和上面正相反
        Imgproc.threshold(src, dest, 128, 255, Imgproc.THRESH_BINARY_INV);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-thresh-binary-inv.jpg", dest);

        //大于thresh的变成thresh, 小于等于的不变
        Imgproc.threshold(src, dest, 128, 255, Imgproc.THRESH_TRUNC);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-thresh-trunc.jpg", dest);

        //大于thresh的不变, 小于等于的变成0
        Imgproc.threshold(src, dest, 128, 255, Imgproc.THRESH_TOZERO);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-thresh-zero.jpg", dest);

        //大于thresh的变成0, 小于等于的不变
        Imgproc.threshold(src, dest, 128, 255, Imgproc.THRESH_TOZERO_INV);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-thresh-zero-inv.jpg", dest);

        //区别: thresh不是指定,而是通过一个kernel去找, mean_c表示该点的值是周边的均值减去c,
        // threshold只能是binary或binary_inv, 3是size, 0是C
        Imgproc.adaptiveThreshold(src, dest, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 3, 0);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-thresh-mean-binary.jpg", dest);

        //kernel是高斯kernel, 改点的值是周边的权重求和减去c
        Imgproc.adaptiveThreshold(src, dest, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 3, 0);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-thresh-gaussian-binary.jpg", dest);
    }
}
