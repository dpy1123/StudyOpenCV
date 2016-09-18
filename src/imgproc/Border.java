package imgproc;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by dd on 16/9/18.
 */
public class Border {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        Mat dest = new Mat();

        int top = 0, bottom = 0, left = 0, right = 0;
        //令上下边距为原图高的5%, 左右边距为原图宽的5%
        top = bottom = (int)(0.05 * src.height());
        left = right = (int)(0.05 * src.width());

        //使用指定的颜色填充,  iiiiii|abcdefgh|iiiiiii
        Core.copyMakeBorder(src, dest, top, bottom, left, right, Core.BORDER_CONSTANT, new Scalar(0,0,255));
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-border-constant.jpg", dest);

        //使用边界原有颜色填充, aaaaaa|abcdefgh|hhhhhhh
        Core.copyMakeBorder(src, dest, top, bottom, left, right, Core.BORDER_REPLICATE);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-border-replicate.jpg", dest);

        //cdefgh|abcdefgh|abcdefg
        Core.copyMakeBorder(src, dest, top, bottom, left, right, Core.BORDER_WRAP);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-border-wrap.jpg", dest);

        //fedcba|abcdefgh|hgfedcb
        Core.copyMakeBorder(src, dest, top, bottom, left, right, Core.BORDER_REFLECT);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-border-reflect.jpg", dest);

        //gfedcb|abcdefgh|gfedcba  原边界a和h不重复
        Core.copyMakeBorder(src, dest, top, bottom, left, right, Core.BORDER_REFLECT_101);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/4-border-reflect101.jpg", dest);
    }
}
