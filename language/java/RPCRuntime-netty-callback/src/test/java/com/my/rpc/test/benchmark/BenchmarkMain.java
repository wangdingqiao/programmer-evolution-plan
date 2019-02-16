package com.my.rpc.test.benchmark;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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


@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@State(Scope.Benchmark)
@Warmup(iterations = 15, time = 50, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 30, time = 50, timeUnit = TimeUnit.MILLISECONDS)
public class BenchmarkMain
{
	public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(BenchmarkMain.class.getSimpleName())
                .resultFormat(ResultFormatType.TEXT)
                .build();

        new Runner(opt).run();
    }
	
	private RPCClient client;
	private RPCFloat param1;
	private RPCString param2;
	private RPCCommonMessage.SeqString param3;
	private RPCCommonMessage.MapStrInt param4;
	private RPCBinary param5;
	private RPCCommonMessage.SeqInt param6;
	
	@Setup
    public void setup() throws UnknownHostException, RPCExceptionBase 
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
	
	@TearDown(Level.Trial)
	public void tearDown() throws InterruptedException {
		System.out.println("wait a few seconds for next benchmark");
	    Thread.sleep(5000);
	}
	
	@Threads(1)
	@Benchmark
    public void queryBeers_single_thread(Blackhole bh) {
		try
		{
			IBeerQueryClientStub stub = (IBeerQueryClientStub) client.getStub(IBeerQuery.class);
			BeerArray mBeerArray = new BeerArray();
			CountDownLatch countDownLatch = new CountDownLatch(1);
			stub.queryBeers(param1, param2, param3, param4, param5, param6, new RPCCallBackBase(mBeerArray, countDownLatch)
			{
				@Override
				public void onSuccess()
				{
					BeerArray mBeerArray = (BeerArray)this.getMessage();
					bh.consume(mBeerArray);
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
			
		} catch (RPCExceptionBase | InterruptedException e)
		{
			e.printStackTrace();
		}
    }
	
	@Threads(8)
	@Benchmark
    public void queryBeers_more_thread(Blackhole bh) {
		try
		{
			IBeerQueryClientStub stub = (IBeerQueryClientStub) client.getStub(IBeerQuery.class);
			BeerArray mBeerArray = new BeerArray();
			CountDownLatch countDownLatch = new CountDownLatch(1);
			stub.queryBeers(param1, param2, param3, param4, param5, param6, new RPCCallBackBase(mBeerArray, countDownLatch)
			{
				@Override
				public void onSuccess()
				{
					BeerArray mBeerArray = (BeerArray)this.getMessage();
					bh.consume(mBeerArray);
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
			
		} catch (RPCExceptionBase | InterruptedException e)
		{
			e.printStackTrace();
		}
    }
}
