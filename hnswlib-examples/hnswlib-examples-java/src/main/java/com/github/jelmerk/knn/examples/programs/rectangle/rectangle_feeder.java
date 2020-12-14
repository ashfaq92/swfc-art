// package com.github.jelmerk.knn.examples.programs.rectangle;
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
// import java.util.ArrayList;
// import java.util.Random;
//
//
// public class rectangle_feeder {
//     public static double[] min = {-25, -25, -25, -25, -25, -25, -25, -25};
//     public static double[] max = {25, 25, 25, 25, 25, 25, 25, 25};
//     public static int[][] bd = {{-25, 25}, {-25, 25}, {-25, 25}, {-25, 25}, {-25, 25}, {-25, 25}, {-25, 25}, {-25, 25}};
//     public static int Dimension = 8;
//     public static double theta = 0.0005432096673068936;
//     public static String DLL_PATH = "C:\\Users\\Muhammad Ashfaq\\OneDrive\\Programming\\hnswlib-master-java\\hnswlib-examples\\hnswlib-examples-java\\src\\main\\java\\com\\github\\jelmerk\\knn\\examples\\programs\\rectangle\\rectangle.dll";
//
//     public interface Sender extends Library {
//         // boolean Produces_Error(double d1, double d2, double d3, double d4, double d5, double d6, double d7, double d8);
//         int LineRectangle(double xr1, double xr2, double yr1, double yr2, double xl1, double xl2, double yl1, double yl2);
//
//         int LineRectangle_m(double xr1, double xr2, double yr1, double yr2, double xl1, double xl2, double yl1, double yl2);
//
//     }
//
//
//     public static int randomTesting() {
//         Sender mySender = (Sender) Native.load(DLL_PATH, Sender.class);
//         Random rng = new Random();
//         double d1, d2, d3, d4, d5, d6, d7, d8;
//         int output1, output2;
//         int f_measure = 1;
//         while (true) {
//             d1 = min[0] + (max[0] - min[0]) * rng.nextDouble();
//             d2 = min[1] + (max[1] - min[1]) * rng.nextDouble();
//             d3 = min[2] + (max[2] - min[2]) * rng.nextDouble();
//             d4 = min[3] + (max[3] - min[3]) * rng.nextDouble();
//             d5 = min[4] + (max[4] - min[4]) * rng.nextDouble();
//             d6 = min[5] + (max[5] - min[5]) * rng.nextDouble();
//             d7 = min[6] + (max[6] - min[6]) * rng.nextDouble();
//             d8 = min[7] + (max[7] - min[7]) * rng.nextDouble();
//             output1 = mySender.LineRectangle(d1, d2, d3, d4, d5, d6, d7, d8);
//             output2 = mySender.LineRectangle_m(d1, d2, d3, d4, d5, d6, d7, d8);
//             if (output1 != output2) {
//                 break;
//             }
//             f_measure++;
//         }
//         ;
//         return f_measure;
//     }
//
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
//         double d1, d2, d3, d4, d5, d6, d7, d8;
//         int output1, output2;
//         do {
//             for (int i = 0; i < fscs.candNum; i++) { // Ëæ»úÉú³Én¸öºòÑ¡µÄ²âÊÔÓÃÀý
//                 candP[i] = Point.generateRandP(bd);
//             }
//             selected = fscs.findFarestCandidate(tcP, generatedNum, candP);
//             tcP[generatedNum] = candP[selected];
//             generatedNum++;
//
//             d1 = tcP[generatedNum - 1].vector[0];
//             d2 = tcP[generatedNum - 1].vector[1];
//             d3 = tcP[generatedNum - 1].vector[2];
//             d4 = tcP[generatedNum - 1].vector[3];
//             d5 = tcP[generatedNum - 1].vector[4];
//             d6 = tcP[generatedNum - 1].vector[5];
//             d7 = tcP[generatedNum - 1].vector[6];
//             d8 = tcP[generatedNum - 1].vector[7];
//             // System.out.println(tcP[generatedNum - 1].toString());
//             output1 = mySender.LineRectangle(d1, d2, d3, d4, d5, d6, d7, d8);
//             output2 = mySender.LineRectangle_m(d1, d2, d3, d4, d5, d6, d7, d8);
//             if (output1 != output2) {
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
//         double d1, d2, d3, d4, d5, d6, d7, d8;
//         int output1, output2;
//
//         Point finalCase;
//         ArrayList<Point> canD;
//         do {
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
//             d1 = finalCase.vector[0];
//             d2 = finalCase.vector[1];
//             d3 = finalCase.vector[2];
//             d4 = finalCase.vector[3];
//             d5 = finalCase.vector[4];
//             d6 = finalCase.vector[5];
//             d7 = finalCase.vector[6];
//             d8 = finalCase.vector[7];
//             // System.out.println(tcP[generatedNum - 1].toString());
//             output1 = mySender.LineRectangle(d1, d2, d3, d4, d5, d6, d7, d8);
//             output2 = mySender.LineRectangle_m(d1, d2, d3, d4, d5, d6, d7, d8);
//         } while (output1 == output2);
//         return kdfc.size;
//     }
//
//     public static int hnswTesting() throws InterruptedException {
//         HNSW_ART myHnsw = new HNSW_ART(10);
//         Sender mySender = (Sender) Native.load(DLL_PATH, Sender.class);
//
//         int selected;
//         Point[] candP = new Point[myHnsw.candNum]; // Candidate test Case set (candidate_set)
//         ArrayList<Point> selected_set = new ArrayList<Point>();
//
//         int f_measure = 0;
//         // HNSW GRAPH INITIALIZATION
//         int givenM = Dimension * 3;
//         int graphSize = 10000;
//         int givenEf = 2;
//         int efConst = 3 * (int) Math.ceil(Math.log10(graphSize));
//         HnswIndex<Integer, double[], Point, Double> hnswIndex = HnswIndex
//                 .newBuilder(DistanceFunctions.DOUBLE_EUCLIDEAN_DISTANCE, graphSize).withM(givenM).withEf(givenEf)
//                 .withEfConstruction(efConst).build();
//         Point first_test_case = Point.generateRandP(bd);
//         hnswIndex.add(first_test_case);
//         selected_set.add(first_test_case);
//         double d1, d2, d3, d4, d5, d6, d7, d8;
//         int output1, output2;
//
//         do {
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
//                         .withEf(givenEf).withEfConstruction(efConst).build();
//                 hnswIndex.addAll(selected_set);
//             }
//             selected = myHnsw.findFarestCandidate(candP, hnswIndex);
//             selected_set.add(candP[selected]);
//             hnswIndex.add(candP[selected]);
//             f_measure++;
//             d1 = selected_set.get(f_measure - 1).vector[0];
//             d2 = selected_set.get(f_measure - 1).vector[1];
//             d3 = selected_set.get(f_measure - 1).vector[2];
//             d4 = selected_set.get(f_measure - 1).vector[3];
//             d5 = selected_set.get(f_measure - 1).vector[4];
//             d6 = selected_set.get(f_measure - 1).vector[5];
//             d7 = selected_set.get(f_measure - 1).vector[6];
//             d8 = selected_set.get(f_measure - 1).vector[7];
//             // System.out.print(selected_set.get(f_measure - 1).printTestCase());
//             output1 = mySender.LineRectangle(d1, d2, d3, d4, d5, d6, d7, d8);
//             output2 = mySender.LineRectangle_m(d1, d2, d3, d4, d5, d6, d7, d8);
//         } while (output1 == output2);
//         return f_measure;
//     }
//
//
//     public static void main(String[] args) throws InterruptedException {
//         int simulations = 5000;
//         long totalFMeasure = 0;
//         int f_measure;
//         long total_fm_time = 0;
//         for (int i = 1; i <= simulations; i++) {
//             long start_time = System.currentTimeMillis();
//             // f_measure = randomTesting();  //f-time: 36.0836
//             // f_measure = fscsTesting(); // F_time
//             // f_measure = limBalKdfcTesting(); //ftime: 772.2012
//             f_measure = hnswTesting(); // ftime = 415.2756  // ftime2: 408.2998
//             long end_time = System.currentTimeMillis();
//
//             totalFMeasure = totalFMeasure + f_measure;
//             total_fm_time = total_fm_time + (end_time - start_time);
//             System.out.println(i + "\tF-measure: " + f_measure + "\tMeanFmeasure: " + (double) totalFMeasure / (double) i);
//         }
//         System.out.println("F_measure: " + (double) totalFMeasure / (double) simulations);
//         System.out.println("F_time: " + (double) total_fm_time / (double) simulations);
//     }
// }
//
