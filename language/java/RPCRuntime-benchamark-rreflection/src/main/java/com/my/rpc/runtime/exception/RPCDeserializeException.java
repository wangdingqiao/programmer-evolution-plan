package com.my.rpc.runtime.exception;

public class RPCDeserializeException extends RPCExceptionBase
{
	private static final long serialVersionUID = 1L;
	
	public RPCDeserializeException(byte exceptionId, String message)
	{
		super(exceptionId, message);
	}
}
