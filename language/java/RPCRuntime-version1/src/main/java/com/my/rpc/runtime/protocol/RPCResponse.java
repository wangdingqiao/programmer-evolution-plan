package com.my.rpc.runtime.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	
	private int contentSize;
	private long requestId;
	private RPCExceptionBase exception;
	private IRPCMessage message;
	private IDeserializer deser;
	
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
	
	public void readFromStream(InputStream input) throws RPCExceptionBase
	{
		try
		{
			final byte[] receiveSizeBytes = new byte[4];
            input.read(receiveSizeBytes);
            this.contentSize = ByteBuffer.wrap(receiveSizeBytes).getInt();
			if(this.contentSize <= 0)
			{
				throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Deserialize_Exception, "RPCResponse read content empty.");
			}
			byte[] contentBuff = new byte[this.contentSize];
			input.read(contentBuff);
			this.deser =  SerializeFactory.getDeserializer(contentBuff);
			this.requestId = deser.readLong();
			this.exception = RPCExceptionFactory.deserializeException(deser);
		}
		catch (IOException e)
		{
			logger.error("RPCResponse " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_IO_Exception, " RPCResponse read failed to read data.");
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
	
	public void writeToStream(OutputStream output) throws RPCExceptionBase
	{
		try
		{
			ISerializer  ser = SerializeFactory.getSerializer();
			ser.putLong(this.requestId);
			RPCExceptionFactory.serializeException(getException(), ser);
			if(this.message != null)
			{
				this.message.serialize(ser);
			}
			byte[] bytes = ser.toByteArray();
			ser.close();
			output.write(ByteBuffer.allocate(4).putInt(bytes.length).array());
			output.write(bytes);
		}
		catch (IOException e)
		{
			logger.error("RPCResponse " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_IO_Exception, " RPCResponse write failed to read data.");
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
