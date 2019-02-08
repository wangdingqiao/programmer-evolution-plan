package com.my.rpc.runtime.runner;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;
import com.my.rpc.runtime.protocol.IRPCMessage;
import com.my.rpc.runtime.protocol.RPCCallInformation;
import com.my.rpc.runtime.protocol.RPCRequest;
import com.my.rpc.runtime.serializer.IDeserializer;

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
	
	public void export(final Class<?> interfaceCls, final Object obj)
	{
		MethodIndexer.getInstance().addObject(interfaceCls, obj);
	}
	
	private void dispatchCall(RPCCallInformation callInformation) throws RPCExceptionBase
	{
		long requestId = callInformation.getRequestInfo().getRequestId();
		try
		{
			RPCRequest request = callInformation.getRequestInfo();
			Class<?> interfaceClass = Class.forName(request.getInterfaceId());
			Object interfaceInstance = MethodIndexer.getInstance().getObject(interfaceClass);
			if(interfaceInstance == null)
			{
				throw new NullPointerException("dispatchCall interface " + interfaceClass.getName() + " implement not found.");
			}
			Method m = MethodIndexer.getInstance().getMethod(interfaceClass, request.getMethodId());
			Class<?>[] paramTypes = m.getParameterTypes();
//			System.out.println("dispatchCall request methodId= " + request.getMethodId() + " method name=" + m.getName());
			Object retVal = null;
			if(paramTypes.length > 0)
			{
				Object[] arguments = new Object[paramTypes.length];
				IDeserializer deser = callInformation.getDeserializer();
				for(int i = 0; i < paramTypes.length;++i)
				{
					IRPCMessage argument = (IRPCMessage)paramTypes[i].getConstructor().newInstance();
					argument.deserialize(deser);
					arguments[i] = argument;
				}
				deser.close();
				retVal = m.invoke(interfaceInstance, arguments);
			}
			else
			{
				retVal = m.invoke(interfaceInstance);
			}
			callInformation.reply((IRPCMessage)retVal);
		}
		catch (IllegalArgumentException | InvocationTargetException e)
		{
			logger.error("dispatchCall " + e.toString());
			callInformation.replyException(RPCExceptionFactory.createException(
					RPCExceptionFactory.RPC_InterfaceRuntime_Exception, 
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
			logger.error("dispatchCall requestId= "+ requestId + " " + e.toString());
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
	
	public void run()
	{
		try(ServerSocket ss = new ServerSocket(this.port))
		{
			logger.info("RPCServer start success.");
			while(true)
			{
				RPCRequest request = null;
				OutputStream oStream = null;
				try
				{
					Socket s = ss.accept();
					oStream = s.getOutputStream();
					request = new RPCRequest();
			    	request.readFromStream(s.getInputStream());
			    	RPCCallInformation callInformation = new RPCCallInformation(request, oStream);
					dispatchCall(callInformation);
					oStream.flush();
					oStream.close();
					s.close();
				}
				catch (Exception e) 
				{
					if(request != null && oStream != null)
					{
						logger.error("RPCServer dispatchCall  request=" + request.toString()  + " reply exception= " + e.toString());
						RPCCallInformation.replyException(RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Unknown_Exception, e), 
								request.getRequestId(), oStream);
					}
					else
					{
						logger.error("RPCServer dispatchCall exception=" + e.toString());
					}
				}
			}
		}
		catch (Exception e) 
		{
			logger.error("RPCServer exception=" + e.toString());
		}
	}
}