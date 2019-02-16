package com.my.rpc.runtime.exception;

public class RPCExceptionBase extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte exceptionId;
	/**
	 * @return the exceptionId
	 */
	public byte getExceptionId()
	{
		return exceptionId;
	}
	public RPCExceptionBase(byte exceptionId, String message)
	{
		super(message);
		this.exceptionId = exceptionId;
	}
}
