package com.my.rpc.runtime.runner;

import com.my.rpc.runtime.protocol.RPCCallInformation;

public abstract class RPCServerStub extends RPCStub
{
	private boolean functionOn;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "RPCServerStub [ key= " + getStubKey() + " functionOn=" + functionOn + "]";
	}

	/**
	 * @return the isFunctionOff
	 */
	public boolean isFunctionOn()
	{
		return functionOn;
	}

	/**
	 * @param functionOn the isFunctionOff to set
	 */
	public void turnFunctionOff()
	{
		this.functionOn = false;
	}
	
	public void turnFunctionOn()
	{
		this.functionOn = true;
	}

	public RPCServerStub(Class<?> cls)
	{
		super(cls);
		this.functionOn = true;
	}
	
	public abstract void dispatchCall(RPCCallInformation callInfo) throws Exception;
}
