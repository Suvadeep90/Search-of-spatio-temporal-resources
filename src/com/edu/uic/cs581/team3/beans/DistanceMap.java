package com.edu.uic.cs581.team3.beans;


public class DistanceMap {

	private Coordinates point;
	private double dist;
	
	/*
	 * Constructor for DistanceMap
	 */
	public DistanceMap()
	{
		point = new Coordinates();
		dist = 0.0;
	}
	
	/*getters and setters*/

	public Coordinates getPoint() {
		return point;
	}

	public void setPoint(Coordinates point) {
		this.point = point;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}
	
}
