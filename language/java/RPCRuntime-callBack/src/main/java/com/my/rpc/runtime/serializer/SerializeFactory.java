package com.my.rpc.runtime.serializer;

import java.nio.ByteBuffer;

public class SerializeFactory
{
	public static ISerializer  getSerializer()
	{
		return new MsgSerByMessagePack();
	}
	
	public static IDeserializer getDeserializer(byte[] bytes)
	{
		IDeserializer deserializer = new MsgDeserByMessagePack();
		deserializer.fromByteArray(bytes);
		return deserializer;
	}
	
	public static IDeserializer getDeserializer(ByteBuffer buffer)
	{
		IDeserializer deserializer = new MsgDeserByMessagePack();
		deserializer.fromByteBuffer(buffer);
		return deserializer;
	}
}
