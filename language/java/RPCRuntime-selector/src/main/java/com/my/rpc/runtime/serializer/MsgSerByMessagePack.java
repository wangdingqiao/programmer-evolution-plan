package com.my.rpc.runtime.serializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;


public class MsgSerByMessagePack implements ISerializer
{
	private MessageBufferPacker packer;
	private static Logger logger = LogManager.getLogger(MsgSerByMessagePack.class);
	public MsgSerByMessagePack()
	{
		this.packer = MessagePack.newDefaultBufferPacker();
	}
	@Override
	public byte[] toByteArray()
	{
		// TODO Auto-generated method stub
		return packer.toByteArray();
	}
	@Override
	public void putInt(int v) throws RPCExceptionBase
	{
		try
		{
			packer.packInt(v);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			logger.error( " putInt " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
	@Override
	public void putInt(Integer v) throws RPCExceptionBase
	{
		this.putInt(v.intValue());
	}
	@Override
	public void putLong(long v) throws RPCExceptionBase
	{
		try
		{
			packer.packLong(v);
		} catch (Exception e)
		{
			logger.error( " putLong long " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
	@Override
	public void putLong(Long v) throws RPCExceptionBase
	{
		this.putLong(v.longValue());
	}
	@Override
	public void putFloat(float v) throws RPCExceptionBase
	{
		try
		{
			packer.packFloat(v);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			logger.error( " putFloat " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
	@Override
	public void putFloat(Float v) throws RPCExceptionBase
	{
		this.putFloat(v.floatValue());
	}
	@Override
	public void putDouble(double v) throws RPCExceptionBase
	{
		try
		{
			packer.packDouble(v);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			logger.error( " putDouble " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
	@Override
	public void putDouble(Double v) throws RPCExceptionBase
	{
		this.putDouble(v.doubleValue());
	}
	@Override
	public void putString(String v) throws RPCExceptionBase
	{
		try
		{
			packer.packString(v);
		} catch (Exception e)
		{
			logger.error( " putString " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
	@Override
	public void putByte(Byte b) throws RPCExceptionBase
	{
		try
		{
			packer.packByte(b);
		} catch (Exception e)
		{
			logger.error( " putByte " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
	@Override
	public void putBytes(byte[] bytes) throws RPCExceptionBase
	{
		try
		{
			packer.packBinaryHeader(bytes.length);
			packer.writePayload(bytes);
		} catch (Exception e)
		{
			logger.error( " putBytes " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
	@Override
	public void putBool(boolean b) throws RPCExceptionBase
	{
		try
		{
			packer.packBoolean(b);
		} catch (Exception e)
		{
			logger.error( " putBool " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
	@Override
	public void putShort(short v) throws RPCExceptionBase
	{
		try
		{
			packer.packShort(v);
		} catch (Exception e)
		{
			logger.error( " putShort " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
		
	}
	@Override
	public void putShort(Short v) throws RPCExceptionBase
	{
		this.putShort(v.shortValue());
	}
	@Override
	public void putArrayHeader(int size) throws RPCExceptionBase
	{
		try
		{
			packer.packArrayHeader(size);
		} catch (Exception e)
		{
			logger.error( " putShort " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
	@Override
	public void putMapHeader(int size) throws RPCExceptionBase
	{
		try
		{
			packer.packMapHeader(size);
		} catch (Exception e)
		{
			logger.error( " putShort " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
	@Override
	public void close() throws RPCExceptionBase
	{
		// TODO Auto-generated method stub
		try
		{
			packer.close();
		} catch (Exception e)
		{
			logger.error( " close " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
	@Override
	public void putVoid() throws RPCExceptionBase
	{
		try
		{
			packer.packNil();
		} catch (Exception e)
		{
			logger.error( " putVoid " + e.toString());
			throw  RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Serialize_Exception, e.toString());
		}
	}
}
