package com.my.rpc.runtime.serializer;

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
}
