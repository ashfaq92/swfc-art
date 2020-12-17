package com.github.jelmerk.knn.examples.hnsw.art;

import com.github.jelmerk.knn.DistanceFunctions;
import com.github.jelmerk.knn.examples.auxiliary.model.Point;
import com.github.jelmerk.knn.examples.fault.model.FaultZone;
import com.github.jelmerk.knn.hnsw.HnswIndex;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class HNSW_ART {
    public int candNum = 10; //  Number of candidate test cases
    public int[][] inputDomain;

    public HNSW_ART() {
        candNum = 10;
    }

    public HNSW_ART(int n) {
        candNum = n;
    }

    public int findFarestCandidate(Point[] candP, HnswIndex<Integer, double[], Point, Double> hnswIndex) {
        // Identify the furthest candidate test cases
        double[] dist = new double[candP.length];  // distance array of candidates
        double tempDist, farestDist = 0;
        int farestIndex = 0;
        double bestDistace = -1.0;

        for (int i = 0; i < candP.length; i++) {
            dist[i] = hnswIndex.findNearest(candP[i].vector(), 1).get(0).distance();
            if (dist[i] > bestDistace) {
                bestDistace = dist[i];
                farestIndex = i;
            }
        }
        return farestIndex;
    }

    public int testHnswArt_Effectiveness(int[][] bound, FaultZone fzb, int ef) throws InterruptedException {
        //Failure detection effect test
        int generatedNum = 0; // Number of test cases generated (f-measure)
        // int maxTry = (int) (30 / fzb.theta); // Stop if more than maxtry times have not hit the fault area
        int selected;
        // Point[] tcP = new Point[maxTry + 2]; // set of test cases (selected_set)
        Point[] candP = new Point[candNum]; // Candidate test Case set (candidate_set)
        ArrayList<Point> selected_set = new ArrayList<Point>();

        // HNSW GRAPH INITIALIZATION
        int dimension = bound.length;
        int givenM = dimension * 3;
        int graphSize = 10000;
        int efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
        HnswIndex<Integer, double[], Point, Double> hnswIndex = HnswIndex
                .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM).withEf(ef)
                .withEfConstruction(efConst).build();
        Point first_test_case = Point.generateRandP(bound);
        hnswIndex.add(first_test_case);
        selected_set.add(first_test_case);
        // System.out.println("first test case:"+tcP[0].printTestCase());
        generatedNum++;
        // System.out.println("Candidates:");
        while (true){
            for (int i = 0; i < candNum; i++) { // Randomly generate n candidate test cases
                candP[i] = Point.generateRandP(bound);
                // System.out.print(candP[i].printTestCase()+",");
            }
            if (selected_set.size() >= hnswIndex.getMaxItemCount()) {
                // System.out.println("doubling");
                graphSize = graphSize * 2;
                efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
                hnswIndex = HnswIndex.newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM)
                        .withEf(ef).withEfConstruction(efConst).build();
                hnswIndex.addAll(selected_set);
            }
            selected = findFarestCandidate(candP, hnswIndex);
            // System.out.println(selected);
            selected_set.add(candP[selected]);
            hnswIndex.add(candP[selected]);
            // System.out.println("\nfarthest"+candP[selected].printTestCase());
            generatedNum++;
            // System.out.println(generatedNum);
            if (fzb.findTarget(selected_set.get(generatedNum - 1))) {
                break;
            }
        }
        return generatedNum;
    }

    public void testHnswArt_Efficiency(int num, int[][] bound, int ef) throws InterruptedException {
        //  Computational efficiency testing
        int selected;
        Point[] candP = new Point[candNum];
        ArrayList<Point> selected_set = new ArrayList<Point>();
        // HNSW GRAPH INITIALIZATION
        int dimension = bound.length;
        int givenM = dimension * 3;
        // int graphSize = num + 2;
        int graphSize = 10000;
        int efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
        HnswIndex<Integer, double[], Point, Double> hnswIndex = HnswIndex
                .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM).withEf(ef)
                .withEfConstruction(efConst).build();
        Point first_test_case = Point.generateRandP(bound);
        hnswIndex.add(first_test_case);
        selected_set.add(first_test_case);
        for (int j = 1; j < num; j++) { // Randomly generate n candidate test cases
            for (int i = 0; i < candNum; i++) {
                candP[i] = Point.generateRandP(bound);
            }
            if (selected_set.size() >= hnswIndex.getMaxItemCount()) {
                // System.out.println("doubling");
                graphSize = graphSize * 2;
                efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
                hnswIndex = HnswIndex.newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM)
                        .withEf(ef).withEfConstruction(efConst).build();
                hnswIndex.addAll(selected_set);
            }
            selected = findFarestCandidate(candP, hnswIndex);
            selected_set.add(candP[selected]);
            hnswIndex.add(candP[selected]);
        }
    }

    public int testHnswArt_Discrepancy(int E, int[][] bound, FaultZone fzb, int ef) throws InterruptedException {
        int counter = 0;
        //  Computational efficiency testing
        int selected;
        // Point[] tcP = new Point[num];
        Point[] candP = new Point[candNum];
        // tcP[0] = Point.generateRandP(bound);
        ArrayList<Point> selected_set = new ArrayList<Point>();
        // HNSW GRAPH INITIALIZATION
        int dimension = bound.length;
        int givenM = dimension * 3;
        // int graphSize = num + 2;
        int graphSize = 10000;
        int efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
        HnswIndex<Integer, double[], Point, Double> hnswIndex = HnswIndex
                .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM).withEf(ef)
                .withEfConstruction(efConst).build();
        Point first_test_case = Point.generateRandP(bound);
        hnswIndex.add(first_test_case);
        selected_set.add(first_test_case);
        for (int j = 1; j < E; j++) { // Randomly generate n candidate test cases
            for (int i = 0; i < candNum; i++) {
                candP[i] = Point.generateRandP(bound);
            }
            if (selected_set.size() >= hnswIndex.getMaxItemCount()) {
                // System.out.println("doubling");
                graphSize = graphSize * 2;
                efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
                hnswIndex = HnswIndex.newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM)
                        .withEf(ef).withEfConstruction(efConst).build();
                hnswIndex.addAll(selected_set);
            }
            selected = findFarestCandidate(candP, hnswIndex);
            selected_set.add(candP[selected]);
            hnswIndex.add(candP[selected]);
            if (fzb.findTarget(candP[selected])) {
                counter++;
            }
        }
        return counter;
    }



    public double testHnswArt_Dispersion(int num, int[][] bound) throws InterruptedException {
        //  Computational efficiency testing
        int selected;
        Point[] candP = new Point[candNum];
        ArrayList<Point> selected_set = new ArrayList<Point>();
        // HNSW GRAPH INITIALIZATION
        int dimension = bound.length;
        int givenM = dimension * 3;
        // int graphSize = num + 2;
        int graphSize = 10000;
        int efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
        int givenEf = 1;
        HnswIndex<Integer, double[], Point, Double> hnswIndex = HnswIndex
                .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM).withEf(givenEf)
                .withEfConstruction(efConst).build();
        Point first_test_case = Point.generateRandP(bound);
        hnswIndex.add(first_test_case);
        selected_set.add(first_test_case);
        for (int j = 1; j < num; j++) { // Randomly generate n candidate test cases
            for (int i = 0; i < candNum; i++) {
                candP[i] = Point.generateRandP(bound);
            }
            if (selected_set.size() >= hnswIndex.getMaxItemCount()) {
                // System.out.println("doubling");
                graphSize = graphSize * 2;
                efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
                hnswIndex = HnswIndex.newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM)
                        .withEf(givenEf).withEfConstruction(efConst).build();
                hnswIndex.addAll(selected_set);
            }
            selected = findFarestCandidate(candP, hnswIndex);
            selected_set.add(candP[selected]);
            hnswIndex.add(candP[selected]);
        }
        double distance;
        double max_distance = -1;
        for (Point tc : selected_set) {
            // System.out.println(tc.toString());
            distance = hnswIndex.findNeighbors(tc.id(), 1).get(0).distance();
            // System.out.println(i+" Distance: "+distance);
            if (distance > max_distance) {
                max_distance = distance;
            }
        }
        return max_distance;
    }


}
