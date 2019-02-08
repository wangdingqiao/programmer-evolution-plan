package com.learningjava.serializationPerformance.common;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Beer")
@XmlAccessorType(XmlAccessType.FIELD)  
public class Beer implements Serializable
{
	private static final long serialVersionUID = 1L;
	public String Brand;
	@XmlElement(name="Sort")
    public BeerSort Sort;
	public float Alcohol;
    public String Brewery;
    
    public Beer()
    {
    	this.Sort = new BeerSort();
    }
    
	public String getBrand()
	{
		return Brand;
	}
	public void setBrand(String brand)
	{
		Brand = brand;
	}
	public BeerSort getSort()
	{
		return Sort;
	}
	public void setSort(BeerSort sort)
	{
		Sort = sort;
	}
	public float getAlcohol()
	{
		return Alcohol;
	}
	public void setAlcohol(float alcohol)
	{
		Alcohol = alcohol;
	}
	public String getBrewery()
	{
		return Brewery;
	}
	public void setBrewery(String brewery)
	{
		Brewery = brewery;
	}
	
	@Override
	public String toString()
	{
		return "Beer [Brand=" + Brand + ", Sort=" + Sort + ", Alcohol=" + Alcohol + ", Brewery=" + Brewery + "]";
	}
}
