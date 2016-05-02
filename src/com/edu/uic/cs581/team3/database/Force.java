package com.edu.uic.cs581.team3.database;

import java.util.ArrayList;

import com.edu.uic.cs581.team3.beans.LocationCoordinates;
import com.edu.uic.cs581.team3.beans.LocationMapDistance;
import com.edu.uic.cs581.team3.constants.ConstVariables;


public class Force {

	
	public LocationMapDistance pointWeight(Double latitude, Double longitude, ArrayList<LocationCoordinates> point)
	{
		LocationMapDistance d ;
		Double dist;
		ArrayList<LocationMapDistance> distPoint = new ArrayList<LocationMapDistance>();
		LocationMapDistance coodinate = new LocationMapDistance();
		
		for(LocationCoordinates i : point)
		{
			d = new LocationMapDistance();
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
		for(LocationMapDistance i : distPoint)
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
