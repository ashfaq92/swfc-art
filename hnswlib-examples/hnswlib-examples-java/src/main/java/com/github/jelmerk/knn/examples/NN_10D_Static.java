package com.github.jelmerk.knn.examples;

import com.github.jelmerk.knn.DistanceFunctions;
import com.github.jelmerk.knn.Index;
import com.github.jelmerk.knn.SearchResult;
import com.github.jelmerk.knn.hnsw.HnswIndex;

import java.util.ArrayList;
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
public class NN_10D_Static {
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
            int seed = (int) (Math.random() * Integer.MAX_VALUE);
            Random rng = new Random(seed); // rng = Random Number Generator
            ArrayList<TestCase> random_points = new ArrayList<TestCase>();
            int size = 1000000; // Size of Random TestCases Dataset

            for (int i = 0; i < size; i++) {
                TestCase point = randomCreateTc(rng);
                random_points.add(point);
            }

            HnswIndex<Integer, double[], TestCase, Double> hnswIndex = HnswIndex
                    .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, random_points.size()).withM(30)
                    .withEfConstruction(18).withEf(givenEf).build();

            hnswIndex.addAll(random_points, (workDone, max) -> System.out
            .printf("Added %d out of %d test cases to the index.%n", workDone, max));
            // hnswIndex.addAll(random_points);
            // System.out.printf("Creating index with %d test cases\n", hnswIndex.size());
            Index<Integer, double[], TestCase, Double> groundTruthIndex = hnswIndex.asExactIndex();
            // System.out.println("Index created!\n");

            int[] kPool = { 100, 1000 }; // No. of nearest neighbors to find

            for (int k : kPool) {
                double totalAccuracy = 0;
                double totalTime = 0;
                // Get any random point from Dataset to server as query point
                for (int i = 1; i <= 1000; i++) {
                    int index = rng.nextInt(random_points.size());
                    TestCase query_point = random_points.get(index);

                    long start_time = System.currentTimeMillis();
                    List<SearchResult<TestCase, Double>> approximateResults = hnswIndex.findNeighbors(query_point.id(),
                            k);
                    long end_time = System.currentTimeMillis();

                    List<SearchResult<TestCase, Double>> groundTruthResults = groundTruthIndex
                            .findNeighbors(query_point.id(), k);

                    int correct = groundTruthResults.stream().mapToInt(r -> approximateResults.contains(r) ? 1 : 0)
                            .sum();
                    long timeTaken = end_time - start_time;
                    totalTime = totalTime + timeTaken;
                    double accuracy = correct / (double) groundTruthResults.size();
                    totalAccuracy = totalAccuracy + accuracy;
                }

                System.out.printf(
                        "ef= " + givenEf + " k= " + k + " Accuracy : %.4f  Time: " + (totalTime / 3000.0) + "%n",
                        totalAccuracy / 3000.0);

            }

        }

        java.awt.Toolkit.getDefaultToolkit().beep();

        System.out.println("----------end of program-----------");
    }
}
