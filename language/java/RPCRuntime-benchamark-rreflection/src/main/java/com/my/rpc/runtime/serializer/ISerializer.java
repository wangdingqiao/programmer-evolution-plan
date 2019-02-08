package com.my.rpc.runtime.serializer;

import com.my.rpc.runtime.exception.RPCExceptionBase;

public interface ISerializer
{
	public byte[] toByteArray();
	
	public void putInt(int v) throws RPCExceptionBase;
	public void putInt(Integer v) throws RPCExceptionBase;
	
	public void putShort(short v) throws RPCExceptionBase;
	public void putShort(Short v) throws RPCExceptionBase;
	
	public void putLong(long v) throws RPCExceptionBase;
	public void putLong(Long v) throws RPCExceptionBase;
	
	public void putFloat(float v) throws RPCExceptionBase;
	public void putFloat(Float v) throws RPCExceptionBase;
	
	public void putDouble(double v) throws RPCExceptionBase;
	public void putDouble(Double v) throws RPCExceptionBase;
	
	public void putString(String v) throws RPCExceptionBase;
	public void putByte(Byte b) throws RPCExceptionBase;
	public void putBytes(byte[] bytes) throws RPCExceptionBase;
	public void putBool(boolean b) throws RPCExceptionBase;
	public void putArrayHeader(int size) throws RPCExceptionBase;
	public void putMapHeader(int size) throws RPCExceptionBase;
	public void close() throws RPCExceptionBase;
}