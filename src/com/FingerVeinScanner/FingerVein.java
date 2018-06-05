package com.FingerVeinScanner;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;

public class FingerVein {

    public static void main (String[] args)
    {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        String src = "/Users/priyanka/Desktop/hw05/src/com//FingerVeinScanner/001_L_2.png";
        String dest = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_1.png";
        String dest2 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_2.png";
        FingerVein.editImage2(src,dest2);
        FingerVein.editImage(dest2,dest);
        // FingerVein.editImage(dest2,dest);
    }
    public static boolean compareResults(String src1,String src2)
    {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        String dest1 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_1.png";
        String dest2 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_2.png";
        String dest3 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_3.png";
        String dest4 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_4.png";
        editImage2(src1,dest3);
        editImage2(src2,dest4);
        editImage(dest3,dest1);
        editImage(dest4,dest2);
        return compareImg(dest1,dest2);
    }
    public static void editImage(String src1, String dest1) {
        try{
            String file = src1;
            Mat src = Imgcodecs.imread(file, 0);
            Mat dest = new Mat(src.rows(),src.cols(),src.type());
            dest = src;
            Imgproc.adaptiveThreshold(src,dest,255,0,1,13, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C);
            Imgproc.medianBlur(src,dest,9);
            Core.bitwise_not(src,dest);
            Imgcodecs.imwrite(dest1, dest);

        }catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }


    public static boolean compareImg(String src1, String src2){
        try {

            String file1 = src1;
            String file2 = src2;
            Mat img1 = Imgcodecs.imread(file1,0);
            System.out.println(img1);
            Mat img2 = Imgcodecs.imread(file2,0);

            int result_cols = img1.cols();
            int result_rows = img1.rows();

            Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
            Imgproc.matchTemplate(img1,img2,result,0);
            Imgproc.threshold(result,result,0.5,1.0,Imgproc.THRESH_TOZERO);
            Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

            Point matchLoc;
            if(0 == Imgproc.TM_SQDIFF || 0 == Imgproc.TM_SQDIFF_NORMED) {
                matchLoc = mmr.minLoc;
            }else {
                matchLoc = mmr.maxLoc;
            }

            System.out.println(mmr.maxVal);
            System.out.println(mmr.minVal);
            double threshold = 4.15*(Math.pow(10,9));

            if(mmr.maxVal<threshold)
            {
                return true;
            }

                return false;
        } catch(Exception e) {

        }
        return false;
    }

    public static void editImage2(String src1, String dest)
    {
        String file1 = src1;
        Mat src = Imgcodecs.imread(file1);
        Mat dst = new Mat();
        Mat alpha = new Mat();
        Mat temp = new Mat();
        Imgproc.cvtColor(src,temp,Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(temp,alpha,0,225,Imgproc.THRESH_BINARY);
        //Imgproc.adaptiveThreshold(temp, alpha, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 1501, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C);

        ArrayList<Mat> rgba = new ArrayList<>(4);
        ArrayList<Mat> rgb = new ArrayList<>(3);
        Core.split(src,rgb);
        System.out.println(rgb.size());
        rgba.add(rgb.get(0));
        rgba.add(rgb.get(1));
        rgba.add(rgb.get(2));
        System.out.println(src);
        System.out.println(alpha);
        rgba.add(alpha);
        Core.merge(rgba, dst);
        System.out.println(dst.channels());
        Imgcodecs.imwrite(dest, dst);



    }
}