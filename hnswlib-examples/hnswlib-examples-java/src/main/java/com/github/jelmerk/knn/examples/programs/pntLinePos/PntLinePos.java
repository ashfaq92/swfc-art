package com.github.jelmerk.knn.examples.programs.pntLinePos;

import java.util.Scanner;

/**
 * 
 * @author zxz 
 * ÅÐ¶ÏÒ»¸öµãÓëÒ»ÌõÖ±ÏßµÄÎ»ÖÃ¹ØÏµ
 */
public class PntLinePos {

	/**
	 * Return true if point (x2, y2) is on the left side of the directed line from
	 * (x0, y0) to (x1, y1)
	 */
	public static boolean leftOfTheLine(double x0, double y0, double x1, double y1, double x2, double y2) {
		return (x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0) > 0;
	}

	/**
	 * Return true if point (x2, y2) is on the same line from (x0, y0) to (x1, y1)
	 */
	public static boolean onTheSameLine(double x0, double y0, double x1, double y1, double x2, double y2) {
		return Math.abs((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0)) < 0.000000001;
	}

	/**
	 * Return true if point (x2, y2) is on the line segment from (x0, y0) to (x1,
	 * y1)
	 */
	public static boolean onTheLineSegment(double x0, double y0, double x1, double y1, double x2, double y2) {
		return onTheSameLine(x0, y0, x1, y1, x2, y2) && ((x0 <= x2 && x2 <= x1) || (x0 >= x2 && x2 >= x1));
	}

	public static int getTheLocation(double x0, double y0, double x1, double y1, double x2, double y2) {
		if (leftOfTheLine(x0, y0, x1, y1, x2, y2))
			return 0; // ×ó±ß
		else if (onTheLineSegment(x0, y0, x1, y1, x2, y2))
			return 1; // ÔÚÏß¶ÎÉÏ
		else if (onTheSameLine(x0, y0, x1, y1, x2, y2))
			return 2; // ÔÚÖ±ÏßÉÏ
		else
			return 3; // ÓÒ±ß
	}

	public boolean producesError(int x0, int y0, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		int origResult = PntLinePos.getTheLocation(x0, y0, x1, y1, x2, y2);
		int errResult = PntLinePos_Err.getTheLocation(x0, y0, x1, y1, x2, y2);
		if (origResult != errResult) {
			return true;
		}
		return false;
	}
}
