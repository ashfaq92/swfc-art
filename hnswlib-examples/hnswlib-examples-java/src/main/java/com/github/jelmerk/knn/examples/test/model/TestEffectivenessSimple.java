package com.github.jelmerk.knn.examples.test.model;


import com.github.jelmerk.knn.examples.fault.model.FaultZone;
import com.github.jelmerk.knn.examples.fault.model.FaultZone_Point_Square;
import com.github.jelmerk.knn.examples.hnsw.art.HNSW_ART;

import java.io.IOException;

// import sun.util.locale.provider.FallbackLocaleProviderAdapter;

public class TestEffectivenessSimple {

    public static void main(String[] args) throws IOException, InterruptedException {

        int[][] bd = {{-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000},
                {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}, {-5000, 5000}};
        int simulations = 3000;

        float area = 0.01f;
        long total_f_measure = 0;
        int f_measure;
        for (int i = 1; i <= simulations; i++) {
            f_measure = fixRateTest(area, bd);
            total_f_measure = total_f_measure + f_measure;
            System.out.println(i + "\tf-measure: " + f_measure + "\tmeanF-ratio: " + (total_f_measure / i) * area * 100);
        }
        System.out.println("AvgFRatio: " + ((double) total_f_measure / (double) simulations) * area * 100.0);
        java.awt.Toolkit.getDefaultToolkit().beep();
    }


    private static int fixRateTest(float area, int[][] bd) throws IOException, InterruptedException {
        // FSCS_ART fscs;
        // FaultZone fzb;
        // // fzb = new FaultZone_Block(bd, area);
        // fzb = new FaultZone_Strip(bd, area, 0.9);
        // fscs = new FSCS_ART(10);
        // return fscs.testFscsArt_Effectiveness(bd, fzb);
        HNSW_ART hnsw;
        FaultZone fzb;
        // fzb = new FaultZone_Block(bd, area);
        // fzb = new FaultZone_Strip(bd, area, 0.9);
        fzb = new FaultZone_Point_Square(bd, area);
        hnsw = new HNSW_ART(10);
        return hnsw.testHnswArt_Effectiveness(bd, fzb,1);
    }
}
