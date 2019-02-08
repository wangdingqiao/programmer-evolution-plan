package com.learningjava.serializationPerformance.tester;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.learningjava.serializationPerformance.common.BeerArray;
import com.learningjava.serializationPerformance.common.SerializationTester;

public class XMLSerializationTester extends SerializationTester
{
	@Override
	public String getName()
	{
		return "XML";
	}
	
	@Override
	public BeerArray deserialze(byte[] bytes) throws IOException, ClassNotFoundException, JAXBException
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		JAXBContext jaxbContext = JAXBContext.newInstance(BeerArray.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		BeerArray beers = (BeerArray) jaxbUnmarshaller.unmarshal(bais);
		bais.close();
		return beers;
	}

	@Override
	public byte[] serialize(BeerArray beerArray) throws Exception
	{
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JAXBContext jaxbContext = JAXBContext.newInstance(BeerArray.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.marshal(beerArray, baos);
        //marshaller.marshal(beers, new File("example.xml"));
        baos.close();
        return baos.toByteArray();
	}
	
	public BeerArray loadFile(File file) throws JAXBException, IOException
	{
        JAXBContext jaxbContext = JAXBContext.newInstance(BeerArray.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BeerArray beerArray = (BeerArray) unmarshaller.unmarshal(file);
        if(beerArray.isEmpty())
        {
        	throw new IOException("Error, Failed to load data from file " + file);
        }
		return beerArray;
	}
}
