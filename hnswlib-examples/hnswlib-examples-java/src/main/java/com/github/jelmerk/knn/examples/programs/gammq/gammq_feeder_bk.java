// package com.github.jelmerk.knn.examples.programs.gammq;
//
// import com.github.jelmerk.knn.DistanceFunctions;
// import com.github.jelmerk.knn.examples.auxiliary.model.Point;
// import com.github.jelmerk.knn.examples.fscs.art.FSCS_ART;
// import com.github.jelmerk.knn.examples.hnsw.art.HNSW_ART;
// import com.github.jelmerk.knn.examples.kdfc.art.KDFC_ART;
// import com.github.jelmerk.knn.hnsw.HnswIndex;
// import com.sun.jna.Library;
// import com.sun.jna.Native;
//
// import java.io.BufferedWriter;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.ArrayList;
//
//
// public class gammq_feeder {
//     public static double[] min = {0, 0};
//     public static double[] max = {1700, 40};
//     public static int[][] bd = {{0, 1700}, {0, 40}};
//     public static double theta = 0.000830;
//     public int Dimension = 2;
//     public static String DLL_PATH = "C:\\Users\\Muhammad Ashfaq\\OneDrive\\Programming\\hnswlib-master-java\\hnswlib-examples\\hnswlib-examples-java\\src\\main\\java\\com\\github\\jelmerk\\knn\\examples\\programs\\gammq\\dpt_gammq.dll";
//
//     public interface Sender extends Library {
//         boolean Produces_Error(double x, double y);
//     }
//
//
//     // public static int randomTesting() {
//     //     Sender mySender = (Sender) Native.load(DLL_PATH, Sender.class);
//     //     Random rng = new Random();
//     //     double tc = min[0] + (max[0] - min[0]) * rng.nextDouble();
//     //     int f_measure = 1;
//     //     while (mySender.bessj(tc) == mySender.bessjm(tc)) {
//     //         f_measure++;
//     //         tc = min[0] + (max[0] - min[0]) * rng.nextDouble();
//     //     }
//     //     return f_measure;
//     // }
//     public static int fscsTesting() {
//         Sender mySender = (Sender) Native.load(DLL_PATH, Sender.class);
//         FSCS_ART fscs = new FSCS_ART(10);
//         int generatedNum = 0;
//         int maxTry = (int) (30 / theta);
//         int selected;
//         Point[] tcP = new Point[maxTry + 2];
//         Point[] candP = new Point[fscs.candNum];
//
//         tcP[0] = Point.generateRandP(bd);  // first test case
//         generatedNum++; // increasing f-measure without testing first test case
//         double x = tcP[0].vector[0];
//         double y = tcP[0].vector[1];
//         if (mySender.Produces_Error(x, y)) {
//             return generatedNum;
//         }
//         do {
//             for (int i = 0; i < fscs.candNum; i++) { // Ëæ»úÉú³Én¸öºòÑ¡µÄ²âÊÔÓÃÀý
//                 candP[i] = Point.generateRandP(bd);
//             }
//             selected = fscs.findFarestCandidate(tcP, generatedNum, candP);
//             tcP[generatedNum] = candP[selected];
//             generatedNum++;
//             x = tcP[generatedNum - 1].vector[0];
//             y = tcP[generatedNum - 1].vector[1];
//             // System.out.print(selected_set.get(f_measure - 1).printTestCase());
//             if (mySender.Produces_Error(x, y)) {
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
//         double x = p.vector[0];
//         double y = p.vector[1];
//         if (mySender.Produces_Error(x,y)) {
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
//             x = finalCase.vector[0];
//             y = finalCase.vector[1];
//             // System.out.println(tcP[generatedNum - 1].toString());
//             if (mySender.Produces_Error(x, y)) {
//                 break;
//             }
//         }
//         return kdfc.size;
//     }
//
//     public static int hnswTesting(int ef) throws InterruptedException {
//         HNSW_ART myHnsw = new HNSW_ART(10);
//         gammq_feeder PogramInstance = new gammq_feeder();
//         Sender mySender = (Sender) Native.load(DLL_PATH, Sender.class);
//
//         int selected;
//         Point[] candP = new Point[myHnsw.candNum]; // Candidate test Case set (candidate_set)
//         ArrayList<Point> selected_set = new ArrayList<Point>();
//
//         int f_measure = 0;
//         // HNSW GRAPH INITIALIZATION
//         int givenM = PogramInstance.Dimension * 3;
//         int graphSize = 10000;
//         int efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
//         HnswIndex<Integer, double[], Point, Double> hnswIndex = HnswIndex
//                 .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM).withEf(ef)
//                 .withEfConstruction(efConst).build();
//         Point first_test_case = Point.generateRandP(bd);
//         hnswIndex.add(first_test_case);
//         selected_set.add(first_test_case);
//         f_measure++;
//         double x = selected_set.get(f_measure - 1).vector[0];
//         double y = selected_set.get(f_measure - 1).vector[1];
//         if (mySender.Produces_Error(x, y)) {
//             return f_measure;
//         }
//
//         while (true) {
//             for (int i = 0; i < myHnsw.candNum; i++) { // Randomly generate n candidate test cases
//                 candP[i] = Point.generateRandP(bd);
//                 // System.out.print(candP[i].printTestCase() + ",");
//
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
//             selected_set.add(candP[selected]);
//             hnswIndex.add(candP[selected]);
//             f_measure++;
//             x = selected_set.get(f_measure - 1).vector[0];
//             y = selected_set.get(f_measure - 1).vector[1];
//             // System.out.print(selected_set.get(f_measure - 1).printTestCase());
//             if (mySender.Produces_Error(x, y)) {
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
//         String fileName = "gammq.txt";
//         BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
//         out.write("fscs_fmeasure\tfscs_ftime\tlimbalkdfc_fmeasure\tlimbalkdfc_ftime\thsnw_fmeasure\thnsw_ftime\n");
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
//         System.out.println(fileName);
//         System.out.println("F_measures (fscs,kdfc,hnsw): ");
//         System.out.println(fscs_fmeasure + "\t\t" + kdfc_fmeasure + "\t\t" + hnsw_fmeasure);
//         System.out.println("F_time: (fscs,kdfc,hnsw)");
//         System.out.println(fscs_ftime + "\t\t" + kdfc_ftime + "\t\t" + hnsw_ftime);
//
//         out.write(fscs_fmeasure + "\t" + fscs_ftime + "\t" + kdfc_fmeasure + "\t" + kdfc_ftime + "\t" + hnsw_fmeasure + "\t" + hnsw_ftime);
//         out.close();
//     }
// }
//
