package com.github.jelmerk.knn.examples;

import com.github.jelmerk.knn.DistanceFunctions;
import com.github.jelmerk.knn.examples.auxiliary.model.Point;
import com.github.jelmerk.knn.hnsw.HnswIndex;

import java.util.Random;

public class HnswgIllustration {
    public static void main(String[] args) throws Exception {
        long seed = 10;
        Random generator = new Random(seed);
        int[][] bound = {{-5000, 5000}, {-5000, 5000}};
        Point first_test_case = Point.generateRandP(bound, generator);
        int d = 2;
        int M = 3 * d;
        int baseSize = 10000;
        int ef = 2;
        int efConst = 3 * (int) Math.ceil(Math.log10(baseSize));
        HnswIndex<Integer, double[], Point, Double> hnswg = HnswIndex
                .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, baseSize).withM(M).withEf(ef)
                .withEfConstruction(efConst).build();

        System.out.println(hnswg);
        hnswg.add(first_test_case);
        System.out.println("end of program");

    }
}
