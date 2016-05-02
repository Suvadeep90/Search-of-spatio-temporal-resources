package com.edu.uic.cs581.team3.beans;

public class Elements {

	private TimeDuration duration;

    private LocationDistance distance;

    private String status;

    public TimeDuration getDuration ()
    {
        return duration;
    }

    public void setDuration (TimeDuration duration)
    {
        this.duration = duration;
    }

    public LocationDistance getDistance ()
    {
        return distance;
    }

    public void setDistance (LocationDistance distance)
    {
        this.distance = distance;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [duration = "+duration+", distance = "+distance+", status = "+status+"]";
    }
}
