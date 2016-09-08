package core;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

/**
 * Created by dd on 16/9/7.
 */
public class BasicDraw {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat board = Mat.zeros(320, 480, CvType.CV_8UC3);
        Imgproc.rectangle(board, new Point(10,10), new Point(470, 310), new Scalar(0, 0, 255), 2);
        Imgproc.rectangle(board, new Point(10,10), new Point(470, 310), new Scalar(125, 120, 125), -1);
        Imgproc.line(board, new Point(100,50), new Point(220, 50), new Scalar(0, 255, 255), 2);
        //圆心,(长轴,短轴),旋转角度,弧线开始角度,弧线结束角度,颜色,粗细(-1填充)
        Imgproc.ellipse(board, new Point(160, 50), new Size(60,20), 0, 90, 360, new Scalar(0,255,0),2);
        Imgproc.circle(board, new Point(240, 160),20, new Scalar(255,0,255),2,Imgproc.LINE_8,0);
        Imgproc.fillPoly(board,
                Arrays.asList(new MatOfPoint(new Point(10,300), new Point(20, 300), new Point(30,310),new Point(100,280))),
                new Scalar(255, 255, 255));

        String text = "Hello OpenCV!";
        int[] baseLine = new int[1];
        //[out]	baseLine	y-coordinate of the baseline relative to the bottom-most text point.
        Size size = Imgproc.getTextSize(text,Core.FONT_HERSHEY_SIMPLEX, 1.2, 2, baseLine);
        Imgproc.putText(board, text, new Point((480-size.width)/2, size.height),//以文字的左下角为锚点
                Core.FONT_HERSHEY_SIMPLEX, 1.2, new Scalar(255,255,255), 2);
        System.out.println(baseLine[0]);//值是12 不知道是什么含义
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/draw.jpg", board);
    }
}
