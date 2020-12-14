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
public class NN_2D_Static {
    private static TestCase randomCreateTc(Random rng) {
        int id = rng.nextInt() & Integer.MAX_VALUE; // Only positive integer ID's of random points
        double x = rng.nextDouble();
        double y = rng.nextDouble();

        return new TestCase(id, new double[] { x, y });
    }

    public static void main(String[] args) throws Exception {
        int[] efPool = { 0, 1, 2, 3, 15 };
        for (int givenEf : efPool) {
            int seed = (int) (Math.random() * Integer.MAX_VALUE);
            Random rng = new Random(seed); // rng = Random Number Generator
            ArrayList<TestCase> random_points = new ArrayList<TestCase>();
            int size = 1000000; // Size of Random TestCases Dataset

            for (int i = 0; i < size; i++) {
                TestCase point = randomCreateTc(rng);
                random_points.add(point);
            }
            System.out.println("Constructing index of Random TestCases. . .");

            HnswIndex<Integer, double[], TestCase, Double> hnswIndex = HnswIndex
                    .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, random_points.size()).withM(6)
                    .withEfConstruction(18).withEf(givenEf).build();

            hnswIndex.addAll(random_points, (workDone, max) -> System.out
                    .printf("Added %d out of %d test cases to the index.%n", workDone, max));
            System.out.printf("Creating index with %d test cases\n", hnswIndex.size());
            Index<Integer, double[], TestCase, Double> groundTruthIndex = hnswIndex.asExactIndex();
            System.out.println("Index created!\n");

            int[] kPool = { 1, 10, 100, 1000, 10000, 100000 }; // No. of nearest neighbors to find

            for (int k : kPool) {
                // Get any random point from Dataset to server as query point
                int index = rng.nextInt(random_points.size());
                TestCase query_point = random_points.get(index);

                long start_time = System.currentTimeMillis();
                List<SearchResult<TestCase, Double>> approximateResults = hnswIndex.findNeighbors(query_point.id(), k);
                long end_time = System.currentTimeMillis();

                List<SearchResult<TestCase, Double>> groundTruthResults = groundTruthIndex
                        .findNeighbors(query_point.id(), k);

                int correct = groundTruthResults.stream().mapToInt(r -> approximateResults.contains(r) ? 1 : 0).sum();
                System.out.printf(
                        "for ef= " + givenEf + " k= " + k + " Accuracy : %.4f  Time: " + (end_time - start_time) + "%n",
                        correct / (double) groundTruthResults.size());

            }

        }

        java.awt.Toolkit.getDefaultToolkit().beep();

        System.out.println("----------end of program-----------");
    }
}
