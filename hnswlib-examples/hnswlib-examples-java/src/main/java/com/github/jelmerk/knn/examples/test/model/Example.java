package com.github.jelmerk.knn.examples.test.model;

import com.github.jelmerk.knn.examples.auxiliary.model.Point;
import com.github.jelmerk.knn.examples.fault.model.FaultZone;
import com.github.jelmerk.knn.examples.fault.model.FaultZone_Block;
import com.github.jelmerk.knn.examples.hnsw.art.HNSW_ART;
import com.github.jelmerk.knn.hnsw.HnswIndex;


import java.util.Random;

public class Example {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start");

        int[][] bd = {{0, 100}, {0, 100}};

        float area = 0.01f;
        FaultZone fzb = new FaultZone_Block(bd, area);

        long seed = new Random().nextLong();
        // long seed = 1;

        HNSW_ART hnsw = new HNSW_ART(10);
        int testCases = 2;
        HnswIndex<Integer, double[], Point, Double> hnsw2 = hnsw.generateTestCases(bd, fzb, 2, seed, testCases);
        System.out.println("end");
    }
}
