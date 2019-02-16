package com.my.rpc.runtime.exception;

public class RPCSerializeException extends RPCExceptionBase
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RPCSerializeException(byte exceptionId, String message)
	{
		super(exceptionId, message);
	}
}
