package com.my.rpc.runtime.serializer;

import java.nio.ByteBuffer;

import com.my.rpc.runtime.exception.RPCExceptionBase;

public interface IDeserializer
{
	public void fromByteArray(byte[] bytes);
	public void fromByteBuffer(ByteBuffer buffer);
	public int readInt() throws RPCExceptionBase;
	public short readShort() throws RPCExceptionBase;
	public long readLong() throws RPCExceptionBase;
	public float readFloat() throws RPCExceptionBase;
	public double readDouble() throws RPCExceptionBase;
	public String readString() throws RPCExceptionBase;
	public byte readByte() throws RPCExceptionBase;
	public byte[] readBytes() throws RPCExceptionBase;
	public byte[] readBytes(int length) throws RPCExceptionBase;
	public boolean readBool() throws RPCExceptionBase;
	public int readArrayHeader() throws RPCExceptionBase;
	public int readMapHeader() throws RPCExceptionBase;
	public void close() throws RPCExceptionBase;
	public void readVoid() throws RPCExceptionBase;
}
