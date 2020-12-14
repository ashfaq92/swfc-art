package com.github.jelmerk.knn.examples.test.model;

import com.github.jelmerk.knn.examples.fscs.art.FSCS_ART;
import com.github.jelmerk.knn.examples.hnsw.art.HNSW_ART;
import com.github.jelmerk.knn.examples.kdfc.art.KDFC_ART;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TestEfficiencySimple {

    final static int n = 1003; // �ظ�����

    public static void main(String[] args) throws IOException, InterruptedException {

        int num = 10000; // number of test cases to generate
        int[][] bd = { {-5000, 5000} }; // dimensions
        int simulations = 1;
        long totalTime = 0;
        long time;

        for (int i = 1; i <= simulations; i++) {
            // time = testFscsART(bd, num);
            time = testLimBalKDFC(bd, num);
            // time = testHnswArt(bd, num);
            totalTime = totalTime + time;
            System.out.println(i + "\tTimeTaken: " + time + "\tmean: " + ((double) totalTime / (double) i));
        }
        System.out.println("Avg. Time Taken: " + (double) totalTime / (double) simulations);
        java.awt.Toolkit.getDefaultToolkit().beep();

    }

    public static long testFscsART(int[][] bd, int pointNum) throws IOException {

        FSCS_ART fscs;
        long n1 = System.currentTimeMillis();
        fscs = new FSCS_ART();
        fscs.testFscsArt_Efficiency(pointNum, bd);
        long n2 = System.currentTimeMillis();
        return (n2 - n1);
     
    }

    public static void testNaiveKDFC(String file, int[][] bd, int pointNum) throws IOException {
        File f1 = new File(file);
        f1.createNewFile();

        PrintWriter out = new PrintWriter(new FileWriter(f1));
        KDFC_ART kdfc;

        double sum = 0;
        for (int i = 0; i < n; i++) {
            long n1 = System.nanoTime();
            kdfc = new KDFC_ART(bd);
            kdfc.testNaiveKDFC_Efficiency(pointNum);
            long n2 = System.nanoTime();
            if (i != 0 && i != 1 && i != 2) {
                sum = sum + (n2 - n1);
                out.println(1.0 * (n2 - n1) / 1e6);
            }

        }

        double num = 1000.0;
        System.out.print(sum / num / 1e6 + "\t");
        out.print(sum / num / 1e6 + "\t");
        out.close();

    }

    public static void testSemiBalKDFC(String file, int[][] bd, int pointNum) throws IOException {
        File f1 = new File(file);
        // if(!f1.exists()){
        f1.createNewFile();
        // }
        PrintWriter out = new PrintWriter(new FileWriter(f1));
        KDFC_ART kdfc;

        double sum = 0;
        for (int i = 0; i < n; i++) {
            long n1 = System.nanoTime();
            kdfc = new KDFC_ART(bd);
            kdfc.testSemiBalKDFC_Efficiency(pointNum);
            long n2 = System.nanoTime();
            if (i != 0 && i != 1 && i != 2) {
                sum = sum + (n2 - n1);
                out.println(1.0 * (n2 - n1) / 1e6);
            }
        }

        double num = 1000.0;
        System.out.print(sum / num / 1e6 + "\t");
        out.print(sum / num / 1e6 + "\t");
        out.close();

    }

    public static long testLimBalKDFC(int[][] bd, int pointNum) throws IOException {
        KDFC_ART kdfc;
        double d = bd.length;
        int[] backNum = new int[pointNum];
        backNum[1] = 1;
        for (int i = 2; i < pointNum; i++) {
            backNum[i] = (int) Math.ceil(1 / 2.0 * Math.pow((d + 1 / d), 2) * (Math.log(i) / Math.log(2)));
        }

        long n1 = System.currentTimeMillis();
        kdfc = new KDFC_ART(bd);
        kdfc.testLimBalKDFC_Efficiency(pointNum, backNum);
        long n2 = System.currentTimeMillis();
        return (n2 - n1);
    }

    public static long testHnswArt(int[][] bd, int pointNum) throws IOException, InterruptedException {
        HNSW_ART hnsw;
        long n1 = System.currentTimeMillis();
        hnsw = new HNSW_ART(10);
        hnsw.testHnswArt_Efficiency(pointNum, bd,2);
        long n2 = System.currentTimeMillis();
        return (n2 - n1);
    }

}
