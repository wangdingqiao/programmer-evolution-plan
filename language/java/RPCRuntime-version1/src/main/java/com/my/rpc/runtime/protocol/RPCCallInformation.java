package com.my.rpc.runtime.protocol;

import java.io.OutputStream;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.serializer.IDeserializer;
import com.my.rpc.runtime.serializer.MsgDeserByMessagePack;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCVoid;

public class RPCCallInformation
{
	
	private IDeserializer deser;
	private OutputStream output;
	private RPCRequest request;
	
	public RPCCallInformation(RPCRequest request, OutputStream output)
	{
		super();
		this.request = request;
		this.output = output;
		this.deser = new MsgDeserByMessagePack();
		this.deser.fromByteArray(request.getBodyData());
	}
	public IDeserializer getDeserializer()
	{
		return deser;
	}
	
	public void reply(IRPCMessage message) throws RPCExceptionBase
	{
		RPCResponse response = new RPCResponse(this.request.getRequestId(), message);
		response.writeToStream(output);
	}
	
	public void reply() throws RPCExceptionBase
	{
		this.reply(null);
	}
	
	public static void replyException(RPCExceptionBase e, long requestId, OutputStream output) throws RPCExceptionBase
	{
		RPCResponse response = new RPCResponse(requestId, e);
		response.writeToStream(output);
	}
	
	public void replyException(RPCExceptionBase e) throws RPCExceptionBase
	{
		RPCResponse response = new RPCResponse(this.request.getRequestId(), e);
		response.writeToStream(output);
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
