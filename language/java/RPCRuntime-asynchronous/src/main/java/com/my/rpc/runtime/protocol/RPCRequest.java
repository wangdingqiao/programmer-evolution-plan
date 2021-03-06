package com.my.rpc.runtime.protocol;

import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;
import com.my.rpc.runtime.serializer.IDeserializer;
import com.my.rpc.runtime.serializer.ISerializer;
import com.my.rpc.runtime.serializer.SerializeFactory;

public class RPCRequest
{
	private static Logger logger = LogManager.getLogger(RPCRequest.class);
	
	private int  contentSize;
	private long requestId;
	private String interfaceId;
	private int methodId;
	private long requestTime;
	private byte[] bodyData;
	private ISerializer  ser;
	private IDeserializer deser;
	
	public RPCRequest()
	{
		this.resetData();
	}
	
	private void resetData()
	{
		this.contentSize = 0;
		this.requestId = 0;
		this.interfaceId = "";
		this.methodId = 0;
		this.requestTime = 0;
		this.bodyData = new byte[0];
		this.ser = null;
		this.deser = null;
	}
	
	public void destory()
	{
		if(this.ser != null)
		{
			try
			{
				this.ser.close();
			} catch (RPCExceptionBase e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(this.deser != null)
		{
			try
			{
				this.deser.close();
			} catch (RPCExceptionBase e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void readFromByteBuffer(ByteBuffer buffer) throws RPCExceptionBase
	{
		try
		{
			this.deser =  SerializeFactory.getDeserializer(buffer);
			this.requestId = deser.readLong();
			this.interfaceId = deser.readString();
			this.methodId = deser.readInt();
			this.requestTime = deser.readLong();
			this.bodyData = deser.readBytes();
			this.deser.close();
		} 
		catch(NullPointerException e)
		{
			logger.error("RPCRequest " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_NullPointer_Exception, " RPCRequest read null pointer.");
		}
		catch (RPCExceptionBase e) 
		{
			logger.error("RPCRequest " + e.toString());
			throw e;
		}
		catch (Exception e) 
		{
			logger.error("RPCRequest " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Unknown_Exception, "RPCRequest read unknown exception.");
		}
	}
	
	public ByteBuffer writeToByteBuffer() throws RPCExceptionBase
	{
		try
		{
			this.ser = SerializeFactory.getSerializer();
			ser.putLong(this.requestId);
			ser.putString(this.interfaceId);
			ser.putInt(this.methodId);
			ser.putLong(this.requestTime);
			ser.putBytes(this.bodyData);
			return ser.toByteBuffer();
		}
		catch(NullPointerException e)
		{
			logger.error("RPCRequest " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_NullPointer_Exception, " RPCRequest write null pointer.");
		}
		catch (RPCExceptionBase e) 
		{
			logger.error("RPCRequest " + e.toString());
			throw e;
		}
		catch (Exception e) 
		{
			logger.error("RPCRequest " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Unknown_Exception, "RPCRequest write unknown exception= " + e);
		}
	}
	
	/**
	 * @return the contentSize
	 */
	public int getContentSize()
	{
		return contentSize;
	}
	/**
	 * @param contentSize the contentSize to set
	 */
	public void setContentSize(int contentSize)
	{
		this.contentSize = contentSize;
	}
	/**
	 * @return the requestId
	 */
	public long getRequestId()
	{
		return requestId;
	}
	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(long requestId)
	{
		this.requestId = requestId;
	}
	/**
	 * @return the interfaceId
	 */
	public String getInterfaceId()
	{
		return interfaceId;
	}
	/**
	 * @param interfaceId the interfaceId to set
	 */
	public void setInterfaceId(String interfaceId)
	{
		this.interfaceId = interfaceId;
	}
	/**
	 * @return the methodId
	 */
	public int getMethodId()
	{
		return methodId;
	}
	/**
	 * @param methodId the methodId to set
	 */
	public void setMethodId(int methodId)
	{
		this.methodId = methodId;
	}
	/**
	 * @return the requestTime
	 */
	public long getRequestTime()
	{
		return requestTime;
	}
	/**
	 * @param requestTime the requestTime to set
	 */
	public void setRequestTime(long requestTime)
	{
		this.requestTime = requestTime;
	}
	/**
	 * @return the bodyData
	 */
	public byte[] getBodyData()
	{
		return bodyData;
	}
	/**
	 * @param bodyData the bodyData to set
	 */
	public void setBodyData(byte[] bodyData)
	{
		this.bodyData = bodyData;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "RPCRequest [requestId=" + requestId + ", interfaceId=" + interfaceId + ", methodId=" + methodId
				+ ", requestTime=" + requestTime + "]";
	}
}
