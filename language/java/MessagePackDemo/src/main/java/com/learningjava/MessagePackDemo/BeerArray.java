package com.learningjava.MessagePackDemo;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ArrayOfBeer")
public class BeerArray implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Beer> beers;
	
	public BeerArray()
	{
		beers = new ArrayList<>();
	}
	
	@XmlElement(name = "Beer")
	public ArrayList<Beer> getBeers()
	{
		return beers;
	}

	public void setBeers(ArrayList<Beer> beers)
	{
		this.beers = beers;
	}

	@Override
	public String toString()
	{
		return "Beers [beers=" + beers + "]";
	}
	
	public void addBeer(Beer beer)
	{
		this.beers.add(beer);
	}
	
	public boolean isEmpty()
	{
		return beers.isEmpty();
	}
	
	public int  size()
	{
		return beers.size();
	}
}