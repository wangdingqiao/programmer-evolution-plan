package com.learningjava.serializationPerformance.tester;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.learningjava.serializationPerformance.common.Beer;
import com.learningjava.serializationPerformance.common.BeerArray;
import com.learningjava.serializationPerformance.common.SerializationTester;

public class JavaDataStreamTester extends SerializationTester
{
	@Override
	public String getName()
	{
		return "DataStream";
	}
	@Override
	public byte[] serialize(BeerArray beerArray) throws Exception
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeInt(beerArray.size());
		for(Beer beer: beerArray.getBeers())
		{
			dos.writeUTF(beer.getBrand());
			dos.writeInt(beer.getSort().size());
			for(String sortName: beer.getSort().getNames())
			{
				dos.writeUTF(sortName);
			}
			dos.writeFloat(beer.getAlcohol());
			dos.writeUTF(beer.getBrewery());
		}
		baos.close();
		dos.close();
		return baos.toByteArray();
	}

	@Override
	public BeerArray deserialze(byte[] bytes) throws Exception
	{
		BeerArray beerArray = new BeerArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		DataInputStream dis = new DataInputStream(bais);
		int beerSize = dis.readInt();
		for(int i = 0; i < beerSize; ++i)
		{
			Beer beer = new Beer();
			beer.setBrand(dis.readUTF());
			int sortSize = dis.readInt();
			for(int j = 0; j < sortSize;++j)
			{
				beer.getSort().add(dis.readUTF());
			}
			beer.setAlcohol(dis.readFloat());
			beer.setBrewery(dis.readUTF());
			beerArray.addBeer(beer);
		}
		return beerArray;
	}
}
