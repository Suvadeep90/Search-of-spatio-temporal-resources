package com.edu.uic.cs581.team3.beans;

public class Rows {

	private Elements[] elements;

    public Elements[] getElements ()
    {
        return elements;
    }

    public void setElements (Elements[] elements)
    {
        this.elements = elements;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [elements = "+elements+"]";
    }
}
