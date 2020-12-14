package com.github.jelmerk.knn.examples;

import com.github.jelmerk.knn.DistanceFunctions;
import com.github.jelmerk.knn.SearchResult;
import com.github.jelmerk.knn.hnsw.HnswIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Example application that generates random sample data, inserts them into an hnsw index and makes
 * nearest neighbor query for any random number picked from graph
 *
 * @link https://github.com/nmslib/hnswlib/blob/master/README.md
 * Note: This is not standalone example it requires TestCaseSet class to run.
 */
public class NN_2D_Incremental {
    private static TestCase randomCreateTc(Random rng) {
        int id = rng.nextInt() & Integer.MAX_VALUE;  // Only positive integer ID's of random points
        double x = rng.nextDouble();
        double y = rng.nextDouble();

        return new TestCase(id, new double[]{x, y});
    }

    public static void main(String[] args) throws Exception {
        int seed = (int) (Math.random() * Integer.MAX_VALUE);
        Random rng = new Random(seed);  // rng = Random Number Generator
        ArrayList<TestCase> executed_set = new ArrayList<TestCase>();
        int size = 510000;  // Size of Random TestCases Dataset
        TestCase first_tc = randomCreateTc(rng);
        executed_set.add(first_tc);

        System.out.println("Constructing initial index. . .");

        HnswIndex<Integer, double[], TestCase, Double> hnswIndex = HnswIndex
                .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, size)
                .withM(16)
                .withEf(200)
                .withEfConstruction(200)
                .build();

        hnswIndex.addAll(executed_set, (workDone, max) -> System.out.printf("Added %d out of %d test cases to the index.%n", workDone, max));
        System.out.printf("Creating index with %d test cases\n", hnswIndex.size());
        // Index<Integer, double[], TestCase, Double> groundTruthIndex = hnswIndex.asExactIndex();
        System.out.println("Index created!\n");

        int k = 1;  // No. of nearest neighbors to find
        // Get any random point from Dataset to server as query point
        int iterations = 500000;
        long start = System.currentTimeMillis();
        for (int c = 0; c < iterations; c++) {
            TestCase query_point = randomCreateTc(rng);
            hnswIndex.add(query_point);
            executed_set.add(query_point);
            List<SearchResult<TestCase, Double>> approximateResults = hnswIndex.findNeighbors(query_point.id(), k);
            // List<SearchResult<TestCase, Double>> groundTruthResults = groundTruthIndex.findNeighbors(query_point.id(), k);
            System.out.print(c + "\tq,ANN,realNN:\t" + "(" + query_point.vector()[0] + "," + query_point.vector()[1] + "),");
            for (SearchResult<TestCase, Double> result : approximateResults) {
                System.out.println("(" + result.item().vector()[0] + "," + result.item().vector()[1] + ")");
            }
            // for (SearchResult<TestCase, Double> result : groundTruthResults) {
            //     System.out.println("(" + result.item().vector()[0] + "," + result.item().vector()[1] + ")");
            // }
        }
        long end = System.currentTimeMillis();
        long duration = end - start;


        System.out.println("Printing All Test Cases...");
        for (TestCase pt : executed_set) {
            System.out.print("(" + pt.vector()[0] + "," + pt.vector()[1] + "),");
        }

        /*
         *You can copy these printed points and query set to graphing app i.e (www.desmos.com/calculator)
         * to visualize random point and nearest neighbor
         */
        System.out.println("\nTime Taken:" + duration);
        System.out.println("end of program");

    }
}
