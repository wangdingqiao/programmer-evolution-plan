package com.learningjava.serializationPerformance.tester;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.learningjava.serializationPerformance.common.Beer;
import com.learningjava.serializationPerformance.common.BeerArray;
import com.learningjava.serializationPerformance.common.SerializationTester;
import de.undercouch.bson4jackson.BsonFactory;
import de.undercouch.bson4jackson.BsonGenerator;

public class BSONSerializationTester extends SerializationTester
{
	private BsonFactory fac;
	public BSONSerializationTester()
	{
		this.fac = new BsonFactory();
		ObjectMapper mapper = new ObjectMapper(fac);   // 这行非常影响性能 因此放在构造函数里
		fac.setCodec(mapper);
	}
	@Override
	public String getName()
	{
		return "BSON";
	}
	@Override
	public byte[] serialize(BeerArray beerArray) throws Exception
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BsonGenerator  gen = this.fac.createGenerator(baos);
		gen.writeStartArray();
		for(Beer beer: beerArray.getBeers())
		{
			gen.writeStartObject();
			gen.writeStringField("brand", beer.getBrand());
			gen.writeNumberField("sortSize", beer.getSort().size());
			gen.writeFieldName("sort");
			gen.writeStartArray();
			for(String sortName: beer.getSort().getNames())
			{
				gen.writeString(sortName);
			}
			gen.writeEndArray();
			gen.writeNumberField("alcohol", beer.getAlcohol());
			gen.writeStringField("brewery", beer.getBrewery());
			gen.writeEndObject();
		}
		gen.writeEndArray();
		gen.close();
		baos.close();
		return baos.toByteArray();
	}

	@Override
	public BeerArray deserialze(byte[] bytes) throws Exception
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		JsonParser  parser = this.fac.createJsonParser(bais);
		TreeNode node = parser.getCodec().readTree(parser);
		BeerArray beerArray = new BeerArray();
		for(int i = 0; i < node.size(); ++i)
		{
			TreeNode curNode = node.get(String.valueOf(i));
			Beer beer = new Beer();
			beer.setBrand(((TextNode)curNode.get("brand")).asText());
			TreeNode sortNode = curNode.get("sort");
			for(int j = 0; j < sortNode.size();++j)
			{
				TreeNode subNode = sortNode.get(j);
				beer.getSort().add(((TextNode)subNode).asText());
			}
			beer.setAlcohol(Float.parseFloat(curNode.get("alcohol").toString()));
			beer.setBrewery(((TextNode)curNode.get("brewery")).asText());
			beerArray.addBeer(beer);
		}
		return beerArray;
	}
	
}
