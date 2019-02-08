package com.my.rpc.test.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.my.rpc.runtime.builtInMessage.RPCCommonMessage;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCBinary;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCFloat;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCInt;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCString;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.runner.RPCClient;
import com.my.rpc.testMessage.IBeerQuery;
import com.my.rpc.testMessage.MessageBeerArray.BeerArray;


public class AppMain
{
	public static void main(String[] args) throws UnknownHostException
	{
		final RPCClient client = new RPCClient(InetAddress.getLocalHost(), 2048);
		final RPCFloat param1 = new RPCFloat(1.0f);
	    final RPCString param2 = new RPCString("RPCDemo");
	    final RPCCommonMessage.SeqString param3 = new RPCCommonMessage.SeqString();
	    param3.elements.add(new RPCString("Hello"));
	    param3.elements.add(new RPCString("World"));
	    final RPCCommonMessage.MapStrInt param4 = new RPCCommonMessage.MapStrInt();
	    param4.str2Int.put(new RPCString("Good Morninig."), new RPCInt(9));
	    param4.str2Int.put(new RPCString("Good Evening."), new RPCInt(18));
	    final RPCBinary param5 = new RPCBinary();
	    param5.valueFromBytes(new byte[] {1, 2, 3, 4});
	    final RPCCommonMessage.SeqInt param6 = new RPCCommonMessage.SeqInt();
	    param6.elements.add(new RPCInt(10));
	    param6.elements.add(new RPCInt(20));
	    param6.elements.add(new RPCInt(30));
	    
	    Thread thread = new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				int i = 0;
				while(i++ < 50)
				{
					try
					{
//						System.out.println("send  params: " + param1.toString() + ", " + param2.toString() + ", " 
//								+ param3.toString() + ", " + param4.toString() 
//								+ ", " + param5.toString() + ", "  + param6.toString());
						IBeerQuery proxy = client.refer(IBeerQuery.class);
						BeerArray mBeerArray = proxy.queryBeers(param1, param2, param3, param4, param5, param6);
						System.out.println("queryBeers receieve content= " + mBeerArray.toString());
						Thread.sleep(50);
					} catch (RPCExceptionBase | InterruptedException e)
					{
						System.out.println(e);
					}
				}
			}
		});
	    Thread thread2 = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				int i = 0;
				while(i++ < 50)
				{
					try
					{
//						System.out.println("send  params: " + param1.toString() + ", " + param2.toString() + ", " 
//								+ param3.toString() + ", " + param4.toString() 
//								+ ", " + param5.toString() + ", "  + param6.toString());
						IBeerQuery proxy = client.refer(IBeerQuery.class);
						BeerArray mBeerArray = proxy.queryBeers2(param1, param2, param3);
						System.out.println("queryBeers2 receieve content= " + mBeerArray.toString());
						Thread.sleep(50);
					} catch (RPCExceptionBase | InterruptedException e)
					{
						System.out.println(e);
					}
				}
			}
		});
	    thread.start();
	    thread2.start();
	}
}
