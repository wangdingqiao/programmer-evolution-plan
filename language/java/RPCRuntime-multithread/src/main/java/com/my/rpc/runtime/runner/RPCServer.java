package com.my.rpc.runtime.runner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;
import com.my.rpc.runtime.protocol.RPCCallInformation;
import com.my.rpc.runtime.protocol.RPCRequest;
import com.my.rpc.runtime.serviceManager.RPCServiceServerManager;

public class RPCServer 
{
	private static Logger logger = LogManager.getLogger(RPCServer.class);
	private int port;

	public RPCServer(int port)
	{
		this.port = port;
	}
	
	/**
	 * @return the port
	 */
	public int getPort()
	{
		return port;
	}
	
	public boolean registStub(RPCServerStub stub)
	{
		return RPCServiceServerManager.getInstance().registStub(stub);
	}
	
	public RPCServerStub getStub(String stubkey)
	{
		return RPCServiceServerManager.getInstance().getStub(stubkey);
	}
	
	public void run()
	{
		try(ServerSocket ss = new ServerSocket(this.port))
		{
			while(true)
			{
				try
				{
					Socket s = ss.accept();
					new Thread(new ClientHandler(this, s)).start();
				}
				catch (Exception e) 
				{
					logger.error("RPCServer exception=" + e.toString());
				}
			}
		}
		catch (Exception e) 
		{
			logger.error("RPCServer run exception=" + e.toString());
		}
	}
}

class ClientHandler implements Runnable
{
	private final Socket s;
	private final RPCServer server;
	private static Logger logger = LogManager.getLogger(ClientHandler.class);
	public ClientHandler(RPCServer server, Socket s)
	{
		this.server = server;
		this.s = s;
	}
	
	@Override
	public void run()
	{
		RPCRequest request = null;
		try
		{
			// TODO Auto-generated method stub
			request = new RPCRequest();
	    	request.readFromStream(s.getInputStream());
	    	OutputStream oStream = s.getOutputStream();
	    	RPCCallInformation callInformation = new RPCCallInformation(request, oStream);
	    	dispatchCall(callInformation);
			oStream.flush();
			oStream.close();
			s.close();
		}
		catch (Exception e) 
		{
			if(request != null)
			{
				logger.error("RPCServer dispatchCall  request=" + request.toString()  + " exception= " + e.toString());
			}
			else
			{
				logger.error("RPCServer dispatchCall exception=" + e.toString());
			}
		}
	}
	
	private void dispatchCall(RPCCallInformation callInformation) throws RPCExceptionBase
	{
		try
		{
			RPCRequest request = callInformation.getRequestInfo();
			RPCServerStub stub = this.server.getStub(request.getInterfaceId());
			if(stub == null)
			{
				logger.error("RPCServer dispatchCall failed to get stub with key =" + request.getInterfaceId());
				callInformation.replyException(RPCExceptionFactory.createException(
						RPCExceptionFactory.RPC_InterfaceNotFound_Exception, 
						" interface not found."));
				return;
			}
			if(!stub.isFunctionOn())
			{
				callInformation.replyException(RPCExceptionFactory.createException(
						RPCExceptionFactory.RPC_MethodFunctionOff_Exception, 
						" interface turned off "));
				return;
			}
			stub.dispatchCall(callInformation);
		}
		catch (IOException e)
		{
			logger.error("dispatchCall " + e.toString());
			callInformation.replyException(RPCExceptionFactory.createException(
					RPCExceptionFactory.RPC_IO_Exception, 
					" dispatchCall exception= " + e.toString()));
		}
		catch(NullPointerException e)
		{
			logger.error("dispatchCall " + e.toString());
			callInformation.replyException(
					RPCExceptionFactory.createException(RPCExceptionFactory.RPC_NullPointer_Exception, 
							" dispatchCall exception= " + e.toString()));
		}
		catch (RPCExceptionBase e) 
		{
			callInformation.replyException(e);
		}
		catch (Exception e) 
		{
			logger.error("dispatchCall " + e.toString());
			callInformation.replyException(
					RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Unknown_Exception, 
							" dispatchCall exception= " + e.toString()));
		}
	}
}