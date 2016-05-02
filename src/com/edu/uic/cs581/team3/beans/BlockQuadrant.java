package com.edu.uic.cs581.team3.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.edu.uic.cs581.team3.constants.ConstVariables;
import com.edu.uic.cs581.team3.database.FetchPoints;
import com.edu.uic.cs581.team3.database.Force;

public class BlockQuadrant {

	
	private ArrayList<BlockAvailableQuadrant> q1 = new ArrayList<BlockAvailableQuadrant>();
	private ArrayList<BlockAvailableQuadrant> q2 = new ArrayList<BlockAvailableQuadrant>();
	private ArrayList<BlockAvailableQuadrant> q3 = new ArrayList<BlockAvailableQuadrant>();
	private ArrayList<BlockAvailableQuadrant> q4 = new ArrayList<BlockAvailableQuadrant>();
	BlockAvailableQuadrant qq;
	Statement stmt;
	Connection con = null;
	String selectCoordinates;
	LocationCoordinates c1;
	private FetchPoints points = new FetchPoints();
	
	public BlockQuadrant() {
		// TODO Auto-generated constructor stub
		//catch all the coordinates
		points.getCoordinates();

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/sqlserv");
			con = ds.getConnection();
			//con = DriverManager.getConnection(ConstData.DATABASE_URL,ConstData.DATABASE_USER,ConstData.DATABASE_PWD);
			stmt = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void firstQuadrant(Double latitude, Double longitude)
	{
				
		Double intersectionLat = latitude;
		Double intersectionLong = longitude;
				
		
		LocationMapDistance distanceMap = new Force().pointWeight(intersectionLat,intersectionLong, FetchPoints.point);
		////system.out.println("distanceMap.getDist(): "+distanceMap.getDist());
		if(distanceMap.getDist() < ConstVariables.Threshhold_Value)
		{
			intersectionLat = latitude;
			intersectionLong = longitude;
		}
		
		String date = new SimpleDateFormat(ConstVariables.Date_Format).format(new Date());
		date = date.replace("2015", "2012");
		
		selectCoordinates = " select a.block_id, a.available available, a.distance,a.block_latitude latitude,a.block_longitude longitude, driving_time "+
				" from "+
				" (SELECT a.block_id, a.available, b.distance,b.time as driving_time, b.block_latitude,b.block_longitude, "+
						" row_number() over ( partition by a.block_id order by DATEANDTIME desc ) as RowNum  "+
						" FROM dbprojection a JOIN distance b ON a.block_id = b.block_id "+
						" where DATEANDTIME < \'" + date + "\'" +
						" and intersection_latitude = " + intersectionLat +
						" and intersection_longitude = "+ intersectionLong +
						" ) a where RowNum = 1 " + 
						" and available <> 0";

		ResultSet rs;
		try {
			rs = stmt.executeQuery(selectCoordinates);
			while(rs.next())
			{
				if(intersectionLat > rs.getDouble("latitude") && intersectionLong < rs.getDouble("longitude"))


							c1 = new LocationCoordinates();
							qq = new BlockAvailableQuadrant();
							c1.setLatitude(rs.getDouble("latitude"));
							c1.setLongitude(rs.getDouble("longitude"));
							qq.setC1(c1);
							qq.setAvailable(rs.getDouble("available"));
							q1.add(qq);
				
			}	
				
				
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		

	}
	
	public void secondQuadrant(Double latitude, Double longitude)
	{
		

		
		Double intersectionLat = latitude;
		Double intersectionLong = longitude;
								
		LocationMapDistance distanceMap = new Force().pointWeight(intersectionLat,intersectionLong, FetchPoints.point);
		////system.out.println("distanceMap.getDist(): "+distanceMap.getDist());
		if(distanceMap.getDist() < ConstVariables.Threshhold_Value)
		{
			intersectionLat = latitude;
			intersectionLong = longitude;
		}
		
		
		String date = new SimpleDateFormat(ConstVariables.Date_Format).format(new Date());
		date = date.replace("2015", "2012");
		
		selectCoordinates =  " select a.block_id, a.available available, a.distance,a.block_latitude latitude,a.block_longitude longitude, driving_time "+
				" from "+
				" (SELECT a.block_id, a.available, b.distance,b.time as driving_time, b.block_latitude,b.block_longitude, "+
						" row_number() over ( partition by a.block_id order by DATEANDTIME desc ) as RowNum  "+
						" FROM dbprojection a JOIN distance b ON a.block_id = b.block_id "+
						" where DATEANDTIME < \'" + date + "\'" +
						" and intersection_latitude = " + intersectionLat +
						" and intersection_longitude = "+ intersectionLong +
						" ) a where RowNum = 1 " + 
						" and available <> 0";

		
		ResultSet rs;
		try {
			rs = stmt.executeQuery(selectCoordinates);
			while(rs.next())
			{
				if(intersectionLat > rs.getDouble("latitude") && intersectionLong > rs.getDouble("longitude"))


							c1 = new LocationCoordinates();
							qq = new BlockAvailableQuadrant();
							c1.setLatitude(rs.getDouble("latitude"));
							c1.setLongitude(rs.getDouble("longitude"));
							qq.setC1(c1);
							qq.setAvailable(rs.getDouble("available"));
							q2.add(qq);
				
			}	
				
				
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		

	}
	

	public void thirdQuadrant(Double latitude, Double longitude)
	{
		

		
		Double intersectionLat = latitude;
		Double intersectionLong = longitude;
								
		LocationMapDistance distanceMap = new Force().pointWeight(intersectionLat,intersectionLong, FetchPoints.point);
		////system.out.println("distanceMap.getDist(): "+distanceMap.getDist());
		if(distanceMap.getDist() < ConstVariables.Threshhold_Value)
		{
			intersectionLat = latitude;
			intersectionLong = longitude;
		}
		
		
		String date = new SimpleDateFormat(ConstVariables.Date_Format).format(new Date());
		date = date.replace("2015", "2012");
		
		selectCoordinates =  " select a.block_id, a.available available, a.distance,a.block_latitude latitude,a.block_longitude longitude, driving_time "+
				" from "+
				" (SELECT a.block_id, a.available, b.distance,b.time as driving_time, b.block_latitude,b.block_longitude, "+
						" row_number() over ( partition by a.block_id order by DATEANDTIME desc ) as RowNum  "+
						" FROM dbprojection a JOIN distance b ON a.block_id = b.block_id "+
						" where DATEANDTIME < \'" + date + "\'" +
						" and intersection_latitude = " + intersectionLat +
						" and intersection_longitude = "+ intersectionLong +
						" ) a where RowNum = 1 " + 
						" and available <> 0";

		ResultSet rs;
		try {
			rs = stmt.executeQuery(selectCoordinates);
			while(rs.next())
			{
				if(intersectionLat < rs.getDouble("latitude") && intersectionLong > rs.getDouble("longitude"))


							c1 = new LocationCoordinates();
							qq = new BlockAvailableQuadrant();
							c1.setLatitude(rs.getDouble("latitude"));
							c1.setLongitude(rs.getDouble("longitude"));
							qq.setC1(c1);
							qq.setAvailable(rs.getDouble("available"));
							q3.add(qq);
				
			}	
				
				
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}
	
	
	public void forthQuadrant(Double latitude, Double longitude)
	{
		
		
		
		Double intersectionLat = latitude;
		Double intersectionLong = longitude;
								
		LocationMapDistance distanceMap = new Force().pointWeight(intersectionLat,intersectionLong, FetchPoints.point);
		////system.out.println("distanceMap.getDist(): "+distanceMap.getDist());
		if(distanceMap.getDist() < ConstVariables.Threshhold_Value)
		{
			intersectionLat = latitude;
			intersectionLong = longitude;
		}
		
		String date = new SimpleDateFormat(ConstVariables.Date_Format).format(new Date());
		date = date.replace("2015", "2012");
		
		selectCoordinates =  " select a.block_id, a.available available, a.distance,a.block_latitude latitude,a.block_longitude longitude, driving_time "+
				" from "+
				" (SELECT a.block_id, a.available, b.distance,b.time as driving_time, b.block_latitude,b.block_longitude, "+
						" row_number() over ( partition by a.block_id order by DATEANDTIME desc ) as RowNum  "+
						" FROM dbprojection a JOIN distance b ON a.block_id = b.block_id "+
						" where DATEANDTIME < \'" + date + "\'" +
						" and intersection_latitude = " + intersectionLat +
						" and intersection_longitude = "+ intersectionLong +
						" ) a where RowNum = 1 " + 
						" and available <> 0";

		ResultSet rs;
		try {
			rs = stmt.executeQuery(selectCoordinates);
			while(rs.next())
			{
				if(intersectionLat < rs.getDouble("latitude") && intersectionLong < rs.getDouble("longitude"))



							c1 = new LocationCoordinates();
							qq = new BlockAvailableQuadrant();
							c1.setLatitude(rs.getDouble("latitude"));
							c1.setLongitude(rs.getDouble("longitude"));
							qq.setC1(c1);
							qq.setAvailable(rs.getDouble("available"));
							q4.add(qq);

				
			}	
				
				
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}
	
	
	public ArrayList<BlockAvailableQuadrant> maxAvailable(Double latitude, Double longitude)
	{
		int sum[] = new int[4];
		
		firstQuadrant(latitude,longitude);
		secondQuadrant(latitude,longitude);
		thirdQuadrant(latitude,longitude);
		forthQuadrant(latitude,longitude);
		
		for( BlockAvailableQuadrant i : q1)
		{
			sum[0] += i.getAvailable();
			
		}
		for( BlockAvailableQuadrant i : q2)
		{
			sum[1] += i.getAvailable();
			
		}
		for( BlockAvailableQuadrant i : q3)
		{
			sum[2] += i.getAvailable();
			
		}
		for( BlockAvailableQuadrant i : q4)
		{
			sum[3] += i.getAvailable();
			
		}
		
		int max = sum[0];
		int flag = 0;
		for(int i = 1; i< 4; i++)
		{
			if(max < sum[i])
			{
				flag = i;
				max = sum[i];
			}
			
		}
		
		if(flag == 0)		
		return q1;
		else if(flag == 1)		
		return q2;
		else if(flag == 2)		
		return q3;
		else		
		return q4;
		
	}
	

}
