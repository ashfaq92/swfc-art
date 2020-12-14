package com.github.jelmerk.knn.examples;

import com.github.jelmerk.knn.DistanceFunctions;
import com.github.jelmerk.knn.hnsw.HnswIndex;

public class hnsw_construction {
    public static void main(String[] args) throws Exception {
        System.out.println("Value\t\t\tTime");
        long total_time = 0;
        for (int i = 1; i <= 5000; i++) {
            long start_time = System.currentTimeMillis();
            HnswIndex<Integer, double[], TestCase, Double> hnswIndex = HnswIndex
                    .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, 10000).withM(0).withEfConstruction(2000)
                    .withEf(0).build();
            long end_time = System.currentTimeMillis();
            total_time = total_time + (end_time - start_time);
            System.out.println(i + "\tTime: " + (end_time - start_time) + "\t Mean Time:" + (double) total_time / i);
        }
    }
}
