package com.learningjava.serializationPerformance.common;

public abstract class SerializationTester
{
	public abstract byte[] serialize(BeerArray beerArray) throws Exception;
	public abstract BeerArray deserialze(byte[] bytes) throws Exception;
	
	public String getName()
	{
		return this.getClass().getSimpleName();
	}
	
	public boolean isWorking(BeerArray inputObj)
	{
		try
		{
			byte[] output = this.serialize(inputObj);
			BeerArray  deserObj = this.deserialze(output);
			if(inputObj.size() == deserObj.size() && inputObj.toString().equals(deserObj.toString()))
			{
				return true;
			}
			else
			{
				System.out.println("Error, Not Match, inputObj: " + inputObj.toString() + "\n deserObj: " + deserObj.toString());
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public PerformanceResult run(BeerArray beerArray, int times)
	{
		PerformanceResult result = new PerformanceResult();
		try 
		{
			result.setSerializeMethod(this.getName());
			byte[] bytes = this.serialize(beerArray);
			result.setObjectSize(bytes.length);
			
			long start = System.currentTimeMillis();
			for(int i = 0; i < times; ++i)
			{
				this.serialize(beerArray);
			}
			long elapsedMs = System.currentTimeMillis() - start;
			result.setSerializeAvgSpeed(1 / (elapsedMs / (float)times));
			
			start = System.currentTimeMillis();
			for(int i = 0; i < times; ++i)
			{
				this.deserialze(bytes);
			}
			elapsedMs = System.currentTimeMillis() - start;
			result.setDeserailzeAvgSpeed(1 / (elapsedMs / (float)times));
		}
		catch (Exception e) {
			result.setException(e);
		}
		return result;
	}
	@Override
	public String toString()
	{
		return this.getName();
	}
}
