package com.learning.rpc.server;
import com.learning.rpc.common.RpcFramework;
import com.learning.rpc.common.HelloService;

public class RPCServer
{
	public static void main(String[] args) throws Exception
	{
		HelloService service = new HelloServiceImpl();
		RpcFramework.export(service, 1234);
	}
}
