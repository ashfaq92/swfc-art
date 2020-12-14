
package com.github.jelmerk.knn.examples.fault.model;

import com.github.jelmerk.knn.examples.auxiliary.model.Point;

import java.util.Random;

/**
 * 
 * @author zxz Casual Angle of the Strip Square
 */
public class FaultZone_Strip extends FaultZone {  //Strip failure mode

	public int[][] inputDomain;
	public long edge; // The edge length of each dimension
	public double aboveLineDelta;  // Intercept of the upper segment
	public double belowLineDelta; // Intercept of the lower segment
	public double ratio; // Slope of two segments

	public FaultZone_Strip() {

	}

	public FaultZone_Strip(int[][] boundary, float area, double rate) { // rate To limit the extent of the boundary 
		inputDomain = boundary;
		theta = area; // Failure Rate
		edge = inputDomain[0][1] - inputDomain[0][0];  //  Dimension Edge Length
		Random random = new Random();
		int lineLocation = random.nextInt(3); // Generate random number and determine the generation position of line segment

		double p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y; // P1, p2 together in x -5000 p3, p4 in y - 5000 p1-p3 line segments are above p2-p4

		if (lineLocation == 0) { // 在上方生成两点
			while (true) {
				p1x = -5000;
				p2x = -5000;
				p2y = -5000 + (10000 * rate * Math.random()); // rate = upper bound
				p3y = 5000;
				p4x = (-5000 + (10000 * (1 - rate))) + (10000 * rate * Math.random());
				p4y = 5000;
				double bigTriangleArea = (5000 - p2y) * (p4x + 5000) / 2;
				ratio = (p4y - p2y) / (p4x - p2x);
				double temp = 2 * (bigTriangleArea - 10000 * 10000 * area) / ratio;
				p3x = Math.sqrt(temp) - 5000;
				p1y = 5000 - ratio * (p3x + 5000);
				if ((p3x >= (-5000 + (10000 * (1 - rate)))) && (p1y <= (-5000 + 10000 * rate))) { // pp3x and p1y are also within the specified range
					break;
				}
			}
		} else if (lineLocation == 1) { // generate two points on the right
			while (true) {
				p1x = -5000;
				p2x = -5000;
				p2y = -5000 + (10000 * Math.random());// generate p2 points
				p3x = 5000;
				p4x = 5000;
				p4y = (-5000 + (10000 * Math.random()));
				p1y = p2y + 10000 * theta;
				p3y = p4y + 10000 * theta;
				ratio = (p4y - p2y) / (p4x - p2x);
				if (p1y <= 5000 && p3y <= 5000) { // p3x and p1y are also within the specified range
					break;
				}
			}
		} else { // generate two points below
			while (true) {
				p1x = -5000;
				p1y = (-5000 + (10000 * (1 - rate))) + (10000 * rate * Math.random()); // 生成p2点
				p2x = -5000;
				p3x = (-5000 + (10000 * (1 - rate))) + (10000 * rate * Math.random());
				p3y = -5000;
				p4y = -5000;
				ratio = (p3y - p1y) / (p3x - p1x);
				double bigTriangleArea = (p1y + 5000) * (p3x + 5000) / 2;
				double temp = 2 * (10000 * 10000 * area - bigTriangleArea) / ratio;
				p4x = Math.sqrt(temp) - 5000;
				p2y = -ratio * (p4x + 5000) - 5000;
				if ((p4x >= (-5000 + (10000 * (1 - rate)))) && (p2y >= (-5000 + (10000 * (1 - rate))))) {
					break;
				}
			}
		}
		aboveLineDelta = p1y - ratio * p1x;
		belowLineDelta = p4y - ratio * p4x;
	}

	@Override
	public Boolean findTarget(Point p) {   // determine if failure is found
		// TODO Auto-generated method stub
		if (p.coordPoint[1] - ratio * p.coordPoint[0] >= belowLineDelta
				&& p.coordPoint[1] - ratio * p.coordPoint[0] <= aboveLineDelta) {
			return true;
		} else
			return false;

	}
}
