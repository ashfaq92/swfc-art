package com.github.jelmerk.knn.examples.fault.model;

import com.github.jelmerk.knn.examples.auxiliary.model.Point;

import java.util.concurrent.TimeUnit;

public class FaultZone_Point_Square extends FaultZone {   //��״ʧЧģʽ

	public int[][] inputDomain; // ������Χ
	public int num = 25;   //С��ʧЧ�������
	public Point[] faultPoint; // �洢���ϵ������

	public float delta; // ʧЧ����ı߳�
 
	public FaultZone_Point_Square() {

	}

	public FaultZone_Point_Square(int[][] boundary, double area) throws InterruptedException {
		theta = area;
		inputDomain = boundary;
		double sum = 1.0;
		int n = boundary.length;
		for (int i = 0; i < n; i++) {
			sum = sum * (inputDomain[i][1] - inputDomain[i][0]);
		}

		delta = (float) Math.pow(sum * theta / num, 1.0 / n);
		faultPoint = new Point[num];
		int temp = 0;

		while (temp < num) {
			Point faulttemp = new Point(n);
			do {
				for (int i = 0; i < n; i++) {
					faulttemp.coordPoint[i] = inputDomain[i][0]+ (float) ((inputDomain[i][1] - inputDomain[i][0] - delta) * Math.random());
				}
			} while (isOverlap(temp, faulttemp, delta));

			faultPoint[temp] = faulttemp;
			temp++;
		}
	}

	boolean isOverlap(int gNum, Point p, double delta) {   //�ж��Ƿ����Ѳ�����ʧЧ�����ص�
		if (gNum == 0)
			return false;
		else {
			for (int i = 0; i < gNum; i++) {
				boolean ftemp = true;
				for (int j = 0; j < p.n; j++) {
					if (!(Math.abs(p.coordPoint[j] - faultPoint[i].coordPoint[j]) < delta)) {
						ftemp = false;
						break;
					}
				}
				if (ftemp) {
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public Boolean findTarget(Point p) {   // �ж��Ƿ���ʧЧ
		// TODO Auto-generated method stub
		for (int i = 0; i < this.num; i++) {
			boolean ftemp = true;
			for (int j = 0; j < p.n; j++) {
				if (!(p.coordPoint[j] >= this.faultPoint[i].coordPoint[j]
						&& p.coordPoint[j] <= this.faultPoint[i].coordPoint[j] + this.delta)) {
					ftemp = false;
					break;
				}
			}
			if (ftemp) {
				return true;
			}
		}
		return false;
	}
}

