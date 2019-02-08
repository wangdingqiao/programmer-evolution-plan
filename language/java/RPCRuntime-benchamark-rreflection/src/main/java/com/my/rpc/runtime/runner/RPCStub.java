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

	public RPCStub(String stubKey)
	{
		super();
		this.stubKey = stubKey;
	}
	
}
