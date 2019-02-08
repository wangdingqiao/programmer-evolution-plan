package com.learningjava.serializationPerformance.tester;

import com.learningjava.serializationPerformance.common.Beer;
import com.learningjava.serializationPerformance.common.BeerArray;
import com.learningjava.serializationPerformance.common.SerializationTester;

public class ProtoBufSerializationTester extends SerializationTester
{
	
	private BeerArrayProtos.BeerArray.Builder builder;
	private BeerArrayProtos.Beer.Builder beerBuidler;
	private BeerArrayProtos.BeerSort.Builder sortBuilder;
	
	public ProtoBufSerializationTester()
	{
		builder = BeerArrayProtos.BeerArray.newBuilder();
		beerBuidler = BeerArrayProtos.Beer.newBuilder();
		sortBuilder = BeerArrayProtos.BeerSort.newBuilder();
	}
	
	@Override
	public String getName()
	{
		return "ProtoBuf";
	}
	
	@Override
	public byte[] serialize(BeerArray beerArray)
	{
		builder.clear();
		for(Beer beer: beerArray.getBeers())
		{
			beerBuidler.clear();
			beerBuidler.setBrand(beer.getBrand());
			sortBuilder.clear();
			for(String name: beer.getSort().getNames())
			{
				sortBuilder.addNames(name);
			}
			beerBuidler.setSort(sortBuilder.build());
			beerBuidler.setAlcohol(beer.getAlcohol());
			beerBuidler.setBrewery(beer.getBrewery());
			builder.addBeers(beerBuidler.build());
		}
		return builder.build().toByteArray();
	}

	@Override
	public BeerArray deserialze(byte[] bytes) throws Exception
	{
		BeerArrayProtos.BeerArray beerArrayObj = BeerArrayProtos.BeerArray.parseFrom(bytes);
		BeerArray beerArray = new BeerArray();
		for(BeerArrayProtos.Beer beerObj: beerArrayObj.getBeersList())
		{
			Beer beer = new Beer();
			beer.setBrand(beerObj.getBrand());
			for(String sortName: beerObj.getSort().getNamesList())
			{
				beer.getSort().add(sortName);
			}
			beer.setAlcohol(beerObj.getAlcohol());
			beer.setBrewery(beerObj.getBrewery());
			beerArray.addBeer(beer);
		}
		return beerArray;
	}
}
