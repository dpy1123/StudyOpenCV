package core;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Created by dd on 16/9/6.
 */
public class MatContrast {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/bk.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        Mat dest = Mat.zeros(src.size(), src.type());

        double a = 1.7;
        int b = 10;
        for (int i = 0; i < src.rows(); i++) {
            for (int j = 0; j < src.cols(); j++) {
                double[] v = src.get(i, j);
                for (int t = 0; t < v.length; t++) {
                    v[t] = (int) Math.max(0, Math.min(a * v[t] + b, 255));
                }
                dest.put(i, j, v);
            }
        }

        Imgcodecs.imwrite("/Users/dd/Documents/tmp/bk-contrast.jpg", dest);
    }
}
