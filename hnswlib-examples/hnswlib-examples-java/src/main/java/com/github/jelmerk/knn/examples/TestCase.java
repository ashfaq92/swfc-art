package com.github.jelmerk.knn.examples;

import com.github.jelmerk.knn.Item;

import java.util.Arrays;
import java.util.Random;

public class TestCase implements Item<Integer, double[]> {

    private static final long serialVersionUID = 1L;

    private final Integer id;
    public final double[] vector;

    public TestCase(int id, double[] vector) {
        this.id = id;
        // this.id = new Random().nextInt()  & Integer.MAX_VALUE; //Only positive integer ID's of random points
        this.vector = vector;
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
        return "TestCase{" + "id='" + id + '\'' + ", vector=" + Arrays.toString(vector) + '}';
    }

    String printTestCase() {
        return "(" + vector[0] + "," + vector[1] + ")";
    }
}