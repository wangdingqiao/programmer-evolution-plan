package com.my.rpc.runtime.exception;

public class RPCMethodFunctionOffException extends RPCExceptionBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RPCMethodFunctionOffException(byte exceptionId, String message)
	{
		super(exceptionId, message);
	}
}
