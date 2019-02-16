package com.my.rpc.runtime.runner;

public class RPCStub
{
	private String stubKey;

	/**
	 * @return the stubKey
	 */
	public String getStubKey()
	{
		return stubKey;
	}

	/**
	 * @param stubKey the stubKey to set
	 */
	public void setStubKey(String stubKey)
	{
		this.stubKey = stubKey;
	}

	public RPCStub(Class<?> cls)
	{
		this.stubKey = makeSubKey(cls);
	}
	
	public static String makeSubKey(Class<?> cls)
	{
		return cls.getName();
	}
}
