package com.my.rpc.runtime.runner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

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
	public AsynchronousServerSocketChannel channel;
	public CountDownLatch latch;
	
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
	
	public void run()
	{
		try
		{
			latch = new CountDownLatch(1);
			AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
		    String host = "localhost";
		    InetSocketAddress sAddr = new InetSocketAddress(host, this.port);
		    server.bind(sAddr);
		    System.out.format("Server is listening at %s%n", sAddr);
		    Attachment attach = new Attachment();
		    attach.server = server;
		    attach.rpcServer = this;
		    server.accept(attach, new ConnectionHandler());
		    try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) 
		{
			logger.error("RPCServer exception=" + e.toString());
		}
	}
}


class ConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, Attachment> {
  @Override
  public void completed(AsynchronousSocketChannel clientChannel, Attachment attach) {
    if ((clientChannel != null) && (clientChannel.isOpen()))
	{
//        	 SocketAddress clientAddr = clientChannel.getRemoteAddress();
//             System.out.format("Accepted a  connection from  %s%n", clientAddr);
	     attach.server.accept(attach, this); //这里是递归使服务器保持监听状态
	     Attachment newAttach = new Attachment();
	     newAttach.server = attach.server;
	     newAttach.client = clientChannel;
	     newAttach.buffer = ByteBuffer.allocate(10 * 1024);
	     clientChannel.read(newAttach.buffer, newAttach, new ReadHandler(clientChannel, attach.rpcServer));
	}
  }

  @Override
  public void failed(Throwable e, Attachment attach) {
    System.out.println("Failed to accept a  connection.");
    e.printStackTrace();
  }
}

class ReadHandler implements CompletionHandler<Integer, Attachment> {
	//用于读取半包消息和发送应答
	private AsynchronousSocketChannel channel;
	private RPCServer rpcServer;
	public ReadHandler(AsynchronousSocketChannel channel, RPCServer rpcServer) {
			this.channel = channel;
			this.rpcServer = rpcServer;
	}
	@Override
	public void completed(Integer result, Attachment attachment)
	{
		// TODO Auto-generated method stub
		try
		{
    		attachment.buffer.flip();
    		RPCRequest request = new RPCRequest();
			request.readFromByteBuffer(attachment.buffer);
			RPCCallInformation callInformation = new RPCCallInformation(request);
			RPCResponse response = this.rpcServer.dispatchCall(callInformation);
			ByteBuffer writeBuffer = response.writeToByteBuffer();
			channel.write(writeBuffer, writeBuffer,new WriteHandler(this.channel));
		} catch (RPCExceptionBase e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void failed(Throwable exc, Attachment attachment)
	{
		// TODO Auto-generated method stub
		try {
			this.channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class WriteHandler implements CompletionHandler<Integer, ByteBuffer>
{
	private AsynchronousSocketChannel channel;
	public WriteHandler(AsynchronousSocketChannel channel) {
			this.channel = channel;
	}
	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		try {
			channel.close();
		} catch (IOException e) {
		}
	}
	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		try {
			channel.close();
		} catch (IOException e) {
		}
	}
}

class Attachment {
public RPCServer rpcServer = null;
public	  AsynchronousServerSocketChannel server;
public	  AsynchronousSocketChannel client;
public	  ByteBuffer buffer;
}