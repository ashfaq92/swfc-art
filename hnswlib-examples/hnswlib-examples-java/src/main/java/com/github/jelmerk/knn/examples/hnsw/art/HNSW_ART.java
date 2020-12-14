package com.github.jelmerk.knn.examples.hnsw.art;

import com.github.jelmerk.knn.DistanceFunctions;
import com.github.jelmerk.knn.examples.auxiliary.model.Point;
import com.github.jelmerk.knn.examples.fault.model.FaultZone;
import com.github.jelmerk.knn.hnsw.HnswIndex;

import java.util.ArrayList;
import java.util.Random;

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

    public int testHnswArt_Effectiveness(int[][] bound, FaultZone fzb, int ef, long seed) throws InterruptedException {
        Random generator = new Random(seed);

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
        Point first_test_case = Point.generateRandP(bound, generator);
        hnswIndex.add(first_test_case);
        selected_set.add(first_test_case);
        // System.out.println("first test case:"+tcP[0].printTestCase());
        generatedNum++;
        // System.out.println("Candidates:");
        while (true){
            for (int i = 0; i < candNum; i++) { // Randomly generate n candidate test cases
                candP[i] = Point.generateRandP(bound, generator);
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

    public HnswIndex<Integer, double[], Point, Double> generateTestCases(int[][] bound, FaultZone fzb, int ef, long seed, int tcNum) throws InterruptedException {
        Random generator = new Random(seed);

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
        Point first_test_case = Point.generateRandP(bound, generator);
        hnswIndex.add(first_test_case);
        selected_set.add(first_test_case);
        // System.out.println("first test case:"+tcP[0].printTestCase());
        generatedNum++;
        // System.out.println("Candidates:");
        int tc = 1;
        while (tc < tcNum){
            for (int i = 0; i < candNum; i++) { // Randomly generate n candidate test cases
                candP[i] = Point.generateRandP(bound, generator);
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
            tc++;
        }
        return hnswIndex;
    }




}
