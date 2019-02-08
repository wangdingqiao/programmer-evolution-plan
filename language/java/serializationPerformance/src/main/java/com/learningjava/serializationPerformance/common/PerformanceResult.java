package com.learningjava.serializationPerformance.common;

public class PerformanceResult
{
	private String serializeMethod;
	private float  serializeAvgSpeed = 0;
	private float  deserailzeAvgSpeed = 0;
	private long   objectSize = 0;
	private Exception exception;
	
	@Override
	public String toString()
	{
		if(exception == null)
		{
			return "{\"method\":\"" + serializeMethod 
					+ "\",\"serAvgSpeed\":" + String.format("%.2f", serializeAvgSpeed)
					+ ", \"deserAvgSpeed\":" + String.format("%.2f", deserailzeAvgSpeed) 
					+ ", \"data size\":" + objectSize + "}";
		}
		else
		{
			return "{\"method\":" + serializeMethod 
					+ "\"failed, exception\":\"" + exception + "\"}";
		}
	}

	public PerformanceResult()
	{
		this.exception = null;
	}
	
	public Exception getException()
	{
		return exception;
	}
	public void setException(Exception exception)
	{
		this.exception = exception;
	}
	public String getSerializeMethod()
	{
		return serializeMethod;
	}
	public void setSerializeMethod(String serializeMethod)
	{
		this.serializeMethod = serializeMethod;
	}
	public float getSerializeAvgSpeed()
	{
		return serializeAvgSpeed;
	}
	public void setSerializeAvgSpeed(float serializeAvgSpeed)
	{
		this.serializeAvgSpeed = serializeAvgSpeed;
	}
	public float getDeserailzeAvgSpeed()
	{
		return deserailzeAvgSpeed;
	}
	public void setDeserailzeAvgSpeed(float deserailzeAvgSpeed)
	{
		this.deserailzeAvgSpeed = deserailzeAvgSpeed;
	}
	public long getObjectSize()
	{
		return objectSize;
	}
	public void setObjectSize(long objectSize)
	{
		this.objectSize = objectSize;
	}
}