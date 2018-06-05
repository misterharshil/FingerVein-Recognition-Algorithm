/**
 *  FingerVein Scanning System 1.0
 *  ------------------------------
 *  Harshil Parikh (misterharshil@gmail.com)
 *  Dr. Creed Jones (crjones@calbaptist.edu)
 *  California baptist university
 *
 */



package com.FingerVeinScanner;
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

public class ConfusionMatrix {

    public static final double THRESHOLD = 4.15 * (Math.pow(10, 9));
    static String[][] forExcel = new String[220][3];
    public static void main(String args[]) throws IOException{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        int index=0;
        for(int i = 001; i<=110; i++) {
            String Lpath1 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(i) + "/001_L_1.png";
            String Rpath1 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(i) + "/001_R_1.png";
            String Lpath2 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(i) + "/001_L_2.png";
            String Rpath2 = "/Users/priyanka/Desktop/VERA/" + Integer.toString(i) + "/001_R_2.png";
            String dest1 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_1.png";
            String dest2 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_2.png";
            String dest3 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_3.png";
            String dest4 = "/Users/priyanka/Desktop/hw05/src/com/FingerVeinScanner/ProcessedIMG_4.png";
            editImage2(Lpath1,dest3);
            editImage2(Lpath2,dest4);
            editImage(dest3,dest1);
            editImage(dest4,dest2);
            writeToArray(Lpath1,Lpath2,compareImg(dest1,dest2),index);
            index++;
            editImage2(Rpath1,dest3);
            editImage2(Rpath2,dest4);
            editImage(dest3,dest1);
            editImage(dest4,dest2);
            writeToArray(Rpath1,Rpath2,compareImg(dest1,dest2),index);
            index++;
        }
        for(int i = 0; i <110;i++)
        {
            System.out.print(forExcel[i][0]);
            System.out.print(forExcel[i][1]);
            System.out.println(forExcel[i][2]);
        }
        toFile(forExcel);
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

    public static void editImage2(String src1, String dest)
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
    public static double compareImg(String src1, String src2) {
        try {
            String file1 = src1;
            String file2 = src2;
            Mat img1 = Imgcodecs.imread(file1, 0);
            Mat img2 = Imgcodecs.imread(file2, 0);

            int result_cols = img1.cols();
            int result_rows = img1.rows();

            Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
            // Normalized Grayscale co-relation
            Imgproc.matchTemplate(img1, img2, result, 0);
            Imgproc.threshold(result, result, 0.5, 1.0, Imgproc.THRESH_TOZERO);
            Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

            Point matchLoc;
            if (0 == Imgproc.TM_SQDIFF || 0 == Imgproc.TM_SQDIFF_NORMED) {
                matchLoc = mmr.minLoc;
            } else {
                matchLoc = mmr.maxLoc;
            }
            // Threshold Difference.
            System.out.println("[Threshold Difference] - " + mmr.maxVal);
            return mmr.maxVal;
        } catch (Exception e) {
            System.out.println(" There's some error captain! ");
        }
        return Integer.MAX_VALUE;
    }

    public static void writeToArray(String path1,String path2, double threshold,int index)
    {
        forExcel[index][0] = path1;
        forExcel[index][1] = path2;
        forExcel[index][2] = Double.toString(threshold);
    }

    public static void toFile(String[][] board) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < board.length; i++)//for each row
        {
            for(int j = 0; j < board[0].length; j++)//for each column
            {
                builder.append(board[i][j]+"");//append to the output string
                if(j < board[0].length - 1)//if this is not the last row element
                    builder.append(",");//then add comma (if you don't like commas you can use spaces)
            }
            builder.append("\n");//append new line at the end of the row
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/priyanka/Desktop/ConfusionMatrix" + ".csv"));
        writer.write(builder.toString());//save the string representation of the board
        writer.close();
    }
}