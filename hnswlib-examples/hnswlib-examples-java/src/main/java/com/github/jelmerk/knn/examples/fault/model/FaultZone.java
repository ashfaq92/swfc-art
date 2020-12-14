package com.github.jelmerk.knn.examples.fault.model;

import com.github.jelmerk.knn.examples.auxiliary.model.Point;

public abstract class FaultZone {
	public double theta;
	public abstract Boolean findTarget(Point p);
	public double total_domain_area;  //for discrepancy calculation
	public double sub_domain_area;  //for discrepancy calculation
}
