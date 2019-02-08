package com.my.rpc.runtime.serviceManager;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.my.rpc.runtime.runner.RPCClientStub;
import com.my.rpc.runtime.runner.RPCStub;

public class RPCServiceClientManager
{
	private Map<String, RPCClientStub> serviceMap;
	private static Logger logger = LogManager.getLogger(RPCServiceClientManager.class);
	private static RPCServiceClientManager instance = null;
	
	private RPCServiceClientManager()
	{
		serviceMap = new HashMap<>();
	}
	
	/**
	 * @return the serviceMap
	 */
	public Map<String, RPCClientStub> getServiceMap()
	{
		return serviceMap;
	}

	/**
	 * @param serviceMap the serviceMap to set
	 */
	public void setServiceMap(Map<String, RPCClientStub> serviceMap)
	{
		this.serviceMap = serviceMap;
	}
	
	public boolean registStub(RPCClientStub stub)
	{
		if(stub == null)
		{
			logger.error("RPCServiceManager registStub  stub is null.");
			return false;
		}
		if(serviceMap.containsKey(stub.getStubKey()))
		{
//			logger.error("RPCServiceManager registStub duplicate stub key, stub= " + stub.toString());
			return false;
		}
		this.serviceMap.put(stub.getStubKey(), stub);
//		logger.info("RPCServiceManager registStub " + stub.toString() + " success.");
		return true;
	}
	
	public boolean unregistStub(Class<?> cls)
	{
		if(cls == null)
		{
			return false;
		}
		String stubKey = RPCStub.makeSubKey(cls);
		if(this.serviceMap.containsKey(stubKey))
		{
			this.serviceMap.remove(stubKey);
			logger.info("RPCServiceManager unregistStub key= " + stubKey + " success.");
			return true;
		}
		return false;
	}
	
	public RPCClientStub getStub(Class<?> cls)
	{
		return this.serviceMap.get(RPCStub.makeSubKey(cls));
	}
	
	public RPCClientStub getStub(String stubKey)
	{
		return this.serviceMap.get(stubKey);
	}
	
	public static RPCServiceClientManager getInstance()
	{
		if(instance == null)
		{
			instance = new RPCServiceClientManager();
		}
		return instance;
	}
}
