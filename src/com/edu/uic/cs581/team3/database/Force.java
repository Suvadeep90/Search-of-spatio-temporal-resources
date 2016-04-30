package com.edu.uic.cs581.team3.database;

import java.util.ArrayList;

import com.edu.uic.cs581.team3.beans.Coordinates;
import com.edu.uic.cs581.team3.beans.DistanceMap;
import com.edu.uic.cs581.team3.constants.ConstVariables;


public class Force {

	
	public DistanceMap pointWeight(Double latitude, Double longitude, ArrayList<Coordinates> point)
	{
		DistanceMap d ;
		Double dist;
		ArrayList<DistanceMap> distPoint = new ArrayList<DistanceMap>();
		DistanceMap coodinate = new DistanceMap();
		
		for(Coordinates i : point)
		{
			d = new DistanceMap();
			dist=ConstVariables.Distance_Multiply * Math.sqrt(((i.getLongitude() - longitude)*(i.getLongitude() - longitude))
					+((i.getLatitude() - latitude)*(i.getLatitude() - latitude)));
			//d1.point.latitude = i.latitude;
			d.getPoint().setLatitude(i.getLatitude());
			d.getPoint().setLongitude(i.getLongitude());
			
			d.setDist(dist);
		
			distPoint.add(d);
			//System.out.println(d1.getPoint().getLatitude()+" , "+d1.getPoint().getLongitude()+" , "+d1.getDist());
			
			
		}
		
		dist = distPoint.get(0).getDist();
		
		coodinate.setDist(distPoint.get(0).getDist());
		coodinate.getPoint().setLatitude(distPoint.get(0).getPoint().getLatitude());
		coodinate.getPoint().setLongitude(distPoint.get(0).getPoint().getLongitude());
		for(DistanceMap i : distPoint)
		{
			
			if (dist > i.getDist())
			{
				dist = i.getDist();
				coodinate.getPoint().setLatitude(i.getPoint().getLatitude());
				coodinate.getPoint().setLongitude(i.getPoint().getLongitude());
				coodinate.setDist(i.getDist());
			}
		}

		return coodinate;
	}
	
	
}
