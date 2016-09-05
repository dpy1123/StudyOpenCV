package core;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by dd on 16/9/5.
 */
public class MatOperation {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);


        int remapScale = 10;
        int[] colorRemapping = new int[256];
        for (int i = 0; i < 256; i++) {
            colorRemapping[i] = i/remapScale * remapScale;
        }

        long startF = Core.getTickCount();

        Mat dest = src.clone();

        //1.人肉修改像素值
/*        int rows = src.rows();
        int cols = src.cols();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double[] origin =  src.get(i,j);
                double[] fixed = {255, 0, 0};// BGR
                for (int t = 0; t < origin.length; t++) {
                    fixed[t] = colorRemapping[(int)origin[t]];
                }
                dest.put(i,j, fixed);
            }
        }*/

        //2.用框架提供的LUT函数
        Mat convertMat = new Mat(1, 256, CvType.CV_32S);
        /*
         CV_8U and CV_8S -> byte[],
         CV_16U and CV_16S -> short[],
         CV_32S -> int[],
         CV_32F -> float[],
         CV_64F-> double[].
         */
        convertMat.put(0,0,colorRemapping);
        Core.LUT(src, convertMat, dest);


        long endF = Core.getTickCount();
        System.out.println("cost: "+(endF-startF)/Core.getTickFrequency());
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-src.jpg", src);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-remap.jpg", dest);

    }
}
