package com.my.rpc.runtime.runner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

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
	
	protected void call(RPCRequest request, RPCCallBackBase callback) throws RPCExceptionBase
	{
		try
		{
			 request.setRequestId(IDManager.genId());
			 request.setRequestTime(System.currentTimeMillis());
			 logger.info("RPCClientStub begin call request request=" + request.toString());
			 
		  AsynchronousSocketChannel sockChannel = AsynchronousSocketChannel.open();
		  SocketAddress address = new InetSocketAddress("localhost", this.port);
		  logger.info(String.format("client connecting at %s", address));
	        //try to connect to the server side
	        sockChannel.connect( address, sockChannel, new CompletionHandler<Void, AsynchronousSocketChannel >() {
	            @Override
	            public void completed(Void result, AsynchronousSocketChannel channel ) {
	            	ClientAttachment attachment = new ClientAttachment();
	            	attachment.client = sockChannel;
	            	attachment.callback = callback;
	            	attachment.request = request;
	            	ByteBuffer buffer;
					try
					{
						buffer = request.writeToByteBuffer();
						buffer.flip();
						sockChannel.write(buffer, attachment,new ClientWriteHandler(sockChannel));
					} catch (RPCExceptionBase e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }

	            @Override
	            public void failed(Throwable exc, AsynchronousSocketChannel channel) {
	               
	            }
	            
	        });
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
		catch (Exception e) 
		{
			logger.error("RPCClientStub call   " + e.toString());
			throw RPCExceptionFactory.createException(RPCExceptionFactory.RPC_Unknown_Exception, "RPCClientStub call unknown exception.");
		}
	}
}

class ClientAttachment {
public	  AsynchronousSocketChannel client;
public	  ByteBuffer buffer;
public RPCCallBackBase callback;
public RPCRequest request;
}

class ClientReadHandler implements CompletionHandler<Integer, ClientAttachment> {
	//用于读取半包消息和发送应答
	private AsynchronousSocketChannel channel;
	public ClientReadHandler(AsynchronousSocketChannel channel) {
			this.channel = channel;
	}
	@Override
	public void completed(Integer result, ClientAttachment attachment)
	{
		// TODO Auto-generated method stub
		try
		{
//			System.out.println("read size= " + attachment.buffer.position());
    		RPCResponse rpcResponse = new RPCResponse();
	        rpcResponse.readFromByteBuffer(attachment.buffer);
	        if(attachment.callback.getMessage() != null)
	        {
	        	attachment.callback.getMessage().deserialize(rpcResponse.getMessageDeserializer());
	        }
	        attachment.callback.onSuccess();
	        attachment.buffer = null;
		} catch (RPCExceptionBase e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			attachment.callback.onFailed(e);
		}
	}
	@Override
	public void failed(Throwable exc, ClientAttachment attachment)
	{
		attachment.callback.onFailed(exc);
		try {
			this.channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ClientWriteHandler implements CompletionHandler<Integer, ClientAttachment>
{
	private AsynchronousSocketChannel channel;
	public ClientWriteHandler(AsynchronousSocketChannel channel) {
			this.channel = channel;
	}
	@Override
	public void completed(Integer result, ClientAttachment newAttach) {
	     newAttach.buffer = ByteBuffer.allocate(10 * 1024);
	     channel.read(newAttach.buffer, newAttach, new ClientReadHandler(channel));
	}
	@Override
	public void failed(Throwable exc, ClientAttachment attachment) {
		try {
			channel.close();
		} catch (IOException e) {
		}
	}
}
