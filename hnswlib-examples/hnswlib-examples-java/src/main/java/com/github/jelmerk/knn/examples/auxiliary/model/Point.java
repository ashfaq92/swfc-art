package com.github.jelmerk.knn.examples.auxiliary.model;

import com.github.jelmerk.knn.Item;

import java.util.Arrays;
import java.util.Random;

public class Point implements Item<Integer, double[]> {
    private static final long serialVersionUID = 1L;

    private final Integer id;
    public final double[] vector;

    public int n; // Dimensions
    public float[] coordPoint; //Store the coordinate values of each dimension of the point

    public Point(int n) {
        super();
        this.id = new Random().nextInt() & Integer.MAX_VALUE; //Only positive integer ID's of random points
        this.n = n;
        this.coordPoint = new float[n];
        this.vector = new double[n];
    }

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public double[] vector() {
        return vector;
    }

    @Override
    public String toString() {
        return "Point{" + "id='" + id + '\'' + ", vector=" + Arrays.toString(vector) + '}';
    }

    public String printTestCase() {
        return "(" + vector[0] + "," + vector[1] + ")";
    }

    public static double getDistance(Point p1, Point p2) {   /// Get the Euclidean distance between the two test points
        double sum = 0;
        for (int i = 0; i < p1.n; i++) {
            sum = sum + Math.pow(p1.coordPoint[i] - p2.coordPoint[i], 2);
        }
        return Math.sqrt(sum);

    }

    public static Point generateRandP(int[][] inputDomain) { //  Randomly generate a test case from a given space
        int n = inputDomain.length;
        Point newPoint = new Point(n);
        for (int i = 0; i < n; i++) {
            // newPoint.coordPoint[i] = (float) (inputDomain[i][0] + (inputDomain[i][1] - inputDomain[i][0]) * Math.random());
            newPoint.coordPoint[i] = (float) (inputDomain[i][0] + (Math.random() * ((inputDomain[i][1] - inputDomain[i][0]) + 1)) );
            newPoint.vector[i] = newPoint.coordPoint[i];
        }
        return newPoint;
    }


}
