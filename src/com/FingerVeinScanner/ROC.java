/**
 *  FingerVein Scanning System 1.0
 *  ------------------------------
 *  Harshil Parikh (misterharshil@gmail.com)
 *  Dr. Creed Jones (crjones@calbaptist.edu)
 *  California baptist university
 *
 */



package com.FingerVeinScanner;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ROC {

    int index = 1;
    static StringBuilder builder = new StringBuilder();
    static BufferedWriter writer;// = new BufferedWriter(new FileWriter("/Users/priyanka/Desktop/ROC" + ".csv"));
    public static final double THRESHOLD = 3.45*(Math.pow(10,9));
    public static void main(String args[]) throws IOException{
        ROC fingerVein = new ROC();
        writer = new BufferedWriter(new FileWriter("/Users/priyanka/Desktop/ROC" + ".csv"));
        fingerVein.heading();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        for(int i = 001; i<=110; i++) {
            System.out.println(i);
            String Lpath1 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(i) + "/001_L_1.png";
            String Rpath1 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(i) + "/001_R_1.png";
            String Lpath2 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(i) + "/001_L_2.png";
            String Rpath2 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(i) + "/001_R_2.png";
            for(int j = 001; j<=110; j++) {
                String L2path1 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(j) + "/001_L_1.png";
                String R2path1 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(j) + "/001_R_1.png";
                String L2path2 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(j) + "/001_L_2.png";
                String R2path2 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(j) + "/001_R_2.png";
                fingerVein.Comparison(Lpath1,L2path1);
                fingerVein.Comparison(Lpath1,L2path2);
                fingerVein.Comparison(Lpath1,R2path1);
                fingerVein.Comparison(Lpath1,R2path2);

                fingerVein.Comparison(Lpath2,L2path1);
                fingerVein.Comparison(Lpath2,L2path2);
                fingerVein.Comparison(Lpath2,R2path1);
                fingerVein.Comparison(Lpath2,R2path2);

                fingerVein.Comparison(Rpath1,L2path1);
                fingerVein.Comparison(Rpath1,L2path2);
                fingerVein.Comparison(Rpath1,R2path1);
                fingerVein.Comparison(Rpath1,R2path2);

                fingerVein.Comparison(Rpath2,L2path1);
                fingerVein.Comparison(Rpath2,L2path2);
                fingerVein.Comparison(Rpath2,R2path1);
                fingerVein.Comparison(Rpath2,R2path2);


            }
        }
        writer.write(builder.toString());//save the string representation of the board
        writer.close();
    }

    public void Comparison(String file1, String file2)
    {
        String dest1 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_1.png";
        String dest2 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_2.png";
        String dest3 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_3.png";
        String dest4 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_4.png";
        editImage2(file1,dest3);
        editImage2(file2,dest4);
        editImage(dest3,dest1);
        editImage(dest4,dest2);
        boolean sameFinger = file1.substring(0,file1.length()-5).equals(file2.substring(0,file2.length()-5));
        writeToArray(file1, file2, resultsToString(sameFinger,compareImg(dest1, dest2)), index);
        index++;
    }

    public String resultsToString(boolean expected,boolean real)
    {
        if(expected&&real)
        {
            return ",1,0,0,0";
        }
        else if(!expected&&!real)
        {
            return ",0,1,0,0";
        }
        else if(!expected&&real)
        {
            return ",0,0,1,0";
        }
        else
            return ",0,0,0,1";

    }

    public void editImage(String src1, String dest1) {
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

    public boolean compareImg(String src1, String src2){
        try {
            String file1 = src1;
            String file2 = src2;
            Mat img1 = Imgcodecs.imread(file1,0);
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
            if(mmr.maxVal<THRESHOLD)
            {
                return true;
            }

            return false;
        } catch(Exception e) {

        }
        return false;
    }

    public void editImage2(String src1, String dest)
    {
        String file1 = src1;
        Mat src = Imgcodecs.imread(file1);
        Mat dst = new Mat();
        Mat alpha = new Mat();
        Mat temp = new Mat();
        Imgproc.cvtColor(src,temp,Imgproc.COLOR_BGR2GRAY);
        Imgproc.adaptiveThreshold(temp, alpha, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 1501, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C);

        ArrayList<Mat> rgba = new ArrayList<>(4);
        ArrayList<Mat> rgb = new ArrayList<>(3);
        Core.split(src,rgb);
        rgba.add(rgb.get(0));
        rgba.add(rgb.get(1));
        rgba.add(rgb.get(2));
        rgba.add(alpha);
        Core.merge(rgba, dst);
        Imgcodecs.imwrite(dest, dst);
    }

    public void heading()
    {
        builder.append("File 1" + "");//append to the output string
        builder.append(",");//then add comma (if you don't like commas you can use spaces)
        builder.append("File 2" + "");//append to the output string
        builder.append(",True Positive,True Negative,False Positive,False Negative");
        builder.append("\n");//append new line at the end of the row
    }

    public void writeToArray(String path1,String path2, String results,int index)
    {
        builder.append(path1 + "");//append to the output string
        builder.append(",");//then add comma (if you don't like commas you can use spaces)
        builder.append(path1 + "");//append to the output string
        builder.append(results);
        builder.append("\n");//append new line at the end of the row

    }
}