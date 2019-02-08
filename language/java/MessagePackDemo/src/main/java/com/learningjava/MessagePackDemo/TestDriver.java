package com.learningjava.MessagePackDemo;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;

public class TestDriver
{
	public static void main(String[] args) throws Exception
	{
		new TestDriver().run();
	}
	
	public void run() throws Exception
	{
		this.validSerialization();
	}
	
	public void validSerialization() throws Exception
	{
		// test request serialization
		RPCRequest request = makeRequest();
		System.out.println("input Request= " + request);
		MsgPackSerializer serializer = new MsgPackSerializer();
		byte[] bytes = serializer.serializeRequest(request);
		RPCRequest decodeRequest = serializer.deserialzeRequest(bytes);
		System.out.println("output Request size= " + bytes.length + " content= " + decodeRequest);
		System.out.println("decode request success ? " + request.toString().equals(decodeRequest.toString()));
		// test response serialization
		RPCResponse response = makeResponse(request);
		System.out.println("input Response= " + response);
		bytes = serializer.serializeResponse(response);
		RPCResponse decodeResponse = serializer.deserialzeResponse(bytes);
		System.out.println("output Response size= " + bytes.length + " content= " + decodeResponse);
		System.out.println("decode response success ? " + response.response.toString().equals(decodeResponse.response.toString()));
		System.out.println("validation done.");
	}
	
	private RPCRequest makeRequest() throws NoSuchMethodException, SecurityException, ClassNotFoundException
	{
		RPCRequest request = new RPCRequest();
		Class<?> service = Class.forName(IServiceTest.class.getName());
		request.requestId = 1;
		request.clazz = service;
		Method method = service.getMethod("queryBeers", float.class, String.class, ArrayList.class, HashMap.class, byte[].class, int[].class);
		request.method = method.getName();
		request.parameterTypes = method.getParameterTypes();
		request.params = new Object[method.getParameterTypes().length];
		
		request.params[0] = 5.0f;
		request.params[1] = "De Struise Brouwers";
		
		ArrayList<String> listParam = new ArrayList<>();
		listParam.add("Good Morning.");
		listParam.add("Good Afternoon.");
		listParam.add("Good Evening.");
		request.params[2] = listParam;
		
		Map<String, Integer> mapParam = new java.util.HashMap<>();
		mapParam.put("Hello", 1);
		mapParam.put("Hi", 2);
		mapParam.put("Hey", 3);
		request.params[3] = mapParam;
		
		request.params[4] = new byte[] {1, 2, 3, 4};
		request.params[5] = new int[] {10,20,30,40,50};
		request.requestTime = System.currentTimeMillis();
		return request;
	}
	
	private RPCResponse makeResponse(RPCRequest request) throws JAXBException, IOException
	{
		RPCResponse rpcResponse = new RPCResponse();
		float param1 = (float) request.params[0];
		String param2 = (String) request.params[1];
		@SuppressWarnings("unchecked")
		ArrayList<String> param3 = (ArrayList<String>) request.params[2];
		@SuppressWarnings("unchecked")
		HashMap<String, Integer> param4 = (HashMap<String, Integer>) request.params[3];
		byte[] param5 = (byte[])request.params[4];
		int[] param6 = (int[])request.params[5];
		BeerArray beerArray = new ServiceImpl().queryBeers(param1, param2, param3, param4, param5, param6);
		rpcResponse.requestId = request.requestId;
		rpcResponse.responseParamType = BeerArray.class;
		rpcResponse.response = beerArray;
		return rpcResponse;
	}
}
