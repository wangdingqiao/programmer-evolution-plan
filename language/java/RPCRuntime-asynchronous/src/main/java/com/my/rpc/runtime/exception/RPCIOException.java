package com.my.rpc.runtime.exception;

public class RPCIOException extends RPCExceptionBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RPCIOException(byte exceptionId, String message)
	{
		super(exceptionId, message);
	}
}
