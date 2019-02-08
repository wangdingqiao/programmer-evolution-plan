package com.my.rpc.runtime.protocol;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.serializer.IDeserializer;
import com.my.rpc.runtime.serializer.ISerializer;

public interface IRPCMessage
{
	 public void serialize(ISerializer ser) throws RPCExceptionBase ;
	 public void deserialize(IDeserializer deser) throws RPCExceptionBase;
}
