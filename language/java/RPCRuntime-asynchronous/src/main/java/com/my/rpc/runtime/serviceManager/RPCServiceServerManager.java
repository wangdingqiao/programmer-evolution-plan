package com.my.rpc.runtime.serviceManager;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.my.rpc.runtime.runner.RPCServerStub;
import com.my.rpc.runtime.runner.RPCStub;

public class RPCServiceServerManager
{
	private Map<String, RPCServerStub> serviceMap;
	private static Logger logger = LogManager.getLogger(RPCServiceServerManager.class);
	private static RPCServiceServerManager instance = null;
	
	private RPCServiceServerManager()
	{
		serviceMap = new HashMap<>();
	}
	
	/**
	 * @return the serviceMap
	 */
	public Map<String, RPCServerStub> getServiceMap()
	{
		return serviceMap;
	}

	/**
	 * @param serviceMap the serviceMap to set
	 */
	public void setServiceMap(Map<String, RPCServerStub> serviceMap)
	{
		this.serviceMap = serviceMap;
	}
	
	public boolean registStub(RPCServerStub stub)
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
	
	public RPCServerStub getStub(Class<?> cls)
	{
		return this.serviceMap.get(RPCStub.makeSubKey(cls));
	}
	
	public RPCServerStub getStub(String stubKey)
	{
		return this.serviceMap.get(stubKey);
	}
	
	public static RPCServiceServerManager getInstance()
	{
		if(instance == null)
		{
			instance = new RPCServiceServerManager();
		}
		return instance;
	}
}
