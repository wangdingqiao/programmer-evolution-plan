package com.learningjava.MessagePackDemo;

public interface IMessageSerializer
{
	public abstract byte[] serializeRequest(RPCRequest rpcRequest) throws Exception;
	public abstract RPCRequest deserialzeRequest(byte[] bytes) throws Exception;
	
	public abstract byte[] serializeResponse(RPCResponse rpcResponse) throws Exception;
	public abstract RPCResponse deserialzeResponse(byte[] bytes) throws Exception;
}



