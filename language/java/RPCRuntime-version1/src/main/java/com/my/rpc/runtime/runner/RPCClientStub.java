package com.my.rpc.runtime.runner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;
import com.my.rpc.runtime.protocol.RPCRequest;
import com.my.rpc.runtime.protocol.RPCResponse;

public class RPCClientStub extends RPCStub
{
	private static Logger logger = LogManager.getLogger(RPCRequest.class);
	private InetAddress host;
	int port;
	
	public RPCClientStub(Class<?> cls, InetAddress host, int port)
	{
		super(cls);
		this.host = host;
		this.port = port;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "RPCClientStub [ key= " + getStubKey() + " host=" + host + ", port=" + port + "]";
	}
	/**
	 * @return the host
	 */
	public InetAddress getHost()
	{
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(InetAddress host)
	{
		this.host = host;
	}
	/**
	 * @return the port
	 */
	public int getPort()
	{
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port)
	{
		this.port = port;
	}
	
	protected RPCResponse call(RPCRequest request) throws RPCExceptionBase
	{
		try
		{
			 request.setRequestId(IDManager.genId());
			 request.setRequestTime(System.currentTimeMillis());
			 logger.info("RPCClientStub begin call request request=" + request.toString());
			 Socket s = new Socket(this.host, this.port); 
	         // obtaining input and out streams 
			 BufferedInputStream bis = new BufferedInputStream(s.getInputStream()); 
			 BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream()); 
	         request.writeToStream(bos);
	         bos.flush();
	         RPCResponse rpcResponse = new RPCResponse();
	         rpcResponse.readFromStream(bis);
	         bos.close();
	         s.close();
	         if(rpcResponse.getException() != null)
	         {
	        	 throw rpcResponse.getException();
	         }
			 return rpcResponse;
		}
		catch (IOException e)
		{
			logger.error("RPCClientStub call  " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_IO_Exception, " RPCClientStub call failed to read data.");
		}
		catch(NullPointerException e)
		{
			logger.error("RPCClientStub call   " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_NullPointer_Exception, " RPCClientStub call null pointer.");
		}
		catch (RPCExceptionBase e) 
		{
			logger.error("RPCClientStub call   " + e.toString());
			throw e;
		}
		catch (Exception e) 
		{
			logger.error("RPCClientStub call   " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Unknown_Exception, "RPCClientStub call unknown exception.");
		}
	}
}
