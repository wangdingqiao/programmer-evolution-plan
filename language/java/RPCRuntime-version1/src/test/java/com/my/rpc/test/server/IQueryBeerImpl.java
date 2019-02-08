package com.my.rpc.test.server;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.rpc.runtime.builtInMessage.RPCCommonMessage.MapStrInt;
import com.my.rpc.runtime.builtInMessage.RPCCommonMessage.SeqInt;
import com.my.rpc.runtime.builtInMessage.RPCCommonMessage.SeqString;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCBinary;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCFloat;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCInt;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCString;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;
import com.my.rpc.test.beer.Beer;
import com.my.rpc.test.beer.BeerArray;
import com.my.rpc.testMessage.IBeerQueryServerStub;
import com.my.rpc.testMessage.MessageBeerArray;

public class IQueryBeerImpl extends IBeerQueryServerStub
{
	private static Logger logger = LogManager.getLogger(IQueryBeerImpl.class);

	@Override
	public MessageBeerArray.BeerArray queryBeers(RPCFloat param1, RPCString param2, SeqString param3, MapStrInt param4, RPCBinary param5,
			SeqInt param6) throws RPCExceptionBase
	{
		try
		{
			logger.debug("queryBeers input params: " + param1.toString() + ", " + param2.toString() + ", " 
					+ param3.toString() + ", " + param4.toString() 
					+ ", " + param5.toString() + ", "  + param6.toString());
			MessageBeerArray.BeerArray mBeerArray = loadBeers(new File("src/test/beers.xml"), 2);
			logger.debug("queryBeers output:" + mBeerArray.toString());
			return mBeerArray;
		}
		catch (JAXBException e) 
		{
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_IO_Exception, e);
		}
		catch (Exception e ) 
		{
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_InterfaceRuntime_Exception, e);
		}
	}
	
	private MessageBeerArray.BeerArray loadBeers(File file, int amount) throws JAXBException, IOException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(BeerArray.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BeerArray beerArray = (BeerArray) unmarshaller.unmarshal(file);
        if(beerArray.isEmpty())
        {
        	throw new IOException("Error, Failed to load data from file " + file);
        }
        MessageBeerArray.BeerArray mBeerArray = new MessageBeerArray.BeerArray();
		for(Beer beer:beerArray.getBeers())
		{
			MessageBeerArray.Beer mBeer = new MessageBeerArray.Beer();
			mBeer.Alcohol.valueFromFloat(beer.getAlcohol());
			mBeer.Brand.valueFromString(beer.getBrand());
			mBeer.Brewery.valueFromString(beer.getBrewery());
			for(String sortName: beer.getSort().getNames())
			{
				mBeer.Sort.names.add(new RPCString(sortName));
			}
			mBeerArray.beers.add(mBeer);
			if(mBeerArray.beers.size() >= amount)
			{
				break;
			}
		}
		return mBeerArray;
	}

	@Override
	public void voidReturnTest(RPCInt param1) throws RPCExceptionBase
	{
		System.out.println("voidReturnTest executed param: " + param1);
	}

	@Override
	public void exceptionTest() throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_InterfaceRuntime_Exception, "This is a Exception Test.");
	}
}
