package com.my.rpc.runtime.protocol;

import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;
import com.my.rpc.runtime.serializer.IDeserializer;
import com.my.rpc.runtime.serializer.ISerializer;
import com.my.rpc.runtime.serializer.SerializeFactory;

public class RPCResponse
{
	private static Logger logger = LogManager.getLogger(RPCRequest.class);
	
	private long requestId;
	private RPCExceptionBase exception;
	private IRPCMessage message;
	private IDeserializer deser;
	private ISerializer  ser;
	
	public RPCResponse(long requestId, IRPCMessage message)
	{
		this.requestId = requestId;
		this.message = message;
		this.exception = null;
		this.deser = null;
	}
	
	public RPCResponse(long requestId, RPCExceptionBase exception)
	{
		this.requestId = requestId;
		this.exception = exception;
		this.message = null;
		this.deser = null;
	}
	
	public RPCResponse()
	{
		this.requestId = 0;
		this.message = null;
		this.exception = null;
		this.deser = null;
	}
	public void destory()
	{
		if(this.deser != null)
		{
			try
			{
				this.deser.close();
			} catch (RPCExceptionBase e)
			{
				e.printStackTrace();
			}
		}
		if(this.ser != null)
		{
			try
			{
				this.ser.close();
			} catch (RPCExceptionBase e)
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * @return the exception
	 */
	public RPCExceptionBase getException()
	{
		return exception;
	}
	
	/**
	 * @return the requestId
	 */
	public long getRequestId()
	{
		return requestId;
	}
	
	public IDeserializer getMessageDeserializer()
	{
		return this.deser;
	}
	
	public void readFromByteBuffer(ByteBuffer buffer) throws RPCExceptionBase
	{
		try
		{
			buffer.flip();
			this.deser =  SerializeFactory.getDeserializer(buffer);
			this.requestId = deser.readLong();
			this.exception = RPCExceptionFactory.deserializeException(deser);
		}
		catch(NullPointerException e)
		{
			logger.error("RPCResponse " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_NullPointer_Exception, " RPCResponse read null pointer.");
		}
		catch (RPCExceptionBase e) 
		{
			logger.error("RPCResponse " + e.toString());
			throw e;
		}
		catch (Exception e) 
		{
			logger.error("RPCResponse " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Unknown_Exception, "RPCResponse read unknown exception.");
		}
	}
	
	public ByteBuffer writeToByteBuffer() throws RPCExceptionBase
	{
		try
		{
			this.ser = SerializeFactory.getSerializer();
			this.ser.putLong(this.requestId);
			RPCExceptionFactory.serializeException(getException(), this.ser);
			if(this.message != null)
			{
				this.message.serialize(this.ser);
			}
			return this.ser.toByteBuffer();
		}
		catch(NullPointerException e)
		{
			logger.error("RPCResponse " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_NullPointer_Exception, " RPCResponse write null pointer.");
		}
		catch (RPCExceptionBase e) 
		{
			logger.error("RPCResponse " + e.toString());
			throw e;
		}
		catch (Exception e) 
		{
			logger.error("RPCResponse " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Unknown_Exception, "RPCResponse write unknown exception.");
		}
	}
}
