package com.my.rpc.runtime.runner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;
import com.my.rpc.runtime.protocol.IRPCMessage;
import com.my.rpc.runtime.protocol.RPCRequest;
import com.my.rpc.runtime.protocol.RPCResponse;
import com.my.rpc.runtime.serializer.ISerializer;
import com.my.rpc.runtime.serializer.SerializeFactory;

public class RPCClient
{
	private static Logger logger = LogManager.getLogger(RPCRequest.class);
	private InetAddress host;
	int port;
	Socket socket;
	
	public RPCClient(InetAddress host, int port)
	{
		this.host = host;
		this.port = port;
	}
	
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
			 Socket s = new Socket();
			 s.setReuseAddress(true);
			 SocketAddress address = new InetSocketAddress(this.host, this.port);
			 s.connect(address); 
//			 Socket s = new Socket(this.host, this.port);
	         // obtaining input and out streams 
			 BufferedInputStream bis = new BufferedInputStream(s.getInputStream()); 
			 BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream()); 
	         request.writeToStream(bos);
	         bos.flush();
//	         logger.info("RPCClientStub success write to request to stream, request=" + request.toString());
	         RPCResponse rpcResponse = new RPCResponse();
	         rpcResponse.readFromStream(bis);
	         bos.close();
	         s.close();
			 return rpcResponse;
		}
		catch (IOException e)
		{
			logger.error("RPCClientStub call  " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_IO_Exception, " RPCClientStub call failed to read data.");
		}
		catch(NullPointerException e)
		{
			logger.error("RPCClient call" + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_NullPointer_Exception, " RPCClientStub call null pointer.");
		}
		catch (RPCExceptionBase e) 
		{
			logger.error("RPCClient call" + e.toString());
			throw e;
		}
		catch (Exception e) 
		{
			logger.error("RPCClient call" + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Unknown_Exception, "RPCClientStub call unknown exception.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public  <T> T refer(final Class<T> interfaceClass) throws RPCExceptionBase 
	{
		T proxyClass = (T) MethodIndexer.getInstance().getObject(interfaceClass);
		if(proxyClass != null)
		{
			return proxyClass;
		}
		proxyClass = (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new InvocationHandler() 
		{
            public Object invoke(Object proxy, Method method, Object[] arguments) 
            		throws RPCExceptionBase, InstantiationException, IllegalAccessException, IllegalArgumentException, 
            		InvocationTargetException, NoSuchMethodException, SecurityException 
            {
            	 	RPCRequest request = new RPCRequest();
            	 	request.setRequestId(IDManager.genId());
        			request.setRequestTime(System.currentTimeMillis());
            	    request.setRequestTime(System.currentTimeMillis());
            	    ISerializer ser = SerializeFactory.getSerializer();
            	    for(Object argument: arguments)
            	    {
            	    	IRPCMessage message = (IRPCMessage)argument;
            	    	message.serialize(ser);
            	    }
            	    request.setInterfaceId(interfaceClass.getName());
            	    int index = MethodIndexer.getInstance().getMethodIndex(interfaceClass, method.getName());
            	    request.setMethodId(index);
//            	    System.out.println("dispatchCall requestId= " + request.getRequestId() + "  methodId= " + request.getMethodId() + " method name=" + method.getName());
            	    request.setBodyData(ser.toByteArray());
            	    ser.close();
            	    RPCResponse response = call(request);
            	    if(response.getException() != null)
            	    {
            	    	throw response.getException();
            	    }
            	    Class<?> retType = method.getReturnType();
            	    Object retValue = retType.getDeclaredConstructor().newInstance();
            	    ((IRPCMessage)retValue).deserialize(response.getMessageDeserializer());
            	    return retValue;
            }
        });
		MethodIndexer.getInstance().addObject(interfaceClass, proxyClass);
		return proxyClass;
    }
}
