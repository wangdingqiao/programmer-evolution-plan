package com.my.rpc.runtime.serializer;

import java.nio.ByteBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.my.rpc.runtime.exception.RPCExceptionBase;

public class MsgDeserByByteBuffer implements IDeserializer
{
	private ByteBuffer buffer;
	private static Logger logger = LogManager.getLogger(MsgDeserByByteBuffer.class);

	@Override
	public void fromByteArray(byte[] bytes)
	{
		buffer = ByteBuffer.wrap(bytes);
	}
	
	public void fromByteBuffer(ByteBuffer buffer)
	{
		this.buffer = buffer;
	}

	@Override
	public int readInt() throws RPCExceptionBase
	{
		return buffer.getInt();
	}

	@Override
	public long readLong() throws RPCExceptionBase
	{
		return buffer.getLong();
	}

	@Override
	public float readFloat() throws RPCExceptionBase
	{
		return buffer.getFloat();
	}

	@Override
	public double readDouble() throws RPCExceptionBase
	{
		return buffer.getDouble();
	}
	
	private String byteBuffGetString(ByteBuffer buff)
	{
		int length = buff.getInt();
		byte[] bytes = new byte[length];
		buff.get(bytes);
		return new String(bytes);
	}
	
	@Override
	public String readString() throws RPCExceptionBase
	{
		return this.byteBuffGetString(buffer);
	}

	@Override
	public byte readByte() throws RPCExceptionBase
	{
		return buffer.get();
	}

	@Override
	public byte[] readBytes() throws RPCExceptionBase
	{
		int len = readArrayHeader();
		byte[] bytes = new byte[len];
		this.buffer.get(bytes);
		return bytes;
	}

	@Override
	public boolean readBool() throws RPCExceptionBase
	{
		byte b = readByte();
		return b == (byte)1;
	}

	@Override
	public short readShort() throws RPCExceptionBase
	{
		return this.buffer.getShort();
	}

	@Override
	public byte[] readBytes(int length) throws RPCExceptionBase
	{
		byte[] bytes = new byte[length];
		this.buffer.get(bytes);
		return bytes;
	}

	@Override
	public int readArrayHeader() throws RPCExceptionBase
	{
		return this.buffer.getInt();
	}

	@Override
	public int readMapHeader() throws RPCExceptionBase
	{
		return this.buffer.getInt();
	}
	
	@Override
	public void readVoid() throws RPCExceptionBase
	{
		
	}
	
	@Override
	public void close() throws RPCExceptionBase
	{
		this.buffer = null;
	}
}
