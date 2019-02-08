package com.learningjava.MessagePackDemo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ServiceImpl implements IServiceTest
{
	
	
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

	@Override
	public BeerArray queryBeers(float param1, String param2, ArrayList<String> param3, HashMap<String, Integer> param4, byte[] param5, int[] param6) throws JAXBException, IOException
	{
		BeerArray beerArray = this.loadFile(new File("beers.xml"));
		BeerArray retArray = new BeerArray();
		for(int i=0;i < 100; i++)
		{
			retArray.addBeer(beerArray.getBeers().get(i));
		}
		return retArray;
	}
}
