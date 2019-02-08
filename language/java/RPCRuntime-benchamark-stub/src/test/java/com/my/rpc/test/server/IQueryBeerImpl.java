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
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCString;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;
import com.my.rpc.test.beer.*;
import com.my.rpc.testMessage.IBeerQueryServerStub;
import com.my.rpc.testMessage.MessageBeerArray;

public class IQueryBeerImpl extends IBeerQueryServerStub
{
	private static Logger logger = LogManager.getLogger(IQueryBeerImpl.class);
	private MessageBeerArray.BeerArray mBeerArray = null;

	@Override
	public MessageBeerArray.BeerArray queryBeers(RPCFloat param1, RPCString param2, SeqString param3, MapStrInt param4, RPCBinary param5,
			SeqInt param6) throws RPCExceptionBase
	{
		try
		{
			if(this.mBeerArray == null)
			{
				mBeerArray = loadBeers(new File("src/test/beers.xml"), 100);
			}
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
        	logger.error("Error, Failed to load data from file " + file);
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
	public com.my.rpc.testMessage.MessageBeerArray.BeerArray queryBeers2(RPCFloat param1, RPCString param2,
			SeqString param3) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		try
		{
			if(this.mBeerArray == null)
			{
				mBeerArray = loadBeers(new File("src/test/beers.xml"), 100);
			}
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

	@Override
	public com.my.rpc.testMessage.MessageBeerArray.BeerArray queryBeers3(RPCFloat param1, RPCString param2,
			SeqString param3, MapStrInt param4) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		try
		{
			if(this.mBeerArray == null)
			{
				mBeerArray = loadBeers(new File("src/test/beers.xml"), 100);
			}
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
}
