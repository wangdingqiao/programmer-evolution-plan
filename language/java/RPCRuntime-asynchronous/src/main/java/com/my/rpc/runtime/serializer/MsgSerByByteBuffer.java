package com.my.rpc.runtime.serializer;

import java.nio.ByteBuffer;

import com.my.rpc.runtime.exception.RPCExceptionBase;

public class MsgSerByByteBuffer implements ISerializer
{
	private ByteBuffer buff;
	
	public MsgSerByByteBuffer()
	{
		buff = ByteBuffer.allocate(10 * 1024);
	}
	@Override
	public byte[] toByteArray()
	{
		buff.flip();
		byte[] arr = new byte[buff.remaining()];
		buff.get(arr);
		// TODO Auto-generated method stub
		return arr;
	}

	@Override
	public void putInt(int v) throws RPCExceptionBase
	{
		buff.putInt(v);
	}

	@Override
	public void putInt(Integer v) throws RPCExceptionBase
	{
		buff.putInt(v);
	}

	@Override
	public void putShort(short v) throws RPCExceptionBase
	{
		buff.putShort(v);
	}

	@Override
	public void putShort(Short v) throws RPCExceptionBase
	{
		buff.putShort(v);
	}

	@Override
	public void putLong(long v) throws RPCExceptionBase
	{
		buff.putLong(v);
	}

	@Override
	public void putLong(Long v) throws RPCExceptionBase
	{
		buff.putLong(v);
	}

	@Override
	public void putFloat(float v) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		buff.putFloat(v);
	}

	@Override
	public void putFloat(Float v) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		buff.putFloat(v);
	}

	@Override
	public void putDouble(double v) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		buff.putDouble(v);
	}

	@Override
	public void putDouble(Double v) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		buff.putDouble(v);
	}
	
	private void byteBuffPutString(String text, ByteBuffer buff)
	{
		byte[] bytes = text.getBytes();
		buff.putInt(bytes.length);
		buff.put(bytes);
	}
	
	@Override
	public void putString(String v) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		this.byteBuffPutString(v, buff);
	}

	@Override
	public void putByte(Byte b) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		buff.put(b);
	}

	@Override
	public void putBytes(byte[] bytes) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		buff.putInt(bytes.length);
		buff.put(bytes);
	}

	@Override
	public void putBool(boolean b) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		buff.put((byte)(b ? 1: 0));
	}

	@Override
	public void putArrayHeader(int size) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		this.putInt(size);
	}

	@Override
	public void putMapHeader(int size) throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		this.putInt(size);
	}

	@Override
	public void close() throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		this.buff = null;
	}

	@Override
	public void putVoid() throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public ByteBuffer toByteBuffer()
	{
		this.buff.flip();
		return this.buff;
	}
}
