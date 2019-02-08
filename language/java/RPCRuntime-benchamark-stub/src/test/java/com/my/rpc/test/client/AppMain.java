package com.my.rpc.test.client;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import com.my.rpc.runtime.builtInMessage.RPCCommonMessage;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCBinary;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCFloat;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCInt;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCString;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.runner.RPCClient;
import com.my.rpc.testMessage.IBeerQuery;
import com.my.rpc.testMessage.IBeerQueryClientStub;
import com.my.rpc.testMessage.MessageBeerArray.BeerArray;

public class AppMain
{
	public static void main(String[] args) throws UnknownHostException
	{
	    LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
	    File file = new File("log4j2.xml");
	    // this will force a reconfiguration
	    context.setConfigLocation(file.toURI());
		RPCClient client = new RPCClient();
		client.registStub(new IBeerQueryClientStub(InetAddress.getLocalHost(), 2048));
		IBeerQueryClientStub stub = (IBeerQueryClientStub) client.getStub(IBeerQuery.class);
		RPCFloat param1 = new RPCFloat(1.0f);
	    RPCString param2 = new RPCString("RPCDemo");
	    RPCCommonMessage.SeqString param3 = new RPCCommonMessage.SeqString();
	    param3.elements.add(new RPCString("Hello"));
	    param3.elements.add(new RPCString("World"));
	    RPCCommonMessage.MapStrInt param4 = new RPCCommonMessage.MapStrInt();
	    param4.str2Int.put(new RPCString("Good Morninig."), new RPCInt(9));
	    param4.str2Int.put(new RPCString("Good Evening."), new RPCInt(18));
	    RPCBinary param5 = new RPCBinary();
	    param5.valueFromBytes(new byte[] {1, 2, 3, 4});
	    RPCCommonMessage.SeqInt param6 = new RPCCommonMessage.SeqInt();
	    param6.elements.add(new RPCInt(10));
	    param6.elements.add(new RPCInt(20));
	    param6.elements.add(new RPCInt(30));
		try
		{
			System.out.println("send  params: " + param1.toString() + ", " + param2.toString() + ", " 
					+ param3.toString() + ", " + param4.toString() 
					+ ", " + param5.toString() + ", "  + param6.toString());
			BeerArray mBeerArray =  stub.queryBeers(param1, param2, param3, param4, param5, param6);
			System.out.println("receieve content= " + mBeerArray.toString());
		} catch (RPCExceptionBase e)
		{
			e.printStackTrace();
		}
	}
}
