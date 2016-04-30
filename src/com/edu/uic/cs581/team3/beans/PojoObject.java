package com.edu.uic.cs581.team3.beans;

public class PojoObject
{
    private String[][] distance_table;

    public String[][] getDistance_table ()
    {
        return distance_table;
    }

    public void setDistance_table (String[][] distance_table)
    {
        this.distance_table = distance_table;
    }
    
    public String getDistance(){
    	return distance_table[0][1];
    }

    @Override
    public String toString()
    {
        return "ClassPojo [distance_table = "+distance_table+"]";
    }
}