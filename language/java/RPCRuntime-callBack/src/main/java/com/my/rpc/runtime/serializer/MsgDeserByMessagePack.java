package com.my.rpc.runtime.serializer;

import java.nio.ByteBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.msgpack.core.MessagePack.UnpackerConfig;
import org.msgpack.core.MessageUnpacker;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;

public class MsgDeserByMessagePack implements IDeserializer
{
	private MessageUnpacker unpacker;
	private static Logger logger = LogManager.getLogger(MsgDeserByMessagePack.class);

	@Override
	public void fromByteArray(byte[] bytes)
	{
//		unpacker = MessagePack.newDefaultUnpacker(bytes);
		unpacker = new UnpackerConfig()
	            .withStringDecoderBufferSize(1024 * 1024) // If your data contains many large strings (the default is 8k)
	            .newUnpacker(bytes);
	}
	
	public void fromByteBuffer(ByteBuffer buffer)
	{
		unpacker = new UnpackerConfig()
	            .withStringDecoderBufferSize(1024 * 1024) // If your data contains many large strings (the default is 8k)
	            .newUnpacker(buffer);
	}
	

	@Override
	public int readInt() throws RPCExceptionBase
	{
		try
		{
			return unpacker.unpackInt();
		} catch (Exception e)
		{
			logger.error( " readInt " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}

	@Override
	public long readLong() throws RPCExceptionBase
	{
		try
		{
			return unpacker.unpackLong();
		} catch (Exception e)
		{
			logger.error( " readLong " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}

	@Override
	public float readFloat() throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		try
		{
			return unpacker.unpackFloat();
		} catch (Exception e)
		{
			logger.error( " readFloat " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}

	@Override
	public double readDouble() throws RPCExceptionBase
	{
		try
		{
			return unpacker.unpackDouble();
		} catch (Exception e)
		{
			logger.error( " readDouble " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}

	@Override
	public String readString() throws RPCExceptionBase
	{
		try
		{
			return unpacker.unpackString();
		} catch (Exception e)
		{
			logger.error( " readString " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}

	@Override
	public byte readByte() throws RPCExceptionBase
	{
		try
		{
			return unpacker.unpackByte();
		} catch (Exception e)
		{
			logger.error( " readByte " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}

	@Override
	public byte[] readBytes() throws RPCExceptionBase
	{
		try
		{
			int len = unpacker.unpackBinaryHeader();
			byte[] bytes = new byte[len];
			if (len > 0)
			{
				unpacker.readPayload(bytes);
			}
			return bytes;
		} catch (Exception e)
		{
			logger.error( " readBytes " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}

	@Override
	public boolean readBool() throws RPCExceptionBase
	{
		try
		{
			return unpacker.unpackBoolean();
		} catch (Exception e)
		{
			logger.error( " readBool " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}

	@Override
	public short readShort() throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		try
		{
			return unpacker.unpackShort();
		} catch (Exception e)
		{
			logger.error( " readShort " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}

	@Override
	public byte[] readBytes(int length) throws RPCExceptionBase
	{
		try
		{
			byte[] bytes = new byte[length];
			if (length > 0)
			{
				unpacker.readPayload(bytes);
			}
			return bytes;
		} catch (Exception e)
		{
			logger.error( " readBytes " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString());
		}
	}

	@Override
	public int readArrayHeader() throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		try
		{
			return unpacker.unpackArrayHeader();
		} catch (Exception e)
		{
			logger.error( " readShort " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}

	@Override
	public int readMapHeader() throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		try
		{
			return unpacker.unpackMapHeader();
		} catch (Exception e)
		{
			logger.error( " readShort " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}
	
	@Override
	public void readVoid() throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		try
		{
			unpacker.unpackNil();
		} catch (Exception e)
		{
			logger.error( " readVoid " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}
	
	@Override
	public void close() throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		try
		{
			unpacker.close();
		} catch (Exception e)
		{
			logger.error( " close " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, e.toString()); 
		}
	}
}
