package com.learning.rpc.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcFramework 
{
	
	 /**
	  * 暴露服务
	  * @param service 服务实现
	  * @param port    服务端口
	  * @throws Exception
	  */
	 public static void export(final Object service, int port) throws Exception 
	 {
		if(service == null) 
		{
			throw new IllegalArgumentException("service instance is null.");
		}
		if(port <= 0 || port > 65535) 
		{
			throw new IllegalArgumentException("Invalid port " + port);
		}
		
		System.out.println("Export service" + service.getClass().getName()  + " on port: " + port);
		
		// 建立 服务端 rpc socket
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(port);
		while(true) 
		{
			final Socket socket = serverSocket.accept();
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					ObjectOutputStream output = null;
					try
					{
						ObjectInputStream input = new ObjectInputStream(socket.getInputStream()); // an input stream for reading bytes from this socket.
						String methodName = input.readUTF();
						Class<?>[] paramTypes = (Class<?>[])input.readObject();
						Object[] arguments = (Object[])input.readObject();
						output = new ObjectOutputStream(socket.getOutputStream());  // an output stream for writing bytes to this socket.
						Method method = service.getClass().getMethod(methodName, paramTypes);
						Object result = method.invoke(service, arguments);
						output.writeObject(result);
					} catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException 
							| IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
					{
						e.printStackTrace();
						if(output != null)
						{
							try
							{
								output.writeObject(e);
							} catch (IOException e1)
							{
								e1.printStackTrace();
							}
						}
					}
					finally {
						try
						{
							socket.close();
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		
	}
	 
	 
	@SuppressWarnings("unchecked")
	/*
	 * 使用了泛型的动态代理生成方法
	 */
	public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) throws Exception
	{
		if(interfaceClass == null)
		{
			throw new IllegalArgumentException("Interface class == null");
		}
		if(!interfaceClass.isInterface())
		{
			throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class !");
		}
		if(host == null || host.length() == 0)
		{
			throw new IllegalArgumentException("Host == null!");		
		}
		if(port <= 0 || port > 65535) 
		{
			throw new IllegalArgumentException("Invalid port " + port);
		}
		System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ": " + port);
		return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new InvocationHandler()
		{
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
			{
				Socket socket = new Socket(host, port);
				ObjectOutputStream outputStream = null;
				ObjectInputStream inputStream = null;
				try
				{
					outputStream = new ObjectOutputStream(socket.getOutputStream());
					outputStream.writeUTF(method.getName());
					outputStream.writeObject(method.getParameterTypes());
					outputStream.writeObject(args);
					inputStream = new ObjectInputStream(socket.getInputStream());
					Object result = inputStream.readObject();
					if(result instanceof Throwable)
					{
						throw (Throwable)result;
					}
					return result;
				} catch (Exception e)
				{
					e.printStackTrace();
					throw e;
				}
				finally {
					if(inputStream != null)
					{
						inputStream.close();
					}
					if(outputStream != null)
					{
						outputStream.close();
					}
					socket.close();
				}
			}
		});
	}
}
