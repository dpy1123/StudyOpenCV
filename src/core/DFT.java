package core;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Discrete Fourier Transform  离散傅里叶变换
 * Created by dd on 16/9/8.
 */
public class DFT {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/黑长直.jpg", Imgcodecs.IMREAD_REDUCED_GRAYSCALE_4);

        //将图像填充为dft执行效率高的尺寸 (尺寸为2的平方数 或 是由2、3、5的乘积构成的数)
        int optRowSize = Core.getOptimalDFTSize(src.rows());
        int optColSize = Core.getOptimalDFTSize(src.cols());
        System.out.println("dft opt size: " + optColSize +" * "+ optRowSize);
        Mat paddedSrc = new Mat(new Size(optColSize, optRowSize), CvType.CV_8UC1);
        Core.copyMakeBorder(src, paddedSrc, (optRowSize-src.height())/2, (optRowSize-src.height())/2,
                (optColSize-src.width())/2, (optColSize-src.width())/2, Core.BORDER_CONSTANT, Scalar.all(0));

        //由于dft要求src和dst的Mat的channel和type一致,而输出的dst是复数形式的,所以对paddedSrc进行了处理
        Mat srcR = new Mat();//实数部
        paddedSrc.assignTo(srcR, CvType.CV_32F);
        Mat srcI = new Mat(new Size(optColSize, optRowSize), CvType.CV_32F, Scalar.all(0));//虚部
        Mat srcComplex = new Mat();
        Core.merge(Arrays.asList(srcR, srcI), srcComplex);

        //dft
        Mat resComplex = new Mat();
        Core.dft(srcComplex, resComplex);

        //dft结果 复数表示 转成 频域表示 M = \sqrt[2]{ {Re(DFT(I))}^2 + {Im(DFT(I))}^2}
        List<Mat> list = new ArrayList<>();
        Core.split(resComplex, list);
        Mat resMag = new Mat();
        Core.magnitude(list.get(0), list.get(1), resMag);

        //去掉加的pad
        resMag = new Mat(resMag, new Rect((optColSize-src.width())/2, (optRowSize-src.height())/2, src.width(), src.height()));
        //将变换的频率图像四角移动到中心（原来亮的部分在四角 现在移动中心，便于后面的处理）
        int halfWidth = resMag.width()/2;
        int halfHeight = resMag.height()/2;
        Mat topLeft = new Mat(resMag, new Rect(0, 0, halfWidth, halfHeight));
        Mat topRight = new Mat(resMag, new Rect(halfWidth, 0, halfWidth, halfHeight));
        Mat bottomLeft = new Mat(resMag, new Rect(0, halfHeight, halfWidth, halfHeight));
        Mat bottomRight = new Mat(resMag, new Rect(halfWidth, halfHeight, halfWidth, halfHeight));
        Mat tmp = new Mat();

        topLeft.copyTo(tmp);
        bottomRight.copyTo(topLeft);
        tmp.copyTo(bottomRight);

        topRight.copyTo(tmp);
        bottomLeft.copyTo(topRight);
        tmp.copyTo(bottomLeft);


        //显示中心低频部分，加对数是为了更好的显示  M = \log{(1 + M)}
        for (int i = 0; i < resMag.rows(); i++) {
            for (int j = 0; j < resMag.cols(); j++) {
                resMag.put(i, j, 1+resMag.get(i,j)[0]);
            }
        }
        Core.log(resMag, resMag);
        Core.normalize(resMag, resMag, 0, 255, Core.NORM_MINMAX);

        Imgcodecs.imwrite("/Users/dd/Documents/tmp/dft.jpg", resMag);
    }
}
