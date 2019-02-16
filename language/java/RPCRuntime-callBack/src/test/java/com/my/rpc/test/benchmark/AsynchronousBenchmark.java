package com.my.rpc.test.benchmark;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


public class AsynchronousBenchmark
{
	public static void main(String[] args) throws UnknownHostException, InterruptedException 
	{
		
//		int taskCount = Runtime.getRuntime().availableProcessors();
//		System.out.println("taskCount= " + taskCount);
//		ExecutorService executorService = Executors.newFixedThreadPool(taskCount);
//		RunningState state = new RunningState();
//		state.setUp();
//		List<QueryTask> tasks = new ArrayList<QueryTask>();
//		int operCount = 100;
//		for(int i = 0; i < operCount; ++i)
//		{
//			tasks.add(new QueryTask(state));
//		}
//		long before = System.currentTimeMillis();
//		executorService.invokeAll(tasks);
//		double elasped = (double)(System.currentTimeMillis() - before);
//		System.out.println(String.format("executed operation count %d, avag time=%.3f",operCount, elasped / operCount));
//		executorService.shutdown();
		
		RunningState state = new RunningState();
		state.setUp();
		int operCountPerThread = 10;
		List<Thread> threads = new ArrayList<Thread>();
		int threadCount = 16;
		for(int i = 0; i < threadCount; ++i)
		{
			threads.add(new Thread(new QueryRunnable(state, operCountPerThread)));
		}
		int totalOperCount = operCountPerThread * threadCount;
		long before = System.currentTimeMillis();
		for(Thread t: threads)
		{
			t.start();
		}
		for(Thread t: threads)
		{
			t.join();
		}
		double elasped = (double)(System.currentTimeMillis() - before);
		System.out.println(String.format("executed operation count %d, avag time=%.3f",totalOperCount, elasped / totalOperCount));
    }
}

class RunningState
{
	public RPCClient client;
	public RPCFloat param1;
	public RPCString param2;
	public RPCCommonMessage.SeqString param3;
	public RPCCommonMessage.MapStrInt param4;
	public RPCBinary param5;
	public RPCCommonMessage.SeqInt param6;
	
	public void setUp() throws UnknownHostException
	{
		client = new RPCClient();
		IBeerQueryClientStub stub = new IBeerQueryClientStub(InetAddress.getLocalHost(), 2048);
		client.registStub(stub);
		this.param1 = new RPCFloat(1.0f);
		this.param2 = new RPCString("RPCDemo");
		this.param3 = new RPCCommonMessage.SeqString();
	    this.param3.elements.add(new RPCString("Hello"));
	    this.param3.elements.add(new RPCString("World"));
	    this.param4 = new RPCCommonMessage.MapStrInt();
	    this.param4.str2Int.put(new RPCString("Good Morninig."), new RPCInt(9));
	    this.param4.str2Int.put(new RPCString("Good Evening."), new RPCInt(18));
	    this.param5 = new RPCBinary();
	    this.param5.valueFromBytes(new byte[] {1, 2, 3, 4});
	    this.param6 = new RPCCommonMessage.SeqInt();
	    this.param6.elements.add(new RPCInt(10));
	    this.param6.elements.add(new RPCInt(20));
	    this.param6.elements.add(new RPCInt(30));
	    System.out.println("BenchmarkMain setup done.");
	}
}

class QueryTask implements Callable<Boolean>
{
	private RunningState state;
	public QueryTask(RunningState state)
	{
		this.state = state;
	}

	@Override
	public Boolean call() throws Exception
	{
		// TODO Auto-generated method stub
		
		try
		{
			IBeerQueryClientStub stub = (IBeerQueryClientStub) state.client.getStub(IBeerQuery.class);
			BeerArray mBeerArray = new BeerArray();
			CountDownLatch countDownLatch = new CountDownLatch(1);
			stub.queryBeers(state.param1, state.param2, state.param3, state.param4, state.param5, state.param6, new RPCCallBackBase(mBeerArray, countDownLatch)
			{
				@Override
				public void onSuccess()
				{
					BeerArray mBeerArray = (BeerArray)this.getMessage();
					mBeerArray.beers.size();
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
			return true;
			
		} catch (RPCExceptionBase e)
		{
			e.printStackTrace();
			return false;
		}
	}
}

class QueryRunnable implements Runnable
{
	private RunningState state;
	private int operCountPerThread;
	public QueryRunnable(RunningState state, int operCountPerThread)
	{
		this.state = state;
		this.operCountPerThread = operCountPerThread;
	}

	@Override
	public void run()
	{
//		System.out.println("runing threadId= "+ Thread.currentThread().getId());
		try
		{
			CountDownLatch countDownLatch = new CountDownLatch(operCountPerThread);
			for(int i = 0; i < operCountPerThread;++i)
			{
				IBeerQueryClientStub stub = (IBeerQueryClientStub) state.client.getStub(IBeerQuery.class);
				BeerArray mBeerArray = new BeerArray();
				
				stub.queryBeers(state.param1, state.param2, state.param3, state.param4, state.param5, state.param6, new RPCCallBackBase(mBeerArray, countDownLatch)
				{
					@Override
					public void onSuccess()
					{
						BeerArray mBeerArray = (BeerArray)this.getMessage();
						mBeerArray.beers.size();
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
			}
			countDownLatch.await();
		} catch (RPCExceptionBase | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
