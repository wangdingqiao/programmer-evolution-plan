package com.learningjava.serializationPerformance.tester;

import java.io.File;
import java.io.FileOutputStream;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import com.learningjava.serializationPerformance.common.Beer;
import com.learningjava.serializationPerformance.common.BeerArray;
import com.learningjava.serializationPerformance.common.SerializationTester;

public class MsgPackSerializationTester extends SerializationTester
{
	private MessageBufferPacker packer;
	
	public MsgPackSerializationTester()
	{
		this.packer = MessagePack.newDefaultBufferPacker();
	}
	
	@Override
	public String getName()
	{
		return "MessagePack";
	}
	
	@Override
	public byte[] serialize(BeerArray beerArray) throws Exception
	{
		packer.clear();
		packer.packInt(beerArray.size());
		for(Beer beer: beerArray.getBeers())
		{
			packer.packString(beer.getBrand());
			packer.packInt(beer.getSort().size());
			for(String sortName: beer.getSort().getNames())
			{
				packer.packString(sortName);
			}
			packer.packFloat(beer.getAlcohol());
			packer.packString(beer.getBrewery());
		}
		return packer.toByteArray();
	}

	@Override
	public BeerArray deserialze(byte[] bytes) throws Exception
	{
		BeerArray beerArray = new BeerArray();
		MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bytes);
		int beerSize = unpacker.unpackInt();
		for(int i = 0; i < beerSize; ++i)
		{
			Beer beer = new Beer();
			beer.setBrand(unpacker.unpackString());
			int sortSize = unpacker.unpackInt();
			for(int j = 0; j < sortSize;++j)
			{
				beer.getSort().add(unpacker.unpackString());
			}
			beer.setAlcohol(unpacker.unpackFloat());
			beer.setBrewery(unpacker.unpackString());
			beerArray.addBeer(beer);
		}
		return beerArray;
	}
	
}
