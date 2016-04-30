package com.edu.uic.cs581.team3.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.edu.uic.cs581.team3.database.FetchPoints;
import com.edu.uic.cs581.team3.database.ParkingChoosePoints;

public class PointsApproximate implements ServletContextListener {

	public static int requestCount = 0;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		System.out.println("CS 581 Project Server stopped............");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		System.out.println("CS 581 Project Server started............");
		
		new FetchPoints().getCoordinates();
		
		new ParkingChoosePoints().getCoordinates();
		
		System.out.println("Arraylist populated: "+FetchPoints.point.size());
	}

}
