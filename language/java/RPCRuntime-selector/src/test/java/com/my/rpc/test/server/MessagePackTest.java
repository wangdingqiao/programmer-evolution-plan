package com.my.rpc.test.server;

import java.nio.ByteBuffer;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.serializer.IDeserializer;
import com.my.rpc.runtime.serializer.ISerializer;
import com.my.rpc.runtime.serializer.SerializeFactory;

public class MessagePackTest
{
	public static void main(String[] args) throws RPCExceptionBase
	{
		ISerializer serializer = SerializeFactory.getSerializer();
		serializer.putInt(4);
		byte[] bytes = ByteBuffer.allocate(4).putInt(4).array();
		ByteBuffer buffer = ByteBuffer.wrap(serializer.toByteArray());
		IDeserializer deser =  SerializeFactory.getDeserializer(buffer);
		int contentSize = deser.readInt();
		System.out.println("contentSize=" + contentSize);
	}
}
