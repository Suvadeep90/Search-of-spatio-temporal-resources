package com.edu.uic.cs581.team3.beans;


public class LocationMapDistance {

	private LocationCoordinates point;
	private double dist;
	
	/*
	 * Constructor for DistanceMap
	 */
	public LocationMapDistance()
	{
		point = new LocationCoordinates();
		dist = 0.0;
	}
	
	/*getters and setters*/

	public LocationCoordinates getPoint() {
		return point;
	}

	public void setPoint(LocationCoordinates point) {
		this.point = point;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}
	
}
