package com.my.rpc.runtime.runner;

public class IDManager
{
	private static IDManager instance = null;
	private long id = 1;
	
	private IDManager()
	{
		
	}
	private long nextId()
	{
		++id;
		if(id == Long.MAX_VALUE)
		{
			id = 1;
		}
		return id;
	}
	
	public static long genId()
	{
		return IDManager.getInstance().nextId();
	}
	
	private static IDManager getInstance()
	{
		if(instance == null)
		{
			instance = new IDManager();
		}
		return instance;
	}
}
