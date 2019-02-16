package com.my.rpc.runtime.exception;

public class RPCInterfaceNotFoundException extends RPCExceptionBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RPCInterfaceNotFoundException(byte exceptionId, String message)
	{
		super(exceptionId, message);
	}
}
