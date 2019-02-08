package com.learningjava.serializationPerformance.common;

public interface ISerialize
{
	byte[] serialize(Object object);
	Object deserialize(byte[] bytes);
}
