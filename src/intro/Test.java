package intro;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dd on 16/9/22.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        simpleSign();

        Mat src = Imgcodecs.imread("/Users/dd/OneDrive/图片/4.jpg", Imgcodecs.IMREAD_REDUCED_COLOR_4);
        //创建水印
        Mat text = drawSign(480, 300, "Hello OpenCV!");
//        inverseColor(text);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/watermark/text.jpg", text);

        //原图dft
        Mat srcDft = dftC3(src, Core.DFT_COMPLEX_OUTPUT);
        Mat srcDftMP = dftComplex2MP(srcDft);
        showDft("/Users/dd/Documents/tmp/watermark/src-dft.jpg", srcDftMP);

        //原图idft
        Mat srcIdft = idftC3(srcDft);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/watermark/src-idft.jpg", srcIdft);

        //原图dft与水印叠加
        List<Mat> listSrc = new ArrayList<>();
        Core.split(srcDft, listSrc);
        List<Mat> listText = new ArrayList<>();
        text.convertTo(text, CvType.CV_32F);
        Core.split(text, listText);
        for (int i = 0; i < listSrc.size(); i++) {
            Imgcodecs.imwrite("/Users/dd/Documents/tmp/watermark/listSrc"+i+".jpg", listSrc.get(i));
        }
        for (int i = 0; i < listText.size(); i++) {
            Imgcodecs.imwrite("/Users/dd/Documents/tmp/watermark/listText"+i+".jpg", listText.get(i));
        }
        Core.add(listSrc.get(0), listText.get(0), listSrc.get(0));
        Core.add(listSrc.get(2), listText.get(1), listSrc.get(2));
        Core.add(listSrc.get(4), listText.get(2), listSrc.get(4));
        for (int i = 0; i < listSrc.size(); i++) {
            Imgcodecs.imwrite("/Users/dd/Documents/tmp/watermark/listSrcAft"+i+".jpg", listSrc.get(i));
        }
        Mat mixDft = new Mat();
        Core.merge(listSrc, mixDft);
        Mat mixDftMP = dftComplex2MP(mixDft);
        showDft("/Users/dd/Documents/tmp/watermark/mix-dft.jpg", mixDftMP);

        //生成水印图像
        Mat mixIdft = idftC3(dftMP2Complex(mixDftMP));
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/watermark/mix-idft.jpg", mixIdft);

        //还原
        Mat watermark = Imgcodecs.imread("/Users/dd/Documents/tmp/watermark/mix-idft.jpg");
        Mat watermarkDft = dftC3(watermark, Core.DFT_COMPLEX_OUTPUT);
        Mat watermarkDftMP = dftComplex2MP(watermarkDft);
        showDft("/Users/dd/Documents/tmp/watermark/watermark-dft.jpg", watermarkDftMP);
        Mat sign = new Mat();
        Core.subtract(watermarkDft, srcDft, sign);
        showDft("/Users/dd/Documents/tmp/watermark/watermark-sub.jpg", sign);
        List<Mat> listSign = new ArrayList<>();
        Core.split(sign, listSign);
        for (int i = 0; i < listSign.size(); i++) {
            Imgcodecs.imwrite("/Users/dd/Documents/tmp/watermark/listSign"+i+".jpg", listSign.get(i));
        }
        Mat originSign = new Mat();
//        Core.merge(Arrays.asList(listSign.get(0),listSign.get(2),listSign.get(4)), originSign);
        sign = dftMP2Complex(sign);
        originSign = idftC3(sign);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/watermark/sign.jpg", originSign);



    }

    /**
     * 反色
     * @param img
     */
    private static void inverseColor(Mat img) {
        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols(); j++) {
                double[] value = img.get(i,j);
                for (int k = 0; k < value.length; k++) {
                    value[k] = 255 - value[k];
                }
                img.put(i,j,value);
            }
        }
    }


    private static Mat dftMP2Complex(Mat mp){
        List<Mat> list = new ArrayList<>();
        Core.split(mp, list);
        int c = list.size()/2;
        Mat[] channel = new Mat[c];
        for (int i = 0; i < c; i++) {
            Mat x = new Mat();
            Mat y = new Mat();
            Core.polarToCart(list.get(2*i), list.get(2*i+1), x, y);
            Core.polarToCart(list.get(2*i), list.get(2*i+1), x, y);
            channel[i] = new Mat();
            Core.merge(Arrays.asList(x, y), channel[i]);
        }
        Mat complex = new Mat();
        Core.merge(Arrays.asList(channel), complex);
        return complex;
    }

    /**
     * dft结果 复数表示 转成 振幅相位表示
     * @param complex C1/C3均可
     * @return C2/C6
     */
    private static Mat dftComplex2MP(Mat complex){
        List<Mat> list = new ArrayList<>();
        Core.split(complex, list);
        int c = list.size()/2;
        Mat[] channel = new Mat[c];
        for (int i = 0; i < c; i++) {
            Mat mag = new Mat();
            Mat pha = new Mat();
            Core.magnitude(list.get(2*i), list.get(2*i+1), mag);//M = \sqrt[2]{ {Re(DFT(I))}^2 + {Im(DFT(I))}^2}
            Core.phase(list.get(2*i), list.get(2*i+1), pha);
            channel[i] = new Mat();
            Core.merge(Arrays.asList(mag, pha), channel[i]);
        }
        Mat mp = new Mat();
        Core.merge(Arrays.asList(channel), mp);
        return mp;
    }

    /**
     * 显示dft结果 只取振幅 C1/C3均可
     * @param path
     * @param mp dft结果 振幅相位表示 C2/C6
     */
    private static void showDft(String path, Mat mp){
        //只取振幅
        List<Mat> list = new ArrayList<>();
        Core.split(mp, list);
        int c = list.size()/2;
        Mat mag = new Mat();
        if (c==1){
            mag = list.get(0);
        }else if (c==3){
            Core.merge(Arrays.asList(list.get(0),list.get(2),list.get(4)), mag);
        }
        //显示中心低频部分，加对数是为了更好的显示  M = \log{(1 + M)}
        for (int i = 0; i < mag.rows(); i++) {
            for (int j = 0; j < mag.cols(); j++) {
                double[] v = mag.get(i,j);
                for (int k = 0; k < v.length; k++) {
                    v[k] = 1 + v[k];
                }
                mag.put(i, j, v);
            }
        }
        if (c==1){
            Core.log(mag, mag);
            Core.normalize(mag, mag, 0, 255, Core.NORM_MINMAX);
        }else if (c==3){
            List<Mat> tmp = new ArrayList<>();
            Core.split(mag, tmp);
            for (int i = 0; i < tmp.size(); i++) {
                Mat t = tmp.get(i);
                Core.log(t, t);
                Core.normalize(t, t, 0, 255, Core.NORM_MINMAX);
            }
            Core.merge(tmp, mag);
        }

        Imgcodecs.imwrite(path, mag);
    }

    /**
     * 逆傅立叶
     * @param src 32FC6
     * @return 3通道的彩图
     */
    private static Mat idftC3(Mat src){
        List<Mat> list = new ArrayList<>();
        Core.split(src, list);
        Mat resComplex = new Mat();
        int c = list.size()/2;
        Mat[] array = new Mat[c];
        for (int i = 0; i < c; i++) {
            Mat channel = new Mat();
            array[i] = new Mat();
            Core.merge(Arrays.asList(list.get(2*i), list.get(2*i+1)), channel);
            Core.idft(channel, array[i], Core.DFT_SCALE|Core.DFT_REAL_OUTPUT, 0);

//            Imgcodecs.imwrite("/Users/dd/Documents/tmp/watermark/idft-"+i+".jpg", array[i]);
        }
        Core.merge(Arrays.asList(array), resComplex);
        return resComplex;
    }

    /**
     * 傅立叶
     * @param src 3通道的彩图
     * @return 32FC6 {B{实部,虚部}G{实部,虚部}R{实部,虚部}}
     */
    private static Mat dftC3(Mat src, int dftFlag){
        List<Mat> list = new ArrayList<>();
        Core.split(src, list);
        Mat resComplex = new Mat();
        Mat[] array = new Mat[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Mat channel = list.get(i);
            array[i] = dft(channel, dftFlag);
        }

        Core.merge(Arrays.asList(array), resComplex);

        return resComplex;
    }

    private static Mat dft(Mat src, int dftFlag){
        //由于dft要求src和dst的Mat的channel和type一致,而输出的dst是复数形式的,所以对paddedSrc进行了处理
        Mat srcR = new Mat();//实数部
        src.assignTo(srcR, CvType.CV_32F);
        Mat srcI = new Mat(src.size(), CvType.CV_32F, Scalar.all(0));//虚部
        Mat srcComplex = new Mat();
        Core.merge(Arrays.asList(srcR, srcI), srcComplex);

        //dft
        Mat resComplex = new Mat();
        Core.dft(srcComplex, resComplex, dftFlag, 0);

        return resComplex;
    }

    /**
     * 简单水印Test
     * @throws UnsupportedEncodingException
     */
    private static void simpleSign() throws UnsupportedEncodingException {
        String text = "English Math Comic 中午  cdcccddd";
        byte[] bytes = (text.getBytes("utf-8"));

        Mat dest = new Mat(new Size(300, 50), CvType.CV_8UC1);
        dest.put(0, 0, bytes);
        Imgcodecs.imwrite("/Users/dd/Documents/tmp/test.bmp", dest);//注意如果保存为jpg的话会因压缩造成数值不准,推荐bmp或tiff

        Mat src = Imgcodecs.imread("/Users/dd/Documents/tmp/test.bmp", CvType.CV_8UC1);
        byte[] data = new byte[bytes.length];
        src.get(0,0, data);

        String cText = new String(data, "utf-8");
        System.out.println(cText);
    }

    /**
     * 画水印
     * @param width
     * @param height
     * @param text
     * @return
     */
    private static Mat drawSign(int width, int height, String text){
        Mat board = Mat.zeros(height, width, CvType.CV_8UC3);
        int[] baseLine = new int[1];
        //[out]	baseLine	y-coordinate of the baseline relative to the bottom-most text point.
        Size size = Imgproc.getTextSize(text,Core.FONT_HERSHEY_SIMPLEX, 1.2, 2, baseLine);
        Imgproc.putText(board, text, new Point((width-size.width)/2, size.height),//以文字的左下角为锚点
                Core.FONT_HERSHEY_SIMPLEX, 1.2, new Scalar(255,255,255), 2);
        return board;
    }
}
