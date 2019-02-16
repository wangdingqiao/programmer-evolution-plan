package com.my.rpc.runtime.runner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;
import com.my.rpc.runtime.protocol.RPCCallInformation;
import com.my.rpc.runtime.protocol.RPCRequest;
import com.my.rpc.runtime.protocol.RPCResponse;
import com.my.rpc.runtime.serviceManager.RPCServiceServerManager;


public class RPCServer 
{
	private static Logger logger = LogManager.getLogger(RPCServer.class);
	private int port;
	private Selector selector;
	private ServerSocketChannel serverChannel;
	private volatile boolean started;

	public RPCServer(int port)
	{
		this.port = port;
		this.started = true;
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
	
	RPCResponse dispatchCall(RPCCallInformation callInformation) throws RPCExceptionBase
	{
		try
		{
			RPCRequest request = callInformation.getRequestInfo();
			RPCServerStub stub = getStub(request.getInterfaceId());
			
			if(stub == null)
			{
				logger.error("RPCServer dispatchCall failed to get stub with key =" + request.getInterfaceId());
				RPCResponse response = new RPCResponse(request.getRequestId(),RPCExceptionFactory.createException(
						RPCExceptionFactory.RPC_InterfaceNotFound_Exception, 
						" interface not found."));
				return response;
			}
			if(!stub.isFunctionOn())
			{
				RPCResponse response = new RPCResponse(request.getRequestId(),RPCExceptionFactory.createException(
						RPCExceptionFactory.RPC_MethodFunctionOff_Exception, 
						" interface turned off "));
				return response;
			}
			return stub.dispatchCall(callInformation);
		}
		catch (IOException e)
		{
			logger.error("dispatchCall " + e.toString());
			RPCResponse response = new RPCResponse(callInformation.getRequestInfo().getRequestId(),RPCExceptionFactory.createException(
					RPCExceptionFactory.RPC_IO_Exception, 
					" dispatchCall exception= " + e.toString()));
			return response;
		}
		catch(NullPointerException e)
		{
			logger.error("dispatchCall " + e.toString());
			RPCResponse response = new RPCResponse(callInformation.getRequestInfo().getRequestId(),
					RPCExceptionFactory.createException(RPCExceptionFactory.RPC_NullPointer_Exception, 
					" dispatchCall exception= " + e.toString()));
			return response;
		}
		catch (RPCExceptionBase e) 
		{
			RPCResponse response = new RPCResponse(callInformation.getRequestInfo().getRequestId(),e);
			return response;
		}
		catch (Exception e) 
		{
			RPCResponse response = new RPCResponse(callInformation.getRequestInfo().getRequestId(),RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Unknown_Exception, 
					" dispatchCall exception= " + e.toString()));
			return response;
		}
	}
	
	public void stop()
	{
		started = false;
		try
		{
			this.selector.close();
			this.serverChannel.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		
		try
		{
			this.selector = Selector.open();
	        this.serverChannel = ServerSocketChannel.open();
	        InetSocketAddress sAddr = new InetSocketAddress("localhost", this.port);
	        this.serverChannel.bind(sAddr);
	        this.serverChannel.configureBlocking(false);
	        this.serverChannel.register(selector, SelectionKey.OP_ACCEPT);
	        logger.info(String.format("Server is listening at %s%n", sAddr));
			while(started)
			{
				try{
					//无论是否有读写事件发生，selector每隔1s被唤醒一次
					selector.select();
					Set<SelectionKey> keys = selector.selectedKeys();
					Iterator<SelectionKey> it = keys.iterator();
					SelectionKey key = null;
					while(it.hasNext()){
						key = it.next();
						try
						{
							if (key.isAcceptable()) {
			                    register(selector, this.serverChannel);
			                }
			                if (key.isReadable()) {
			                	handleRequest(key);
			                }
						}
						catch(Exception e)
						{
							if(key != null)
							{
								key.cancel();
								if(key.channel() != null)
								{
									key.channel().close();
								}
							}
						}
						it.remove();
					}
				}catch(Throwable t){
					t.printStackTrace();
				}
			}
			//selector关闭后会自动释放里面管理的资源
			if(selector != null)
				try{
					selector.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
		}
		catch (Exception e) 
		{
			logger.error("RPCServer exception=" + e.toString());
		}
	}
	
	
private  void register(Selector selector, ServerSocketChannel serverSocket)
	      throws IOException 
{
	  
    SocketChannel client = serverSocket.accept();
    client.configureBlocking(false);
    client.register(selector, SelectionKey.OP_READ);
}
	
private void handleRequest(SelectionKey key) throws IOException
{
	if(!key.isValid())
	{
		return;
	}
	RPCRequest request = null;
	try
	{
		SocketChannel sc = (SocketChannel) key.channel();
		request = new RPCRequest();
		ByteBuffer buffer = ByteBuffer.allocate(2 * 1024);
		int readBytes = sc.read(buffer);
		if(readBytes > 0)
		{
			buffer.flip();
			request.readFromByteBuffer(buffer);
			RPCCallInformation callInformation = new RPCCallInformation(request);
			RPCResponse response = dispatchCall(callInformation);
			ByteBuffer retBuff = response.writeToByteBuffer();
			sc.write(retBuff);
			// 目前实现为连接一次就关闭
			key.cancel();
			sc.close();
		}
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
}