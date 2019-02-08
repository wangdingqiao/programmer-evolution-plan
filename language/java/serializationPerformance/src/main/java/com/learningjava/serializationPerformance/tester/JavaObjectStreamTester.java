package com.learningjava.serializationPerformance.tester;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.learningjava.serializationPerformance.common.BeerArray;
import com.learningjava.serializationPerformance.common.SerializationTester;

public class JavaObjectStreamTester extends SerializationTester
{
	@Override
	public String getName()
	{
		return "ObjectStream";
	}
	@Override
	public BeerArray deserialze(byte[] bytes) throws IOException, ClassNotFoundException
	{
		BeerArray object = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		object = (BeerArray)ois.readObject();
		bais.close();
		ois.close();
		if(object == null)
		{
			throw new IOException("java object deserialize failed.");
		}
		return object;
	}

	@Override
	public byte[] serialize(BeerArray beerArray) throws Exception
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(beerArray);
		oos.close();
		baos.close();
		return baos.toByteArray();
	}
}
