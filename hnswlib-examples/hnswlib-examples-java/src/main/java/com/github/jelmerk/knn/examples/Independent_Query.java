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
public class Independent_Query {
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
        int size = 500;  // Size of Random TestCases Dataset
        for (int i = 0; i < size; i++) {
            TestCase point = randomCreateTc(rng);
            executed_set.add(point);
        }
        System.out.println("Constructing initial index. . .");

        HnswIndex<Integer, double[], TestCase, Double> hnswIndex = HnswIndex
                .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, 1900)
                .withM(16)
                .withEf(200)
                .withEfConstruction(200)
                .build();

        hnswIndex.addAll(executed_set, (workDone, max) -> System.out.printf("Added %d out of %d test cases to the index.%n", workDone, max));
        System.out.printf("Creating index with %d test cases\n", hnswIndex.size());
        System.out.println("Index created!\n");

        TestCase query_point = randomCreateTc(rng);
        System.out.println("Query Point: "+query_point.printTestCase());
        // public List<SearchResult<TItem, TDistance>> findNearest(TVector destination, int k) {

        // List<SearchResult<TestCase, Double>> approximateResults = hnswIndex.findNeighbors(query_point.id(), 1);
        List<SearchResult<TestCase, Double>> approximateResults = hnswIndex.findNearest(query_point.vector(),1);

        System.out.println(approximateResults.toString());
        // hnswIndex.add(query_point);
        // executed_set.add(query_point);

        System.out.println("\nPrinting All Test Cases...");
        for (TestCase pt : executed_set) {
            System.out.print("(" + pt.vector()[0] + "," + pt.vector()[1] + "),");
        }

        /*
         *You can copy these printed points and query set to graphing app i.e (www.desmos.com/calculator)
         * to visualize random point and nearest neighbor
         */
        System.out.println("end of program");

    }
}
// (0.022071773058109256, 0.3085351596638196)