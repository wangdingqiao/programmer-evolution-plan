package com.my.rpc.test.beer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Sort")
@XmlAccessorType(XmlAccessType.FIELD)  
public class BeerSort implements Serializable
{
	private static final long serialVersionUID = 1L;
	@XmlElement(name="string")
	public List<String> names;
	public List<String> getNames()
	{
		return names;
	}
	
	public void setNames(List<String> sortNames)
	{
		this.names = sortNames;
	}

	public int size()
	{
		return this.names.size();
	}
	public void add(String sortName)
	{
		this.names.add(sortName);
	}
	@Override
	public String toString()
	{
		return this.names.toString();
	}
	
	public BeerSort()
	{
		names = new ArrayList<>();
	}
}
