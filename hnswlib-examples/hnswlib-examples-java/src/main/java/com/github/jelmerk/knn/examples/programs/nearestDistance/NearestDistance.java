package com.github.jelmerk.knn.examples.programs.nearestDistance;

public class NearestDistance {

	public static double getMinDistance(double[][] points) {
		int p1 = 0, p2 = 1;
		double shortestDistance = distance(points[p1][0], points[p1][1], points[p2][0], points[p2][1]);

		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				double distance = distance(points[i][0], points[i][1], points[j][0], points[j][1]);

				if (shortestDistance > distance) {
					p1 = i;
					p2 = j;
					shortestDistance = distance;
				}
			}
		}
		return shortestDistance;
	}

	public static double getMinDistanceErr(double[][] points) {
		int p1 = 0, p2 = 1;
		double shortestDistance = distance(points[p1][0], points[p1][1], points[p2][0], points[p2][1]);

		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {
				double distance = distance(points[i][0], points[i][1], points[j][0], points[j][1]);

				if (shortestDistance > distance - 0.09) { // ERROR if (shortestDistance > distance) {
					p1 = i;
					p2 = j;
					shortestDistance = distance;
				}
			}
		}
		return shortestDistance;
	}

	public static double distance(double x1, double y1, double x2, double y2) {

		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}


	public boolean producesError(int x0, int x1, int x2, int x3, int x4, int x5, int x6, int x7, int x8, int x9) {
		// TODO Auto-generated method stub

		double[][] points = { { x0, x1 }, { x2, x3 }, { x4, x5 }, { x6, x7 }, { x8, x9 } };
		double origResult = getMinDistance(points);
		double errResult = getMinDistanceErr(points);
		if (Math.abs(origResult - errResult) > 1e-20)
			return true;

		return false;
	}

}