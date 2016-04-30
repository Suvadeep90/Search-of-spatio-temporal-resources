package com.edu.uic.cs581.team3.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.edu.uic.cs581.team3.beans.Coordinates;

public class ParkingChoosePoints {

	
public static ArrayList<Coordinates> midPoint;
	
	public void getCoordinates()
	{
		
		
		Statement stmt = null;
		Coordinates cds2;
		midPoint = new ArrayList<Coordinates>();

			
		Connection con = null;
		
  
		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/sqlserv");
			con = ds.getConnection();
			stmt = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		String selectCoordinates = "select distinct dest_block_latitude, dest_block_longitude from WalkingTime";
		
		
		try {
			ResultSet res2 = stmt.executeQuery(selectCoordinates);
			while(res2.next())
			{
				cds2 = new Coordinates();
				cds2.setLatitude(res2.getDouble("dest_block_latitude"));
				cds2.setLongitude(res2.getDouble("dest_block_longitude"));
				midPoint.add(cds2);
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
