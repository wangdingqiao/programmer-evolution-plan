package com.learningjava.serializationPerformance.tester;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.learningjava.serializationPerformance.common.Beer;
import com.learningjava.serializationPerformance.common.BeerArray;
import com.learningjava.serializationPerformance.common.SerializationTester;

public class JSONSerializationTester extends SerializationTester
{
	private ObjectMapper mapper;
	
	public JSONSerializationTester()
	{
		this.mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(BeerArray.class, new BeerArrayJsonDeserializer());
		module.addSerializer(new BeerArrayJsonSerializer());
		this.mapper.registerModule(module); 
	}
	
	@Override
	public String getName()
	{
		return "JSON";
	}
	
	@Override
	public BeerArray deserialze(byte[] bytes) throws JsonParseException, JsonMappingException, IOException
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		BeerArray beerArray = this.mapper.readValue(bais, BeerArray.class);
		return beerArray;
	}

	@Override
	public byte[] serialize(BeerArray beerArray) throws JsonGenerationException, JsonMappingException, IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		this.mapper.writeValue(baos,beerArray);
		baos.close();
		//mapper.writeValue(new File("beer" + beerArray.size() + ".json"), beerArray);
		return baos.toByteArray();
	}
}

class BeerArrayJsonSerializer extends StdSerializer<BeerArray> 
{
    
	private static final long serialVersionUID = 1L;
   
    public BeerArrayJsonSerializer() 
    {
        super(BeerArray.class);
    }

	@Override
	public void serialize(BeerArray beerArray, JsonGenerator jgen, SerializerProvider provider) throws IOException
	{
		jgen.writeStartArray();
		for(Beer beer: beerArray.getBeers())
		{
			jgen.writeStartObject();
			jgen.writeStringField("brand", beer.getBrand());
		    jgen.writeNumberField("sortSize", beer.getSort().size());
		    jgen.writeFieldName("sort");
		    jgen.writeStartArray();
			for(String sortName: beer.getSort().getNames())
			{
				jgen.writeString(sortName);
			}
			jgen.writeEndArray();
			jgen.writeNumberField("alcohol", beer.getAlcohol());
			jgen.writeStringField("brewery", beer.getBrewery());
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
	}
}

class BeerArrayJsonDeserializer extends StdDeserializer<BeerArray> 
{
	private static final long serialVersionUID = 1L;
	
	protected BeerArrayJsonDeserializer()
	{
		super(BeerArray.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BeerArray deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
	{
		TreeNode node = jp.getCodec().readTree(jp);
		BeerArray beerArray = new BeerArray();
		for(int i = 0; i < node.size(); ++i)
		{
			TreeNode curNode = node.get(i);
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

