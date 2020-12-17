package com.github.jelmerk.knn.examples.test.model;

import com.github.jelmerk.knn.examples.fscs.art.FSCS_ART;
import com.github.jelmerk.knn.examples.hnsw.art.HNSW_ART;
import com.github.jelmerk.knn.examples.kdfc.art.KDFC_ART;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TestEfficiency {

    final static int n = 1003;

    public static void main(String[] args) throws IOException, InterruptedException {

        ArrayList<Integer> num = new ArrayList<>();
        ArrayList<int[][]> domains = new ArrayList<>();

        num.add(100);
        num.add(200);
        num.add(500);
        num.add(1000);
        num.add(2000);
        num.add(5000);
        num.add(10000);
        num.add(15000);
        num.add(20000);

        int[][] bd2 = {{-5000, 5000}, {-5000, 5000}};
        int[][] bd3 = {{-5000, 5000}, {-5000, 5000}, {-5000, 5000}};
        int[][] bd4 = {{-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}};
        int[][] bd5 = {{-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}};
        int[][] bd10 = {{-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000},
                {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}};
        int[][] bd15 = {{-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000},
                {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000},
                {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}};


        domains.add(bd2);
        domains.add(bd3);
        domains.add(bd4);
        domains.add(bd5);
        domains.add(bd10);
        domains.add(bd15);

        for (int[][] bd : domains) {
            System.out.println("\n-------DIMENSION: " + bd.length + "\t--------: ");
            for (int n : num) {
                // change file paths accordingly
                System.out.println("\n" + bd.length + "D\tTeseCases: " + n);
                String s1 = "E:/temp/" + bd.length + "d-FscsART-" + n + ".txt";
                String s2 = "E:/temp/" + bd.length + "d-LimBalKDFC-" + n + ".txt";
                String s3 = "E:/temp/" + bd.length + "d-HnswART2-" + n + ".txt";

                testFscsART(s1, bd, n);
                testLimBalKDFC(s2, bd, n);
                testSwfcART(s3, bd, n, 2);
            }
        }
    }

    public static void testFscsART(String file, int[][] bd, int pointNum) throws IOException {
        File f1 = new File(file);
        f1.createNewFile();
        PrintWriter out = new PrintWriter(new FileWriter(f1));

        FSCS_ART fscs;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            long n1 = System.nanoTime();
            fscs = new FSCS_ART();
            fscs.testFscsArt_Efficiency(pointNum, bd);
            long n2 = System.nanoTime();
            if (i != 0 && i != 1 && i != 2) {
                sum = sum + (n2 - n1);
                out.println(1.0 * (n2 - n1) / 1e6);
            }
        }

        double num = 1000.0;
        System.out.println("FSCS-ART:\t" + sum / num / 1e6 + "\t");
        out.print(sum / num / 1e6 + "\t");
        out.close();

    }

    public static void testLimBalKDFC(String file, int[][] bd, int pointNum) throws IOException {
        File f1 = new File(file);
        // if(!f1.exists()){
        f1.createNewFile();
        // }
        PrintWriter out = new PrintWriter(new FileWriter(f1));
        KDFC_ART kdfc;

        double d = bd.length;
        int[] backNum = new int[pointNum];
        backNum[1] = 1;
        for (int i = 2; i < pointNum; i++) {
            backNum[i] = (int) Math.ceil(1 / 2.0 * Math.pow((d + 1 / d), 2) * (Math.log(i) / Math.log(2)));
        }

        double sum = 0;

        for (int i = 0; i < n; i++) {
            long n1 = System.nanoTime();
            kdfc = new KDFC_ART(bd);
            kdfc.testLimBalKDFC_Efficiency(pointNum, backNum);
            long n2 = System.nanoTime();
            if (i != 0 && i != 1 && i != 2) {
                sum = sum + n2 - n1;
                out.println(1.0 * (n2 - n1) / 1e6);
                // System.out.println(1.0 * (n2 - n1) / 1e6);
            }
        }

        double num = 1000.0;
        System.out.println("LimBal-KDFC:\t" + sum / num / 1e6 + "\t");
        out.print(sum / num / 1e6 + "\t");
        out.close();

    }


    public static void testSwfcART(String file, int[][] bd, int pointNum, int ef) throws IOException, InterruptedException {
        File f1 = new File(file);
        f1.createNewFile();
        PrintWriter out = new PrintWriter(new FileWriter(f1));

        HNSW_ART hnsw;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            long n1 = System.nanoTime();
            hnsw = new HNSW_ART(10);
            hnsw.testHnswArt_Efficiency(pointNum, bd, ef);
            long n2 = System.nanoTime();
            if (i != 0 && i != 1 && i != 2) {
                sum = sum + (n2 - n1);
                out.println(1.0 * (n2 - n1) / 1e6);
            }
        }

        double num = 1000.0;
        System.out.print("SWFC-ART\t" + sum / num / 1e6 + "\t");
        out.print(sum / num / 1e6 + "\t");
        out.close();

    }

}
