package core;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by dd on 16/9/5.
 */
public class MatMask {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        Mat dest = Mat.eye(src.size(), src.type());

        //1.人肉修改像素值
       /* int rows = src.rows();
        int cols = src.cols();
        for (int i = 1; i < rows-1; i++) {
            for (int j = 1; j < cols-1; j++) {
                double[] pre = src.get(i, j-1);
                double[] cur = src.get(i, j);
                double[] nxt = src.get(i, j+1);
                double[] up = src.get(i-1, j);
                double[] down = src.get(i+1, j);
                dest.put(i, j, sharp(pre, cur, nxt, up, down));
            }
        }
        //四周一圈没有sharp,设置成白色
        dest.row(0).setTo(new Scalar(255, 255, 255));           // The top row
        dest.row(rows - 1).setTo(new Scalar(255, 255, 255));    // The bottom row
        dest.col(0).setTo(Scalar.all(255));                     // The left column
        dest.col(cols - 1).setTo(new Scalar(255, 255, 255));    // The right column

        */
        //2.filter2D
        Mat kernel = new Mat(3, 3, CvType.CV_32S);
        kernel.put(0, 0, new Scalar(0, -1, 0).val);
        kernel.put(1, 0, new Scalar(-1, 5, -1).val);
        kernel.put(2, 0, new Scalar(0, -1, 0).val);
        System.out.println(kernel.dump());
        Imgproc.filter2D(src, dest, dest.depth(), kernel);

        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-sharp.jpg", dest);

    }


    private static double[] sharp(double[] pre, double[] cur, double[] nxt, double[] up, double[] down){
        double[] result = new double[cur.length];

        for (int i = 0; i < result.length; i++) {
            //I(i,j) = 5*I(i,j) - [ I(i-1,j) + I(i+1,j) + I(i,j-1) + I(i,j+1)]
            double tmp = 5*cur[i] - (pre[i]+nxt[i]+up[i]+down[i]);
            result[i] = Math.max(0, Math.min(tmp, 255));
        }
        return result;
    }
}
