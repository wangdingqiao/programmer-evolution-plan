package com.my.rpc.runtime.exception;

public class RPCInterfaceRuntimeException extends RPCExceptionBase
{
	private static final long serialVersionUID = 1L;
	
	public RPCInterfaceRuntimeException(byte exceptionId, String message)
	{
		super(exceptionId, message);
	}
}
