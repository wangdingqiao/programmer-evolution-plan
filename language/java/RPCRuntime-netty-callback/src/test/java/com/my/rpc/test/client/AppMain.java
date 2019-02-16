package com.my.rpc.test.client;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import com.my.rpc.runtime.builtInMessage.RPCCommonMessage;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCBinary;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCFloat;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCInt;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCString;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.runner.RPCCallBackBase;
import com.my.rpc.runtime.runner.RPCClient;
import com.my.rpc.testMessage.IBeerQuery;
import com.my.rpc.testMessage.IBeerQueryClientStub;
import com.my.rpc.testMessage.MessageBeerArray.BeerArray;

public class AppMain
{
	private RPCClient client;
	
	public AppMain() throws UnknownHostException
	{
		client = new RPCClient();
		client.registStub(new IBeerQueryClientStub(InetAddress.getLocalHost(), 2048));
	}
	
	public static void main(String[] args) throws UnknownHostException
	{
	    LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
	    File file = new File("log4j2.xml");
	    // this will force a reconfiguration
	    context.setConfigLocation(file.toURI());
		AppMain main = new AppMain();
		main.testMethodOne();
//		main.testMethodException();
//		main.testMethodVoidReturn();
		CountDownLatch latch = new CountDownLatch(1);
		 try {
				latch.await();
		} 
		 catch (InterruptedException e) 
		 {
				e.printStackTrace();
		}
	}
	
	public void testMethodOne()
	{
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
			BeerArray mBeerArray = new BeerArray();
			CountDownLatch countDownLatch = new CountDownLatch(1);
			System.out.println("queryBeers operation begin.");
			long t1 = System.currentTimeMillis();
			stub.queryBeers(param1, param2, param3, param4, param5, param6, new RPCCallBackBase(mBeerArray,countDownLatch)
			{
				@Override
				public void onSuccess()
				{
					// TODO Auto-generated method stub
					System.out.println("receieve content= " + mBeerArray.toString());
					this.getCountDown().countDown();
				}
				
				@Override
				public void onFailed(Throwable exc)
				{
					// TODO Auto-generated method stub
					System.out.println("failed to receieve exception= " + exc);
					this.getCountDown().countDown();
				}
			});
			countDownLatch.await();
			long t2 = System.currentTimeMillis() - t1;
			System.out.println("queryBeers operation done elasped: " + t2);
			
		} catch (RPCExceptionBase | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void testMethodVoidReturn()
	{
		try
		{
			IBeerQueryClientStub stub = (IBeerQueryClientStub) client.getStub(IBeerQuery.class);
			RPCInt param1 = new RPCInt(1);
			System.out.println("send  params: " + param1.toString());
			stub.voidReturnTest(param1,new RPCCallBackBase(null,null) {

				@Override
				public void onSuccess()
				{
					// TODO Auto-generated method stub
					System.out.println("receieve voidReturnTest response.");
				}

				@Override
				public void onFailed(Throwable exc)
				{
					// TODO Auto-generated method stub
					
				}
				
			});
			
		} catch (RPCExceptionBase e)
		{
			e.printStackTrace();
		}
	}
	
	public void testMethodException()
	{
		try
		{
			IBeerQueryClientStub stub = (IBeerQueryClientStub) client.getStub(IBeerQuery.class);
			stub.exceptionTest(new RPCCallBackBase(null,null) {

				@Override
				public void onSuccess()
				{
					// TODO Auto-generated method stub
					System.out.println("receieve exceptionTest response");
				}

				@Override
				public void onFailed(Throwable exc)
				{
					// TODO Auto-generated method stub
					System.out.println("receieve exceptionTest response exception=." + exc);
				}
			});
			
		} catch (RPCExceptionBase e)
		{
			e.printStackTrace();
		}
	}
}
