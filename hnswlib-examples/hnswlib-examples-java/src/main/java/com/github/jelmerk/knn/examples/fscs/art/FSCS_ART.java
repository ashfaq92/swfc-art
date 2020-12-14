package com.github.jelmerk.knn.examples.fscs.art;

import com.github.jelmerk.knn.examples.auxiliary.model.Point;
import com.github.jelmerk.knn.examples.fault.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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

    public int testFscsArt_Effectiveness(int[][] bound, FaultZone fzb, long seed) { // ʧЧ���Ч������
        Random generator = new Random(seed);

        int generatedNum = 0; // �����ɵĲ���������Ŀ
        int maxTry = (int) (30 / fzb.theta); // �������maxTry�λ�δ���й�������ֹͣ
        int selected;
        Point[] tcP = new Point[maxTry + 2]; // �Ѳ��������ļ���
        Point[] candP = new Point[candNum]; // ��ѡ��������

        tcP[0] = Point.generateRandP(bound, generator);  // first test case
        generatedNum++; // increasing f-measure without testing first test case

        do {
            for (int i = 0; i < candNum; i++) { // �������n����ѡ�Ĳ�������
                candP[i] = Point.generateRandP(bound, generator);
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

}
