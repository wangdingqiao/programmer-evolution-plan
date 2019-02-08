package com.learning.rpc.client;

import com.learning.rpc.common.RpcFramework;
import com.learning.rpc.common.HelloService;

public class RPCTest
{
	public static void main(String[] args) throws Exception
	{
		HelloService service = RpcFramework.refer(HelloService.class, "localhost", 1234);
		for(int i = 0; i < Integer.MAX_VALUE; ++i)
		{
			String hello = service.hello("World" + i);
			System.out.println(hello);
			Thread.sleep(1000);
		}
	}
}
