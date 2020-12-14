package com.github.jelmerk.knn.examples.test.model;

import com.github.jelmerk.knn.examples.fscs.art.FSCS_ART;
import com.github.jelmerk.knn.examples.hnsw.art.HNSW_ART;

import java.io.IOException;
import java.util.Random;

public class TestDispersion {

    public static void main(String[] args) throws InterruptedException, IOException {
        FSCS_ART fscs = new FSCS_ART(10);
        int[] E = {100, 1000, 10000};


        int[][] D = {{-5000, 5000}};
        for (int e_i : E) {
            double dispersion;
            // dispersion = hnsw.testHnswArt_Dispersion(e_i, D);
            dispersion = fscs.testFscsArt_Dispersion(e_i, D);
            System.out.println("Maximum Dispersion for domain" + D.length + " test cases " + e_i + ":\t" + dispersion);
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
        D = new int[][]{{-5000, 5000}, {-5000, 5000}};
        for (int e_i : E) {
            double dispersion;
            dispersion = fscs.testFscsArt_Dispersion(e_i, D);
            System.out.println("Maximum Dispersion for domain" + D.length + " test cases " + e_i + ":\t" + dispersion);
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

        D = new int[][]{{-5000, 5000}, {-5000, 5000}, {-5000, 5000}};
        for (int e_i : E) {
            double dispersion;
            dispersion = fscs.testFscsArt_Dispersion(e_i, D);
            System.out.println("Maximum Dispersion for domain" + D.length + " test cases " + e_i + ":\t" + dispersion);
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

        D = new int[][]{{-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}};
        for (int e_i : E) {
            double dispersion;
            dispersion = fscs.testFscsArt_Dispersion(e_i, D);
            System.out.println("Maximum Dispersion for domain" + D.length + " test cases " + e_i + ":\t" + dispersion);
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

        D = new int[][]{{-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}};
        for (int e_i : E) {
            double dispersion;
            dispersion = fscs.testFscsArt_Dispersion(e_i, D);
            System.out.println("Maximum Dispersion for domain" + D.length + " test cases " + e_i + ":\t" + dispersion);
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

        D = new int[][]{{-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000},
                {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}};
        for (int e_i : E) {
            double dispersion;
            dispersion = fscs.testFscsArt_Dispersion(e_i, D);
            System.out.println("Maximum Dispersion for domain" + D.length + " test cases " + e_i + ":\t" + dispersion);
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

        D = new int[][]{{-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000},
                {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000},
                {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}};
        for (int e_i : E) {
            double dispersion;
            dispersion = fscs.testFscsArt_Dispersion(e_i, D);
            System.out.println("Maximum Dispersion for domain" + D.length + " test cases " + e_i + ":\t" + dispersion);
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

    }
}
