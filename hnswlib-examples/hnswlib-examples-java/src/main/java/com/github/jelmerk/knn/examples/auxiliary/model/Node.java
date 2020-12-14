package com.github.jelmerk.knn.examples.auxiliary.model;

public class Node {

	public int spilt; // The serial number of the split dimension
	public Node left; // Left Tree
	public Node right; // Right sub-tree
	public Node parent; // Parent Node
	public Point p; // a test point (test case) in this node
	public float[][] boundary; // The boundary area of the space in which the node is located
	public int deep;
	
	public Node() {
		super();
	}




}
