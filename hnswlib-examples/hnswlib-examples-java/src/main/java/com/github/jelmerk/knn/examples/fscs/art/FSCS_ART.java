package com.github.jelmerk.knn.examples.fscs.art;

import com.github.jelmerk.knn.examples.auxiliary.model.Point;
import com.github.jelmerk.knn.examples.fault.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FSCS_ART {
    public int candNum = 10; // ��ѡ��������
    public int[][] inputDomain;

    public FSCS_ART() {
        candNum = 10;
    }

    public FSCS_ART(int n) {
        candNum = n;
    }

    public int findFarestCandidate(Point[] tcP, int size, Point[] candP) // ȷ����Զ�ĺ�ѡ����
    {
        double[] dist = new double[candP.length];
        double tempDist, farestDist = 0;
        int farestIndex = 0;

        for (int i = 0; i < candP.length; i++) {
            dist[i] = Point.getDistance(candP[i], tcP[0]);

            for (int j = 1; j < size; j++) {
                tempDist = Point.getDistance(candP[i], tcP[j]);
                if (tempDist < dist[i])
                    dist[i] = tempDist;
            }

            if (i == 0) {
                farestDist = dist[0];
                farestIndex = 0;
            } else {
                if (farestDist < dist[i]) {
                    farestDist = dist[i];
                    farestIndex = i;
                }

            }
        }
        return farestIndex;
    }

    public int testFscsArt_Effectiveness(int[][] bound, FaultZone fzb) { // ʧЧ���Ч������

        int generatedNum = 0; // �����ɵĲ���������Ŀ
        int maxTry = (int) (30 / fzb.theta); // �������maxTry�λ�δ���й�������ֹͣ
        int selected;
        Point[] tcP = new Point[maxTry + 2]; // �Ѳ��������ļ���
        Point[] candP = new Point[candNum]; // ��ѡ��������

        tcP[0] = Point.generateRandP(bound);  // first test case
        generatedNum++; // increasing f-measure without testing first test case

        do {
            for (int i = 0; i < candNum; i++) { // �������n����ѡ�Ĳ�������
                candP[i] = Point.generateRandP(bound);
            }
            selected = findFarestCandidate(tcP, generatedNum, candP);
            tcP[generatedNum] = candP[selected];
            generatedNum++;
            if (fzb.findTarget(tcP[generatedNum - 1])) {
                break;
            }
        } while (generatedNum < maxTry);
        return generatedNum;
    }

    public void testFscsArt_Efficiency(int num, int bound[][]) throws IOException { // ����Ч�ʲ���
        int selected;
        Point[] tcP = new Point[num];
        Point[] candP = new Point[candNum];
        tcP[0] = Point.generateRandP(bound);
        for (int j = 1; j < num; j++) { // �������n����ѡ�Ĳ�������
            for (int i = 0; i < candNum; i++) {
                candP[i] = Point.generateRandP(bound);
            }
            selected = findFarestCandidate(tcP, j, candP);
            tcP[j] = candP[selected];
        }
    }

    public int testFscsArt_Discrepancy(int[][] bound, FaultZone fzb, int testCases) { // ʧЧ���Ч������
        int counter = 0; // number of test cases in sub-domain region
        int selected;
        Point[] tcP = new Point[testCases];
        Point[] candP = new Point[candNum];
        tcP[0] = Point.generateRandP(bound);
        for (int j = 1; j < testCases; j++) { // �������n����ѡ�Ĳ�������
            for (int i = 0; i < candNum; i++) {
                candP[i] = Point.generateRandP(bound);
            }
            selected = findFarestCandidate(tcP, j, candP);
            tcP[j] = candP[selected];
            if (fzb.findTarget(tcP[j])) {
                counter++;
            }
        }
        return counter;
    }

    public double nn_distance(Point q, Point[] X) {
        double nn_distance = Integer.MAX_VALUE;
        double distance;
        for (Point x : X) {
            distance = Point.getDistance(q, x);
            if ((distance < nn_distance) && (distance != 0)) {
                nn_distance = distance;
            }
        }
        return nn_distance;
    }

    public double testFscsArt_Dispersion(int num, int[][] bound) throws IOException { // ����Ч�ʲ���
        int selected;
        Point[] tcP = new Point[num];
        Point[] candP = new Point[candNum];
        tcP[0] = Point.generateRandP(bound);
        for (int j = 1; j < num; j++) { // �������n����ѡ�Ĳ�������
            for (int i = 0; i < candNum; i++) {
                candP[i] = Point.generateRandP(bound);
            }
            selected = findFarestCandidate(tcP, j, candP);
            tcP[j] = candP[selected];
        }
        double distance;
        double max_distance = -1;
        for (Point q : tcP) {
            distance = nn_distance(q, tcP);
            if (distance > max_distance) {
                max_distance = distance;
            }
        }
        return max_distance;
    }

}
