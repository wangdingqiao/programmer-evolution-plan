package com.my.rpc.runtime.runner;

import com.my.rpc.runtime.serviceManager.RPCServiceClientManager;

public class RPCClient
{
	public RPCClient()
	{
		
	}
	
	public void registStub(RPCClientStub stub)
	{
		RPCServiceClientManager.getInstance().registStub(stub);
	}
	
	public RPCClientStub getStub(Class<?> cls)
	{
		return RPCServiceClientManager.getInstance().getStub(cls);
	}
}
