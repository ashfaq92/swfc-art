package com.github.jelmerk.knn.examples.test.model;


import com.github.jelmerk.knn.examples.fault.model.FaultZone;
import com.github.jelmerk.knn.examples.fault.model.FaultZone_Block;
import com.github.jelmerk.knn.examples.fault.model.FaultZone_Point_Square;
import com.github.jelmerk.knn.examples.fscs.art.FSCS_ART;
import com.github.jelmerk.knn.examples.hnsw.art.HNSW_ART;
import com.github.jelmerk.knn.examples.kdfc.art.KDFC_ART;

import java.io.IOException;
import java.util.Random;

// import sun.util.locale.provider.FallbackLocaleProviderAdapter;

public class TestDiscrepancy {

    public static void main(String[] args) throws InterruptedException {
        int M = 1000;
        double E = 1000;
        Random rng = new Random();
        double discrepancy;

        int[][] D = {{-5000, 5000},{-5000, 5000},{-5000, 5000},{-5000, 5000},{-5000, 5000}};


        double max_discrepancy = -1;
        for (int i = 0; i < M; i++) {
            float area = rng.nextFloat();
            discrepancy = FscsDiscrepancyTest(area, D, E);
            if (discrepancy > max_discrepancy) {
                max_discrepancy = discrepancy;
            }
            // System.out.println(i+ " Discrepancy: " + discrepancy);
        }
        System.out.println("Maximum Discrepancy for dimension " + D.length + "and method FSCS: " + max_discrepancy);


        java.awt.Toolkit.getDefaultToolkit().beep();
    }


    private static double FscsDiscrepancyTest(float area, int[][] bd, double E) {
        FSCS_ART fscs;
        FaultZone fzb;
        fzb = new FaultZone_Block(bd, area);
        fscs = new FSCS_ART(10);
        int E_i = fscs.testFscsArt_Discrepancy(bd, fzb, (int) E);
        double D = fzb.total_domain_area;
        double D_i = fzb.sub_domain_area;
        return Math.abs((Math.abs(E_i) / Math.abs(E)) - (Math.abs(D_i) / Math.abs(D)));
    }

    private static double LimbalKdfcDiscrepancyTest(float area, int[][] bd, double E) {
        KDFC_ART kdfc;
        FaultZone fzb;
        double d = bd.length;
        int[] backNum = new int[(int) E];
        backNum[1] = 1;
        for (int i = 2; i < E; i++) {
            backNum[i] = (int) Math.ceil(1 / 2.0 * Math.pow((d + 1 / d), 2) * (Math.log(i) / Math.log(2)));
        }
        fzb = new FaultZone_Block(bd, area);
        kdfc = new KDFC_ART(bd);
        int E_i = kdfc.testLimBalKDFC_Discrepancy(fzb, (int) E, backNum);
        double D = fzb.total_domain_area;
        double D_i = fzb.sub_domain_area;
        return Math.abs((Math.abs(E_i) / Math.abs(E)) - (Math.abs(D_i) / Math.abs(D)));
    }

    private static double HnswArt_DiscrepancyTest(float area, int[][] bd, double E, int ef) throws InterruptedException {
        HNSW_ART hnsw;
        FaultZone fzb;
        fzb = new FaultZone_Block(bd, area);
        hnsw = new HNSW_ART(10);
        int E_i = hnsw.testHnswArt_Discrepancy((int) E, bd, fzb, ef);
        double D = fzb.total_domain_area;
        double D_i = fzb.sub_domain_area;
        return Math.abs((Math.abs(E_i) / Math.abs(E)) - (Math.abs(D_i) / Math.abs(D)));
    }
}
