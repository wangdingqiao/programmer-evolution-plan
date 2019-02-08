package com.my.rpc.runtime.exception;

public class RPCGenerateFileException extends RPCExceptionBase
{
	/**
	 * 
	 *
	 */
	private static final long serialVersionUID = 1L;

	public RPCGenerateFileException(byte exceptionId, String message)
	{
		super(exceptionId, message);
	}
}