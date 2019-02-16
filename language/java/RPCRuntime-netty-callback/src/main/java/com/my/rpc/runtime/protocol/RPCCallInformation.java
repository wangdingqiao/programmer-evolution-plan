package com.my.rpc.runtime.protocol;

import java.io.OutputStream;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.serializer.IDeserializer;
import com.my.rpc.runtime.serializer.MsgDeserByMessagePack;

public class RPCCallInformation
{
	
	private IDeserializer deser;
	private RPCRequest request;
	
	public RPCCallInformation(RPCRequest request, OutputStream output)
	{
		super();
		this.request = request;
		this.deser = new MsgDeserByMessagePack();
		this.deser.fromByteArray(request.getBodyData());
	}
	public RPCCallInformation(RPCRequest request)
	{
		super();
		this.request = request;
		this.deser = new MsgDeserByMessagePack();
		this.deser.fromByteArray(request.getBodyData());
	}
	public IDeserializer getDeserializer()
	{
		return deser;
	}
	
	public RPCResponse reply(IRPCMessage message) throws RPCExceptionBase
	{
		RPCResponse response = new RPCResponse(this.request.getRequestId(), message);
		return response;
	}
	
	public RPCResponse reply() throws RPCExceptionBase
	{
		return this.reply(null);
	}
	
	public static RPCResponse replyException(RPCExceptionBase e, long requestId, OutputStream output) throws RPCExceptionBase
	{
		RPCResponse response = new RPCResponse(requestId, e);
		return response;
	}
	
	public RPCResponse replyException(RPCExceptionBase e) throws RPCExceptionBase
	{
		RPCResponse response = new RPCResponse(this.request.getRequestId(), e);
		return response;
	}
	
	public RPCRequest getRequestInfo()
	{
		return this.request;
	}
	
	public int getMethodId()
	{
		return this.request.getMethodId();
	}
}
