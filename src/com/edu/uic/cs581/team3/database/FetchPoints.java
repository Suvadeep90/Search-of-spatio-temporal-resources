package com.edu.uic.cs581.team3.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.edu.uic.cs581.team3.beans.LocationCoordinates;
import com.edu.uic.cs581.team3.constants.ConstVariables;


public class FetchPoints {

	public static ArrayList<LocationCoordinates> point;
	
	public void getCoordinates()
	{
		
		
		Statement stmt = null;
		LocationCoordinates cds;
		point = new ArrayList<LocationCoordinates>();

			
		Connection con = null;
		
  
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
			
		String chooseCoordinates = "select distinct latitude, longitude from sf_park_node";
		
		
		try {
			ResultSet res = stmt.executeQuery(chooseCoordinates);
			while(res.next())
			{
				cds = new LocationCoordinates();
				cds.setLatitude(res.getDouble("latitude"));
				cds.setLongitude(res.getDouble("longitude"));
				point.add(cds);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

	
}
