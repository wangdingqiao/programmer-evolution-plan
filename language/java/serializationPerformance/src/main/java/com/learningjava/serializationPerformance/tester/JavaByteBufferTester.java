package com.learningjava.serializationPerformance.tester;

import java.nio.ByteBuffer;

import com.learningjava.serializationPerformance.common.Beer;
import com.learningjava.serializationPerformance.common.BeerArray;
import com.learningjava.serializationPerformance.common.SerializationTester;

public class JavaByteBufferTester extends SerializationTester
{
	@Override
	public String getName()
	{
		return "ByteBuffer";
	}
	@Override
	public byte[] serialize(BeerArray beerArray) throws Exception
	{
		java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(128 * beerArray.size()); // need enough buffer, or will cause java.nio.BufferOverflowException
		buffer.putInt(beerArray.size());
		for(Beer beer: beerArray.getBeers())
		{
			this.byteBuffPutString(beer.getBrand(), buffer);
			buffer.putInt(beer.getSort().size());
			for(String sortName: beer.getSort().getNames())
			{
				this.byteBuffPutString(sortName, buffer);
			}
			buffer.putFloat(beer.getAlcohol());
			this.byteBuffPutString(beer.getBrewery(), buffer);
		}
		byte[] result = new byte[buffer.position()];
		System.arraycopy(buffer.array(),0,result,0,buffer.position());
		return result;
	}

	@Override
	public BeerArray deserialze(byte[] bytes) throws Exception
	{
		java.nio.ByteBuffer buffer = java.nio.ByteBuffer.wrap(bytes);
		int beerSize = buffer.getInt();
		BeerArray beerArray = new BeerArray();
		for(int i = 0; i < beerSize; ++i)
		{
			Beer beer = new Beer();
			beer.setBrand(this.byteBuffGetString(buffer));
			int sortSize = buffer.getInt();
			for(int j = 0; j < sortSize;++j)
			{
				beer.getSort().add(this.byteBuffGetString(buffer));
			}
			beer.setAlcohol(buffer.getFloat());
			beer.setBrewery(this.byteBuffGetString(buffer));
			beerArray.addBeer(beer);
		}
		return beerArray;
	}
	
	private void byteBuffPutString(String text, ByteBuffer buff)
	{
		byte[] bytes = text.getBytes();
		buff.putInt(bytes.length);
		buff.put(bytes);
	}
	
	private String byteBuffGetString(ByteBuffer buff)
	{
		int length = buff.getInt();
		byte[] bytes = new byte[length];
		buff.get(bytes);
		return new String(bytes);
	}
}
