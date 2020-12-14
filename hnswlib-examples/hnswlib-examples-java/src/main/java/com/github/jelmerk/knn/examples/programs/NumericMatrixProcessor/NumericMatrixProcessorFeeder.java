package com.github.jelmerk.knn.examples.programs.NumericMatrixProcessor;

import com.github.jelmerk.knn.DistanceFunctions;
import com.github.jelmerk.knn.examples.auxiliary.model.Point;
import com.github.jelmerk.knn.examples.fscs.art.FSCS_ART;
import com.github.jelmerk.knn.examples.hnsw.art.HNSW_ART;
import com.github.jelmerk.knn.examples.kdfc.art.KDFC_ART;
import com.github.jelmerk.knn.hnsw.HnswIndex;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NumericMatrixProcessorFeeder {
    public static double[][] bd = {{-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}};
    private static double theta = 0.00020123;

    private static boolean addMatricesTest(Point tc) {
        double[][] originalOutput = MainNew.addMatrices(tc);
        double[][] modifiedOutput = MainNew_m.addMatrices(tc);
        return Arrays.deepEquals(originalOutput, modifiedOutput);
    }

    private static boolean multiplyMatricesConsonantTest(Point tc) {
        double[][] originalOutput = MainNew.multiplyMatricesConsonant(tc);
        double[][] modifiedOutput = MainNew_m.multiplyMatricesConsonant(tc);
        return Arrays.deepEquals(originalOutput, modifiedOutput);
    }

    private static boolean multiplyMatricesTest(Point tc) {
        double[][] originalOutput = MainNew.multiplyMatrices(tc);
        double[][] modifiedOutput = MainNew_m.multiplyMatrices(tc);
        return Arrays.deepEquals(originalOutput, modifiedOutput);
    }

    private static boolean transposeMatrixTest(Point tc) {
        Random rng = new Random();
        int choice = rng.nextInt(5);
        double[][] originalOutput = MainNew.transposeMatrix(tc, choice);
        double[][] modifiedOutput = MainNew_m.transposeMatrix(tc, choice);
        return Arrays.deepEquals(originalOutput, modifiedOutput);
    }

    private static boolean determinantOfMatrixTest(Point tc) {
        double originalOutput = MainNew.determinantOfMatrix(tc);
        double modifiedOutput = MainNew_m.determinantOfMatrix(tc);
        return originalOutput == modifiedOutput;
    }

    private static boolean inverseOfMatrixTest(Point tc) {
        double[][] originalOutput = MainNew.inverseOfMatrix(tc);
        double[][] modifiedOutput = MainNew_m.inverseOfMatrix(tc);
        return Arrays.deepEquals(originalOutput, modifiedOutput);
    }

    private static boolean runAllTests(Point a) {
        boolean sameOutput = true;
        boolean revealFailure = false;

        sameOutput = addMatricesTest(a);
        if (!sameOutput) {
            revealFailure = true;
        }
        sameOutput = multiplyMatricesConsonantTest(a);
        if (!sameOutput) {
            revealFailure = true;
        }
        sameOutput = multiplyMatricesTest(a);
        if (!sameOutput) {
            revealFailure = true;
        }
        sameOutput = transposeMatrixTest(a);
        if (!sameOutput) {
            revealFailure = true;
        }
        sameOutput = determinantOfMatrixTest(a);
        if (!sameOutput) {
            revealFailure = true;
        }
        sameOutput = inverseOfMatrixTest(a);
        if (!sameOutput) {
            revealFailure = true;
        }
        return revealFailure;
    }

    private static double getFailureRate() {
        Random generator = new Random();
        double totalFMeasure = 0;
        int iterations = 10000;

        for (int i = 0; i <= iterations; i++) {
            long fMeasure = 0;
            while (true) {
                Point tc = Point.generateRandP(bd, generator);
                // System.out.println(tc.toString());
                fMeasure++;

                boolean revealFailure = runAllTests(tc);
                if (revealFailure) {
                    System.out.print(fMeasure + "\t");
                    totalFMeasure += fMeasure;
                    break;
                }
            }
        }
        System.out.println(1 / (totalFMeasure / iterations));
        return 1 / (totalFMeasure / iterations);
    }

    private Random getRandomGenerator(long mySeed) {
        Random generator;
        if (mySeed == 0) {
            generator = new Random();
        } else {
            generator = new Random(mySeed);
        }
        return generator;
    }

    private long[] randomTesting(long mySeed, BufferedWriter writer) throws IOException {
        Random generator = getRandomGenerator(mySeed);
        long fMeasure = 0;
        long tcGenTime = 0;
        long execTime = 0;
        while (true) {
            long start = System.nanoTime();
            // double tc = min[0] + (max[0] - min[0]) * generator.nextDouble();
            Point tc = Point.generateRandP(bd, generator);
            tcGenTime = tcGenTime + (System.nanoTime() - start);

            fMeasure++;
            start = System.nanoTime();
            boolean revealFailure = runAllTests(tc);
            execTime = execTime + (System.nanoTime() - start);

            if (revealFailure) {
                writer.write(fMeasure + "\t" + tcGenTime + "\t" + execTime + "\n");
                return new long[]{fMeasure, tcGenTime, execTime};
            }
        }
    }

    public long[] fscsTesting(long mySeed, BufferedWriter writer) throws IOException {
        Random generator = getRandomGenerator(mySeed);
        long fMeasure = 0;
        long tcGenTime = 0;
        long execTime = 0;

        FSCS_ART fscs = new FSCS_ART(10);
        int maxTry = (int) (30 / theta);
        int selected;
        Point[] tcP = new Point[maxTry + 2];
        Point[] candP = new Point[fscs.candNum];

        tcP[0] = Point.generateRandP(bd, generator);  // first test case
        fMeasure++; // increasing f-measure without testing first test case

        while (true) {
            long start = System.nanoTime();
            for (int i = 0; i < fscs.candNum; i++) {
                candP[i] = Point.generateRandP(bd, generator);
            }
            selected = fscs.findFarestCandidate(tcP, (int) fMeasure, candP);
            tcGenTime = tcGenTime + (System.nanoTime() - start);

            tcP[(int) fMeasure] = candP[selected];

            fMeasure++;

            start = System.nanoTime();
            boolean revealFailure = runAllTests(tcP[(int) (fMeasure - 1)]);
            execTime = execTime + (System.nanoTime() - start);
            if (revealFailure) {
                writer.write(fMeasure + "\t" + tcGenTime + "\t" + execTime + "\n");
                return new long[]{fMeasure, tcGenTime, execTime};
            }
        }
    }

    public long[] limBalKdfcTesting(long mySeed, BufferedWriter writer) throws IOException {
        Random generator = getRandomGenerator(mySeed);
        long tcGenTime = 0;
        long execTime = 0;
        long fMeasure = 0;

        // initialization phase
        int[] backNum = new int[100 * (int) (1 / theta)];
        backNum[0] = 1;
        backNum[1] = 1;
        double d = bd.length;
        for (int i = 2; i < backNum.length; i++) {
            backNum[i] = (int) Math.ceil(1 / 2.0 * Math.pow((d + 1 / d), 2) * (Math.log(i) / Math.log(2)));
        }
        Point p = Point.generateRandP(bd, generator);
        KDFC_ART kdfc = new KDFC_ART(bd);
        kdfc.insertPointByStrategy(p);
        Point finalCase;
        ArrayList<Point> canD;

        while (true) {
            long start = System.nanoTime();
            canD = new ArrayList<>();
            for (int i = 0; i < kdfc.candidateNum; i++) {
                canD.add(Point.generateRandP(kdfc.inputDomain, generator));
            }
            finalCase = canD.get(0);
            int back = backNum[kdfc.size];
            double distance = kdfc.getMinDisByBacktracking(finalCase, back);
            for (int c = 1; c < kdfc.candidateNum; c++) {
                d = kdfc.getMinDisByBacktracking(canD.get(c), back); // get minimum distance
                if (distance < d) { // get the candidate for smallest distance
                    distance = d;
                    finalCase = canD.get(c);
                }
            }

            kdfc.insertPointByStrategy(finalCase);
            tcGenTime = tcGenTime + (System.nanoTime() - start);


            start = System.nanoTime();
            boolean revealFailure = runAllTests(finalCase);
            execTime = execTime + (System.nanoTime() - start);
            if (revealFailure) {
                fMeasure = kdfc.size;
                writer.write(fMeasure + "\t" + tcGenTime + "\t" + execTime + "\n");
                return new long[]{fMeasure, tcGenTime, execTime};
            }
        }
    }

    public long[] hnswTesting(int ef, long mySeed, BufferedWriter writer) throws InterruptedException, IOException {
        Random generator = getRandomGenerator(mySeed);
        HNSW_ART myHnsw = new HNSW_ART(10);
        int selected;
        Point[] candP = new Point[myHnsw.candNum]; // Candidate test Case set (candidate_set)
        ArrayList<Point> selected_set = new ArrayList<>();

        long fMeasure = 0;
        long tcGenTime = 0;
        long execTime = 0;

        // HNSW GRAPH INITIALIZATION
        int givenM = bd.length * 3;
        int graphSize = 10000;
        int efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
        HnswIndex<Integer, double[], Point, Double> hnswIndex = HnswIndex
                .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM).withEf(ef)
                .withEfConstruction(efConst).build();
        Point first_test_case = Point.generateRandP(bd, generator);
        hnswIndex.add(first_test_case);
        selected_set.add(first_test_case);

        while (true) {
            long start = System.nanoTime();
            for (int i = 0; i < myHnsw.candNum; i++) { // Randomly generate n candidate test cases
                candP[i] = Point.generateRandP(bd, generator);
            }
            selected = myHnsw.findFarestCandidate(candP, hnswIndex);
            selected_set.add(candP[selected]);
            hnswIndex.add(candP[selected]);
            tcGenTime = tcGenTime + (System.nanoTime() - start);

            start = System.nanoTime();
            boolean revealFailure = runAllTests(selected_set.get(selected_set.size() - 1));
            execTime = execTime + (System.nanoTime() - start);
            if (revealFailure) {
                fMeasure = selected_set.size();
                writer.write(fMeasure + "\t" + tcGenTime + "\t" + execTime + "\n");
                return new long[]{fMeasure, tcGenTime, execTime};
            }
            // Make new HNSW Graph with double size of initial provided size and add all previous test cases
            if (selected_set.size() >= hnswIndex.getMaxItemCount()) {
                // System.out.println("doubling");
                graphSize = graphSize * 2;
                efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
                hnswIndex = HnswIndex.newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM)
                        .withEf(ef).withEfConstruction(efConst).build();
                hnswIndex.addAll(selected_set);
            }
        }
    }

    private void SimRunner(int sim, boolean withSameSeed) throws InterruptedException, IOException {
        BufferedWriter RTwriter = new BufferedWriter(new FileWriter("NumericMatrixProcessorRT.txt"));
        BufferedWriter FSCSwriter = new BufferedWriter(new FileWriter("NumericMatrixProcessorFSCS.txt"));
        BufferedWriter LimBalKdfcwriter = new BufferedWriter(new FileWriter("NumericMatrixProcessorLimBalKdfc.txt"));
        BufferedWriter HNSWwriter = new BufferedWriter(new FileWriter("NumericMatrixProcessorHNSW.txt"));
        long RT_Fms, RT_tcGenTimes, RT_execTimes, FSCS_Fms, FSCS_tcGenTimes, FSCS_execTimes, limbalKdfc_Fms, limbalKdfc_tcGenTimes, limbalKdfc_execTimes, hnsw_Fms, hnsw_tcGenTimes, hnsw_execTimes;
        RT_Fms = RT_tcGenTimes = RT_execTimes = FSCS_Fms = FSCS_tcGenTimes = FSCS_execTimes = limbalKdfc_Fms = limbalKdfc_tcGenTimes = limbalKdfc_execTimes = hnsw_Fms = hnsw_tcGenTimes = hnsw_execTimes = 0;
        for (int i = 0; i < sim; i++) {
            long mySeed;
            if (withSameSeed) {
                mySeed = new Random().nextLong();
            } else {
                mySeed = 0;
            }
            long[] resp = randomTesting(mySeed, RTwriter);
            System.out.println(i + "\t" + Arrays.toString(resp));
            RT_Fms = RT_Fms + resp[0];
            RT_tcGenTimes = RT_tcGenTimes + resp[1];
            RT_execTimes = RT_execTimes + resp[2];

            resp = fscsTesting(mySeed, FSCSwriter);
            System.out.println(i + "\t" + Arrays.toString(resp));
            FSCS_Fms = FSCS_Fms + resp[0];
            FSCS_tcGenTimes = FSCS_tcGenTimes + resp[1];
            FSCS_execTimes = FSCS_execTimes + resp[2];

            resp = limBalKdfcTesting(mySeed, LimBalKdfcwriter);
            System.out.println(i + "\t" + Arrays.toString(resp));
            limbalKdfc_Fms = limbalKdfc_Fms + resp[0];
            limbalKdfc_tcGenTimes = limbalKdfc_tcGenTimes + resp[1];
            limbalKdfc_execTimes = limbalKdfc_execTimes + resp[2];

            resp = hnswTesting(2, mySeed, HNSWwriter);
            System.out.println(i + "\t" + Arrays.toString(resp));
            hnsw_Fms = hnsw_Fms + resp[0];
            hnsw_tcGenTimes = hnsw_tcGenTimes + resp[1];
            hnsw_execTimes = hnsw_execTimes + resp[2];
        }

        System.out.println("F_MEASURE \t tcGenTime \t execTime");
        System.out.println("RT:\t" + (RT_Fms / sim) + "\t" + (RT_tcGenTimes / sim) + "\t" + (RT_execTimes / sim));
        System.out.println("FSCS:\t:" + (FSCS_Fms / sim) + "\t" + (FSCS_tcGenTimes / sim) + "\t" + (FSCS_execTimes / sim));
        System.out.println("LimBalKdfc:\t" + (limbalKdfc_Fms / sim) + "\t" + (limbalKdfc_tcGenTimes / sim) + "\t" + (limbalKdfc_execTimes / sim));
        System.out.println("HNSW:\t" + (hnsw_Fms / sim) + "\t" + (hnsw_tcGenTimes / sim) + "\t" + (hnsw_execTimes / sim));

        RTwriter.write((RT_Fms / sim) + "\t" + (RT_tcGenTimes / sim) + "\t" + (RT_execTimes / sim));
        FSCSwriter.write((FSCS_Fms / sim) + "\t" + (FSCS_tcGenTimes / sim) + "\t" + (FSCS_execTimes / sim));
        LimBalKdfcwriter.write((limbalKdfc_Fms / sim) + "\t" + (limbalKdfc_tcGenTimes / sim) + "\t" + (limbalKdfc_execTimes / sim));
        HNSWwriter.write((hnsw_Fms / sim) + "\t" + (hnsw_tcGenTimes / sim) + "\t" + (hnsw_execTimes / sim));
        RTwriter.close();
        FSCSwriter.close();
        LimBalKdfcwriter.close();
        HNSWwriter.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("---START---");
        int simulations = 5000;
        NumericMatrixProcessorFeeder myNumericMatrixProcessorFeeder = new NumericMatrixProcessorFeeder();
        boolean seedOpt = false;
        myNumericMatrixProcessorFeeder.SimRunner(simulations, seedOpt);
        System.out.println("---END---");
    }


}
