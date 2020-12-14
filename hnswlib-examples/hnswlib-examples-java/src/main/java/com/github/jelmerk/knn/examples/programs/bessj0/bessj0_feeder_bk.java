// package com.github.jelmerk.knn.examples.programs.bessj0;
//
// // import util.TestProgram;
//
// import com.github.jelmerk.knn.DistanceFunctions;
// import com.github.jelmerk.knn.examples.auxiliary.model.Point;
// import com.github.jelmerk.knn.examples.fscs.art.FSCS_ART;
// import com.github.jelmerk.knn.examples.hnsw.art.HNSW_ART;
// import com.github.jelmerk.knn.examples.kdfc.art.KDFC_ART;
// import com.github.jelmerk.knn.hnsw.HnswIndex;
//
// import java.io.BufferedWriter;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Random;
//
// import com.sun.jna.Library;
// import com.sun.jna.Native;
// /*
//  * Input Domain:(-300000,300000)
//  * failure rate:0.001298 avf=1.0/0.001298=770.4160246533127889060092449923
//  *errors: 5 errors(2 AOR, 1 ROR, 1SVR ,1CR)
//  * */
// public class bessj0_feeder_bk {
//     public static double[] min = {-300000};
//     public static double[] max = {300000};
//     public static int[][] bd = {{-300000, 300000}};
//     // public static double failureRate = 0.001298;
//     public int Dimension = 1;
//     public static double theta = 0.001373;
//     public static String DLL_PATH = "hnswlib-examples\\hnswlib-examples-java\\target\\classes\\com\\github\\jelmerk\\knn\\examples\\programs\\bessj0\\dpt_bessj0.dll";
//
//     public interface Sender extends Library {
//         double bessj0(double n);
//         double bessj0m(double n);
//     }
//
//
//     public static int randomTesting() {
//         // source of dll file
//         Sender mySender = (Sender) Native.load(DLL_PATH, Sender.class);
//         Random rng = new Random();
//         double tc = min[0] + (max[0] - min[0]) * rng.nextDouble();
//         // System.out.println(tc);
//         int f_measure = 1;
//         while (mySender.bessj0(tc) == mySender.bessj0m(tc)) {
//             f_measure++;
//             tc = min[0] + (max[0] - min[0]) * rng.nextDouble();
//             // System.out.println(tc);
//         }
//         return f_measure;
//     }
//
//     public static int fscsTesting() {
//         Sender mySender = (Sender) Native.load(DLL_PATH, Sender.class);
//
//         FSCS_ART fscs = new FSCS_ART(10);
//         int generatedNum = 0;
//         int maxTry = (int) (30 / theta);
//         int selected;
//         Point[] tcP = new Point[maxTry + 2];
//         Point[] candP = new Point[fscs.candNum];
//
//         tcP[0] = Point.generateRandP(bd);  // first test case
//         // System.out.println(tcP[generatedNum - 1].toString());
//         generatedNum++; // increasing f-measure without testing first test case
//         if (mySender.bessj0(tcP[0].vector[0]) != mySender.bessj0m(tcP[0].vector[0])) {
//             return generatedNum;
//         }
//         do {
//             for (int i = 0; i < fscs.candNum; i++) { // Ëæ»úÉú³Én¸öºòÑ¡µÄ²âÊÔÓÃÀý
//                 candP[i] = Point.generateRandP(bd);
//             }
//             selected = fscs.findFarestCandidate(tcP, generatedNum, candP);
//             tcP[generatedNum] = candP[selected];
//             generatedNum++;
//
//             double x = tcP[generatedNum - 1].vector[0];
//             // System.out.println(tcP[generatedNum - 1].toString());
//             if (mySender.bessj0(x) != mySender.bessj0m(x)) {
//                 break;
//             }
//         } while (generatedNum < maxTry);
//         return generatedNum;
//     }
//
//     public static int limBalKdfcTesting() {
//         Sender mySender = (Sender) Native.load(DLL_PATH, Sender.class);
//         int[] backNum = new int[100 * (int) (1 / theta)];
//         backNum[0] = 1;
//         backNum[1] = 1;
//         double d = bd.length;
//         for (int i = 2; i < backNum.length; i++) {
//             backNum[i] = (int) Math.ceil(1 / 2.0 * Math.pow((d + 1 / d), 2) * (Math.log(i) / Math.log(2)));
//         }
//         Point p = Point.generateRandP(bd);
//         KDFC_ART kdfc = new KDFC_ART(bd);
//         kdfc.insertPointByStrategy(p);
//
//         if (mySender.bessj0(p.vector[0]) != mySender.bessj0m(p.vector[0])) {
//             return kdfc.size;
//         }
//
//         Point finalCase;
//         ArrayList<Point> canD;
//         while (true) {
//             canD = new ArrayList<>(); // ����������ѡ��
//             for (int i = 0; i < kdfc.candidateNum; i++) {
//                 canD.add(Point.generateRandP(kdfc.inputDomain));
//             }
//             finalCase = canD.get(0);
//             int back = backNum[kdfc.size];
//             double distance = kdfc.getMinDisByBacktracking(finalCase, back);
//             for (int c = 1; c < kdfc.candidateNum; c++) {
//                 d = kdfc.getMinDisByBacktracking(canD.get(c), back); // get minimum distance
//                 if (distance < d) { // get the candidate for smallest distance
//                     distance = d;
//                     finalCase = canD.get(c);
//                 }
//             }
//             kdfc.insertPointByStrategy(finalCase);
//             double x = finalCase.vector[0];
//             // System.out.println(tcP[generatedNum - 1].toString());
//             if (mySender.bessj0(x) != mySender.bessj0m(x)) {
//                 break;
//             }
//         }
//         return kdfc.size;
//     }
//
//     public static int hnswTesting(int ef) throws InterruptedException {
//         HNSW_ART myHnsw = new HNSW_ART(10);
//         bessj0_feeder_bk myBessj0 = new bessj0_feeder_bk();
//         // source of dll file
//         Sender mySender = (Sender) Native.load(DLL_PATH, Sender.class);
//
//         int selected;
//         Point[] candP = new Point[myHnsw.candNum]; // Candidate test Case set (candidate_set)
//         ArrayList<Point> selected_set = new ArrayList<Point>();
//
//         int f_measure = 0;
//         // HNSW GRAPH INITIALIZATION
//         int givenM = myBessj0.Dimension * 3;
//         int graphSize = 10000;
//         int efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
//         HnswIndex<Integer, double[], Point, Double> hnswIndex = HnswIndex
//                 .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM).withEf(ef)
//                 .withEfConstruction(efConst).build();
//         Point first_test_case = Point.generateRandP(bd);
//         hnswIndex.add(first_test_case);
//         selected_set.add(first_test_case);
//         f_measure++;
//         if (mySender.bessj0(selected_set.get(f_measure - 1).vector[0]) != mySender.bessj0m(selected_set.get(f_measure - 1).vector[0])) {
//             return f_measure;
//         }
//
//         while (true) {
//             // System.out.println(myBessj0.bd);
//             for (int i = 0; i < myHnsw.candNum; i++) { // Randomly generate n candidate test cases
//                 candP[i] = Point.generateRandP(bd);
//                 // System.out.print(candP[i].printTestCase()+",");
//             }
//             // Make new HNSW Graph with double size of initial provided size and add all previous test cases
//             if (selected_set.size() >= hnswIndex.getMaxItemCount()) {
//                 // System.out.println("doubling");
//                 graphSize = graphSize * 2;
//                 efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
//                 hnswIndex = HnswIndex.newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM)
//                         .withEf(ef).withEfConstruction(efConst).build();
//                 hnswIndex.addAll(selected_set);
//             }
//             selected = myHnsw.findFarestCandidate(candP, hnswIndex);
//             // System.out.println(selected);
//             selected_set.add(candP[selected]);
//             hnswIndex.add(candP[selected]);
//             // System.out.println("\nfarthest"+candP[selected].printTestCase());
//             f_measure++;
//             // System.out.println(generatedNum);
//             // if (fzb.findTarget(selected_set.get(f_measure - 1))) {
//             //     break;
//             // }
//             // double input = selected_set.get(f_measure - 1).vector[0];
//             if (mySender.bessj0(selected_set.get(f_measure - 1).vector[0]) != mySender.bessj0m(selected_set.get(f_measure - 1).vector[0])) {
//                 break;
//             }
//         }
//         return f_measure;
//     }
//
//
//     public static void main(String[] args) throws InterruptedException, IOException {
//         int simulations = 5000;
//         int f_measure;
//         long f_time;
//         String fileName = "bessj0.txt";
//         BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
//         out.write("fscs_fmeasure\tfscs_ftime\tlimbalkdfc_fmeasure\tlimbalkdfc_ftime\thsnw_fmeasure\thnsw_ftime\n");
//
//
//         long fscs_total_f_time = 0;
//         long fscs_totalFMeasure = 0;
//         long kdfc_total_f_time = 0;
//         long kdfc_totalFMeasure = 0;
//         long hnsw_total_f_time = 0;
//         long hnsw_totalFMeasure = 0;
//         for (int i = 1; i <= simulations; i++) {
//             long start_time = System.currentTimeMillis();
//             f_measure = fscsTesting();
//             long end_time = System.currentTimeMillis();
//
//             f_time = end_time - start_time;
//             fscs_totalFMeasure = fscs_totalFMeasure + f_measure;
//             fscs_total_f_time = fscs_total_f_time + f_time;
//             out.write(f_measure + "\t" + f_time + "\t");
//
//             start_time = System.currentTimeMillis();
//             f_measure = limBalKdfcTesting();
//             end_time = System.currentTimeMillis();
//
//             f_time = end_time - start_time;
//             kdfc_totalFMeasure = kdfc_totalFMeasure + f_measure;
//             kdfc_total_f_time = kdfc_total_f_time + f_time;
//             out.write(f_measure + "\t" + f_time + "\t");
//
//             start_time = System.currentTimeMillis();
//             f_measure = hnswTesting(2);
//             end_time = System.currentTimeMillis();
//
//             f_time = end_time - start_time;
//             hnsw_totalFMeasure = hnsw_totalFMeasure + f_measure;
//             hnsw_total_f_time = hnsw_total_f_time + f_time;
//             out.write(f_measure + "\t" + f_time + "\t");
//
//             out.write("\n");
//             out.flush();
//         }
//         double fscs_fmeasure = (double) fscs_totalFMeasure / (double) simulations;
//         double fscs_ftime = (double) fscs_total_f_time / (double) simulations;
//
//         double kdfc_fmeasure = (double) kdfc_totalFMeasure / (double) simulations;
//         double kdfc_ftime = (double) kdfc_total_f_time / (double) simulations;
//
//         double hnsw_fmeasure = (double) hnsw_totalFMeasure / (double) simulations;
//         double hnsw_ftime = (double) hnsw_total_f_time / (double) simulations;
//
//         System.out.println("FSCS_AvgF_measure: " + fscs_fmeasure);
//         out.write(fscs_fmeasure + "\t");
//         System.out.println("FSCS_AvgF_time: " + fscs_ftime);
//         out.write(fscs_ftime + "\t");
//
//         System.out.println("kdfc_AvgF_measure: " + kdfc_fmeasure);
//         out.write(kdfc_fmeasure + "\t");
//         System.out.println("kdfc_AvgF_time: " + kdfc_ftime);
//         out.write(kdfc_ftime + "\t");
//
//         System.out.println("hnsw_AvgF_measure: " + hnsw_fmeasure);
//         out.write(hnsw_fmeasure + "\t");
//         System.out.println("hnsw_AvgF_time: " + hnsw_ftime);
//         out.write(hnsw_ftime + "\t");
//
//         out.close();
//     }
// }
//
