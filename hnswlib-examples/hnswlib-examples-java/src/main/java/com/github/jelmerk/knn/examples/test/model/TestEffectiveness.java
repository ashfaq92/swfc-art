package com.github.jelmerk.knn.examples.test.model;

import com.github.jelmerk.knn.examples.fault.model.FaultZone;
import com.github.jelmerk.knn.examples.fault.model.FaultZone_Block;
import com.github.jelmerk.knn.examples.fault.model.FaultZone_Point_Square;
import com.github.jelmerk.knn.examples.fault.model.FaultZone_Strip;
import com.github.jelmerk.knn.examples.fscs.art.FSCS_ART;
import com.github.jelmerk.knn.examples.hnsw.art.HNSW_ART;
import com.github.jelmerk.knn.examples.kdfc.art.KDFC_ART;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class TestEffectiveness {

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

        ArrayList<Float> area = new ArrayList<>();
        ArrayList<int[][]> domains = new ArrayList<>();

        area.add(0.01f);
        area.add(0.005f);
        area.add(0.002f);
        area.add(0.001f);
        area.add(0.0005f);
        area.add(0.0002f);
        area.add(0.0001f);


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
            System.out.println("\n------DIMENSION:\t" + bd.length + "D--------:");
            for (float a : area) {
                System.out.println("Dimensionality:" + bd.length + "\t" + "theta:" + a);
                System.out.println("FSCS-ART | Limbal-KDFC | SWFC-ART");
                String s1 = bd.length + "d-Block-" + a + ".txt";
                String s2 = bd.length + "d-Strip-" + a + ".txt";
                String s3 = bd.length + "d-Point-" + a + ".txt";
                fixRateTest("E:/temp/" + s1, a, bd, 1);
                fixRateTest("E:/temp/" + s2, a, bd, 2);
                fixRateTest("E:/temp/" + s3, a, bd, 3);
            }

        }


    }

    public static long count_lines_text_java8(String file) throws IOException, URISyntaxException {

        long numberOfLines;
        try (Stream<String> s = Files.lines(Paths.get(file),
                Charset.defaultCharset())) {

            numberOfLines = s.count();

        } catch (IOException e) {
            throw e;
        }
        return numberOfLines;
    }


    public static void fixRateTest(String file, float area, int[][] bd, int t) throws IOException, InterruptedException, URISyntaxException {
        FSCS_ART fscs;
        KDFC_ART kdfc;
        HNSW_ART hnsw;
        FaultZone fzb;
        File f1 = new File(file);
        f1.createNewFile();
        PrintWriter out = new PrintWriter(new FileWriter(f1));


        int[] backNum = new int[100 * (int) (1 / area)];
        backNum[0] = 1;
        backNum[1] = 1;
        double d = bd.length;
        for (int i = 2; i < backNum.length; i++) {
            backNum[i] = (int) Math.ceil(1 / 2.0 * Math.pow((d + 1 / d), 2) * (Math.log(i) / Math.log(2)));
        }


        double num1 = 0, num2 = 0, num3 = 0;

        for (int i = 0; i < 1000; i++) {
            if (t == 1) {
                fzb = new FaultZone_Block(bd, area);
            } else if (t == 2) {
                fzb = new FaultZone_Strip(bd, area, 0.9);
            } else {
                fzb = new FaultZone_Point_Square(bd, area);
            }
            for (int j = 0; j < 10; j++) {

                fscs = new FSCS_ART(10);
                int num = fscs.testFscsArt_Effectiveness(bd, fzb);
                num1 = num1 + num;
                out.print(num + "\t");

                kdfc = new KDFC_ART(bd);
                kdfc.testLimBalKDFC_Effectiveness(fzb, backNum);
                num2 = num2 + kdfc.size;
                out.print(kdfc.size + "\t");

                hnsw = new HNSW_ART(10);
                int hnsw2_num = hnsw.testHnswArt_Effectiveness(bd, fzb, 2);
                num3 = num3 + hnsw2_num;
                out.print(hnsw2_num + "\t");
                out.println();
                out.flush();
            }
        }

        double n = 5000.0;
        double s = 1 / area / 100;
        out.println(new DecimalFormat("0.0000").format(num1 / n / s) + "\t"
                + new DecimalFormat("0.0000").format(num2 / n / s) + "\t"
                + new DecimalFormat("0.0000").format(num3 / n / s));
        System.out.println(new DecimalFormat("0.0000").format(num1 / n / s) + "\t"
                + new DecimalFormat("0.0000").format(num2 / n / s) + "\t"
                + new DecimalFormat("0.0000").format(num3 / n / s));
        out.close();

    }

}
