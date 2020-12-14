package com.github.jelmerk.knn.examples;

import com.github.jelmerk.knn.DistanceFunctions;
import com.github.jelmerk.knn.Index;
import com.github.jelmerk.knn.SearchResult;
import com.github.jelmerk.knn.hnsw.HnswIndex;

import java.util.List;
import java.util.Random;

/**
 * Example application that generates random sample data, inserts them into an
 * hnsw index and makes nearest neighbor query for any random number picked from
 * graph
 * 
 * @link https://github.com/nmslib/hnswlib/blob/master/README.md Note: This is
 *       not standalone example it requires TestCaseSet class to run.
 */
public class NN_10D_Dynamic {
    private static TestCase randomCreateTc(Random rng) {
        int id = rng.nextInt() & Integer.MAX_VALUE; // Only positive integer ID's of random points
        double d1 = rng.nextDouble();
        double d2 = rng.nextDouble();
        double d3 = rng.nextDouble();
        double d4 = rng.nextDouble();
        double d5 = rng.nextDouble();
        double d6 = rng.nextDouble();
        double d7 = rng.nextDouble();
        double d8 = rng.nextDouble();
        double d9 = rng.nextDouble();
        double d10 = rng.nextDouble();
        return new TestCase(id, new double[] { d1, d2, d3, d4, d5, d6, d7, d8, d9, d10 });
    }

    public static void main(String[] args) throws Exception {
        int[] efPool = { 0, 1, 2, 3, 18 };
        for (int givenEf : efPool) {
            int[] kPool = { 1 }; // No. of nearest neighbors to find
            for (int k : kPool) {
                int seed = (int) (Math.random() * Integer.MAX_VALUE);
                Random rng = new Random(seed); // rng = Random Number Generator
                int size = 10000100; // Size of Random TestCases Dataset

                HnswIndex<Integer, double[], TestCase, Double> hnswIndex = HnswIndex
                        .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, size).withM(30).withEfConstruction(18)
                        .withEf(givenEf).build();
                TestCase first_tc = randomCreateTc(rng);
                hnswIndex.add(first_tc);
                Index<Integer, double[], TestCase, Double> groundTruthIndex = hnswIndex.asExactIndex();

                
                double totalAccuracy = 0;
                double totalTime = 0;
                for (int i = 1; i <= 1000000; i++) {
                    TestCase point = randomCreateTc(rng);
                    long start_time = System.currentTimeMillis();
                    List<SearchResult<TestCase, Double>> approximateResults = hnswIndex.findNearest(point.vector(), k);
                    long end_time = System.currentTimeMillis();
                    groundTruthIndex = hnswIndex.asExactIndex();
                    List<SearchResult<TestCase, Double>> groundTruthResults = groundTruthIndex
                            .findNearest(point.vector(), k);
                    int correct = groundTruthResults.stream().mapToInt(r -> approximateResults.contains(r) ? 1 : 0)
                            .sum();
                    long timeTaken = end_time - start_time;
                    totalTime = totalTime + timeTaken;
                    double accuracy = (double) correct / (double) groundTruthResults.size();
                    totalAccuracy = totalAccuracy + accuracy;
                    hnswIndex.add(point);
                }
                System.out.println("ef= " + givenEf + " k= " + k + " TotalAccuracy : " + totalAccuracy + "  TotalTime: "
                        + totalTime);
            }
        }

        java.awt.Toolkit.getDefaultToolkit().beep();

        System.out.println("----------end of program-----------");
    }
}
