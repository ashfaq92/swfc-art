package com.github.jelmerk.knn.examples.programs.twoLinesPos;

import java.lang.Math;

/**
 * 
 * @author zxz
 * �ж����߶��Ƿ��ཻ
 */
public class TwoLinesPos {



	// ���
	public double mult(Point a, Point b, Point c) {
		return (a.x - c.x) * (b.y - c.y) - (b.x - c.x) * (a.y - c.y);
	}

	// aa��bbΪһ���߶����˵�  cc��ddΪ��һ���߶ε����˵� �����߶��ཻʱ����1
	public int intersect(Point aa, Point bb, Point cc, Point dd) {
		if (Math.max(aa.x, bb.x) < Math.min(cc.x, dd.x)) {
			return -1;
		}
		if (Math.max(aa.y, bb.y) < Math.min(cc.y, dd.y)) {
			return -1;
		}
		if (Math.max(cc.x, dd.x) < Math.min(aa.x, bb.x)) {
			return -1;
		}
		if (Math.max(cc.y, dd.y) < Math.min(aa.y, bb.y)) {
			return -1;
		}
		if (mult(cc, bb, aa) * mult(bb, dd, aa) < 0) {
			return -1;
		}
		if (mult(aa, dd, cc) * mult(dd, bb, cc) < 0) {
			return -1;
		}
		return 1;
	}

	public int intersectErr(Point aa, Point bb, Point cc, Point dd) {
		if (Math.max(aa.x, bb.x) < Math.min(cc.x, dd.x)) {
			return -1;
		}
		if (Math.max(aa.y, bb.y) < Math.min(cc.y, dd.y)) {
			return -1;
		}
		if (Math.max(cc.x, dd.x) < Math.min(aa.x, bb.x)) {
			return -1;
		}
		if (Math.max(cc.y, dd.y) < Math.min(aa.y, bb.y)) {
			return -1;
		}
		if (mult(cc, bb, aa) * mult(bb, dd, aa) < -15) { // ERROR mult(bb, dd, aa) < 0
			return -1;
		}
		if (mult(aa, dd, cc) * mult(dd, bb, cc) < 0) {
			return -1;
		}
		return 1;
	}

	public boolean producesError(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		// TODO Auto-generated method stub
		Point p1 = new Point(x1, y1);
		Point p2 = new Point(x2, y2);
		Point p3 = new Point(x3, y3);
		Point p4 = new Point(x4, y4);
		if (intersect(p1, p2, p3, p4) != intersectErr(p1, p2, p3, p4)) {
			return true;
		}
		return false;
	}

}
