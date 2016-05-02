package com.edu.uic.cs581.team3.beans;

public class BlockAvailableQuadrant {

	LocationCoordinates C1 = new LocationCoordinates();
	public LocationCoordinates getC1() {
		return C1;
	}
	public void setC1(LocationCoordinates c1) {
		C1 = c1;
	}
	public int getAvailable() {
		return Available;
	}
	public void setAvailable(double d) {
		Available = (int) d;
	}
	int Available;
	
	
}
