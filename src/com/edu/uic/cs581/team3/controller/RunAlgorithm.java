package com.edu.uic.cs581.team3.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.apache.catalina.connector.Request;

import com.edu.uic.cs581.team3.algorithm.ProcessAlgorithm;
import com.edu.uic.cs581.team3.beans.LocationDataSet;
import com.edu.uic.cs581.team3.beans.LocationMapDistance;
import com.edu.uic.cs581.team3.beans.LocationPoints;
import com.edu.uic.cs581.team3.beans.BlockAvailableQuadrant;
import com.edu.uic.cs581.team3.beans.BlockQuadrant;
import com.edu.uic.cs581.team3.constants.ConstVariables;
import com.edu.uic.cs581.team3.database.FetchPoints;
import com.edu.uic.cs581.team3.database.ParkingChoosePoints;
import com.edu.uic.cs581.team3.database.Force;
import com.google.gson.Gson;

/**
 * Servlet implementation class RunAlgorithm
 */
//@WebServlet(description = "Invokes the routing algorithm", urlPatterns = { "/RunAlgorithm" })
public class RunAlgorithm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RunAlgorithm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Inside Invoke Algorithm");
		String end = "";
		String finalDest = "";
		String intersection = "";
		
		//getting destination co-ordinates
		String destination_Location = request.getParameter("getDest");
		
		//getting the algorithm type
		String algoString = request.getParameter("getAlgoType");

		//select iteration for baseline algorithm
		String iteration = request.getParameter("getIteration");
		System.out.println("Value of Iteration"+iteration);
		
		//creating object of ImplementAlgorithm
		ProcessAlgorithm impl = new ProcessAlgorithm();
		
		HttpSession session = request.getSession();

		if(iteration!=null)
		{
			if(iteration!="undefined")
			{
				if(iteration.equalsIgnoreCase("next")){
					String points = request.getParameter("getPoints");
					points = points.replace("(", "");
					points = points.replace(")","");
					Double pointsLat = Double.parseDouble(points.split(",")[0]);
					Double pointsLong = Double.parseDouble(points.split(",")[1]);
					LocationMapDistance distanceMap = new Force().pointWeight(pointsLat,pointsLong, FetchPoints.point);
					points = distanceMap.getPoint().getLatitude() + "," + distanceMap.getPoint().getLongitude();;
					String currBlock =  session.getAttribute("currBlock") + "";
					currBlock = session.getAttribute("blockID") + "," + currBlock;
					session.setAttribute("blockID", currBlock);
					BlockQuadrant quad = new BlockQuadrant();
					ArrayList<BlockAvailableQuadrant> q = new ArrayList<BlockAvailableQuadrant>();
					q.addAll(quad.maxAvailable(pointsLat, pointsLong));
					String Lat = "";
					String Long = "";
					for(BlockAvailableQuadrant i : q)
					{
						Lat = Lat + i.getC1().getLatitude() +  ", ";
						Long = Long + i.getC1().getLongitude() + ", ";
					}
					if(algoString.equalsIgnoreCase(ConstVariables.PT_Gravitational_Cost_Algo))
							end = impl.probabilisticCostBased(points, destination_Location, request);
					else if(algoString.equalsIgnoreCase(ConstVariables.PT_Gravitational_Distance_Algo))
							end = impl.probabilisticDistanceBased(points, destination_Location, request);
					else if(algoString.equalsIgnoreCase(ConstVariables.PT_Greedy_Algo))
						end = impl.probabilisticGreedy(points, destination_Location, request);
					else if(algoString.equalsIgnoreCase(ConstVariables.Baseline))
						end = impl.baselined(points, destination_Location, request);
				}
				
			}
		}
		

		if(request.getParameter("getPoints")!=null || request.getParameter("getPoints")!="undefined")
		{
			//call to check intersection
			System.out.println("Call check intersection");
			intersection = impl.callAndCheckIntersection(request);

			if(!intersection.equalsIgnoreCase("")){
				if(destination_Location != null || destination_Location!="undefined"){
					
					//for gravitational force cost based
					if(algoString.equalsIgnoreCase(ConstVariables.RT_Gravitational_Cost_Algo))
						end = impl.costBasedForce(intersection, destination_Location);
					
					//for greedy algorithm
					else if(algoString.equalsIgnoreCase(ConstVariables.RT_Greedy_Algo))
						end = impl.greedyAlgorithm(intersection, destination_Location);
					//for gravitational force distance based
					else if(algoString.equalsIgnoreCase(ConstVariables.RT_Gravitational_Distance_Algo))
						end = impl.distanceBasedForce(intersection,destination_Location);
					//for probabilistic cost based
					else if(algoString.equalsIgnoreCase(ConstVariables.PT_Gravitational_Cost_Algo))
						end = impl.probabilisticCostBased(intersection, destination_Location, request);
					//for probabilistic distance based
					else if(algoString.equalsIgnoreCase(ConstVariables.PT_Gravitational_Distance_Algo))
						end = impl.probabilisticDistanceBased(intersection, destination_Location, request);
					//probabilistic greedy algo
					else if(algoString.equalsIgnoreCase(ConstVariables.PT_Greedy_Algo))
						end = impl.probabilisticGreedy(intersection, destination_Location, request);
					//for baseline
					else if(algoString.equalsIgnoreCase(ConstVariables.Baseline))
						end = impl.baselined(intersection, destination_Location, request);
				}
			}
			
		}

		//writing to JSON object
		System.out.println("Response in Invoke Algo"+response.toString());
		write(response, end);
		
		
		
	}
	
	/*
	 * for writing the asynchronous response
	 */
	private void write(HttpServletResponse response, String end) throws IOException {
		
		//setting the content type and character encoding
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		//writing the response to Json Object
		response.getWriter().write(new Gson().toJson(end));
		
	}
	
	

}
