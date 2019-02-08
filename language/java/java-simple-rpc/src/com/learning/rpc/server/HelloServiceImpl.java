package com.learning.rpc.server;
import com.learning.rpc.common.HelloService;

public class HelloServiceImpl implements HelloService
{

	@Override
	public String hello(String name)
	{
		return "Hello " + name;
	}

}
