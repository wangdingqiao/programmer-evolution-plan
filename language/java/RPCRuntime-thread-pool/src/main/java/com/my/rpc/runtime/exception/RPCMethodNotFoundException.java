package com.my.rpc.runtime.exception;

public class RPCMethodNotFoundException extends RPCExceptionBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RPCMethodNotFoundException(byte exceptionId, String message)
	{
		super(exceptionId, message);
	}
}
