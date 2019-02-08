package com.my.rpc.runtime.serviceManager;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.my.rpc.runtime.runner.RPCStub;

public class RPCServiceManager
{
	private Map<String, RPCStub> serviceMap;
	private static Logger logger = LogManager.getLogger(RPCServiceManager.class);
	private static RPCServiceManager instance = null;
	
	private RPCServiceManager()
	{
		serviceMap = new HashMap<>();
	}
	
	/**
	 * @return the serviceMap
	 */
	public Map<String, RPCStub> getServiceMap()
	{
		return serviceMap;
	}

	/**
	 * @param serviceMap the serviceMap to set
	 */
	public void setServiceMap(Map<String, RPCStub> serviceMap)
	{
		this.serviceMap = serviceMap;
	}
	
	public boolean registStub(RPCStub stub)
	{
		if(stub == null)
		{
			logger.error("RPCServiceManager registStub  stub is null.");
			return false;
		}
		if(serviceMap.containsKey(stub.getStubKey()))
		{
			logger.error("RPCServiceManager registStub duplicate stub key, stub= " + stub.toString());
			return false;
		}
		this.serviceMap.put(stub.getStubKey(), stub);
		logger.info("RPCServiceManager registStub " + stub.toString() + " success.");
		return true;
	}
	
	public boolean unregistStub(String stubKey)
	{
		if(this.serviceMap.containsKey(stubKey))
		{
			this.serviceMap.remove(stubKey);
			logger.info("RPCServiceManager unregistStub key= " + stubKey + " success.");
			return true;
		}
		return false;
	}
	
	public RPCStub getStub(String stubKey)
	{
		return this.serviceMap.get(stubKey);
	}
	
	public static RPCServiceManager getInstance()
	{
		if(instance == null)
		{
			instance = new RPCServiceManager();
		}
		return instance;
	}
}
