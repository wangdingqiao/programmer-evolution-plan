package com.my.rpc.runtime.exception;

public class RPCMessageDefinitionException extends RPCExceptionBase
{
	/**
	 * 
	 *
	 */
	private static final long serialVersionUID = 1L;

	public RPCMessageDefinitionException(byte exceptionId, String message)
	{
		super(exceptionId, message);
	}
}