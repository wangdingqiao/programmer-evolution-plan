package com.learningjava.serializationPerformance.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;

import com.google.protobuf.Empty;
import com.learningjava.serializationPerformance.common.BeerArray;
import com.learningjava.serializationPerformance.common.PerformanceResult;
import com.learningjava.serializationPerformance.common.SerializationTester;
import com.learningjava.serializationPerformance.tester.BSONSerializationTester;
import com.learningjava.serializationPerformance.tester.JSONSerializationTester;
import com.learningjava.serializationPerformance.tester.JavaByteBufferTester;
import com.learningjava.serializationPerformance.tester.JavaDataStreamTester;
import com.learningjava.serializationPerformance.tester.JavaObjectStreamTester;
import com.learningjava.serializationPerformance.tester.MsgPackSerializationTester;
import com.learningjava.serializationPerformance.tester.ProtoBufSerializationTester;
import com.learningjava.serializationPerformance.tester.UnsafeSerializationTester;
import com.learningjava.serializationPerformance.tester.XMLSerializationTester;

public class PerformanceMain
{
	private BeerArray beerArray;
	private ArrayList<SerializationTester> testers;
	private int SMALL_DATA_SET_SIZE = 100;
	
	
	public PerformanceMain()
	{
		this.beerArray = null;
		this.testers = new ArrayList<>();
	}

	public static void main(String[] args) throws Exception
	{
		new PerformanceMain().run(10000);
	}
	
	private void setUp() throws Exception
	{
		this.beerArray = new XMLSerializationTester().loadFile(new File("beers.xml"));
        System.out.println("Set up loaded " + this.beerArray.size() + " data item.");
        
        testers.add(new JavaObjectStreamTester());
        testers.add(new JavaDataStreamTester());
        testers.add(new JavaByteBufferTester());
        testers.add(new XMLSerializationTester());
        testers.add(new JSONSerializationTester());
        testers.add(new BSONSerializationTester());
        testers.add(new UnsafeSerializationTester());
        testers.add(new ProtoBufSerializationTester());
        testers.add(new MsgPackSerializationTester());
	}
	
	private boolean validateTester() throws Exception
	{
		BeerArray beerArray = new BeerArray();
		for(int i = 0; i < 5; ++i)
		{
			beerArray.addBeer(this.beerArray.getBeers().get(i));
		}
		for(SerializationTester tester: testers)
		{
			if(tester.isWorking(beerArray))
			{
				System.out.println("Validate tester " + tester + " is working: OK " );
			}
			else
			{
				System.out.println("Validate tester " + tester + " is working: NO!" );
				return false;
			}
		}
		System.out.println("Validate all ser and deser method done.");
		return true;
	}
	
	private void runSmallDataSet(int times)
	{
		BeerArray beers = new BeerArray();
		for(int i = 0; i < SMALL_DATA_SET_SIZE; ++i)
		{
			beers.addBeer(this.beerArray.getBeers().get(i));
		}
		this.runDataSet(beers, times);
	}
	
	private void runBigDataSet(int times)
	{
		this.runDataSet(this.beerArray, times);
	}
	
	private JSONArray runDataSet(BeerArray beers, int times)
	{
		System.out.println("Begin Run data set size=" + beers.size() + " with operation times=" + times+ " , speed in operation per millisecond.");
		ArrayList<PerformanceResult> results = new ArrayList<>();
		for(SerializationTester tester: testers)
		{
			results.add(tester.run(beers, times));
		}
		for(PerformanceResult result: results)
		{
			System.out.println(result);
		}
		JSONArray jo = new JSONArray(results.toString());
		try
		{
			FileWriter fileWriter = new FileWriter("results-"+ (beers.size() > SMALL_DATA_SET_SIZE ? "big":"small")+".json");
			fileWriter.write(jo.toString());
			fileWriter.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println("End Run data set size=" + beers.size());
		return jo;
	}
	
	public void run(int times) throws Exception
	{
		this.setUp();
		if(!this.validateTester())
		{
			return;
		}
		this.runSmallDataSet(times);
		this.runBigDataSet(times);
	}
}
