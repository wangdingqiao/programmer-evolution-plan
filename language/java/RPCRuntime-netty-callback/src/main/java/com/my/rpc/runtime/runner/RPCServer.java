package com.my.rpc.runtime.runner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.runtime.exception.RPCExceptionFactory;
import com.my.rpc.runtime.protocol.RPCCallInformation;
import com.my.rpc.runtime.protocol.RPCRequest;
import com.my.rpc.runtime.protocol.RPCResponse;
import com.my.rpc.runtime.serviceManager.RPCServiceServerManager;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.channel.socket.SocketChannel;

public class RPCServer 
{
	private static Logger logger = LogManager.getLogger(RPCServer.class);
	private int port;
	private EventLoopGroup group;
	
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
	
	public void stop() throws InterruptedException
	{
		group.shutdownGracefully().sync();
	}
	
	public void run()
	{
		EventLoopGroup group = new NioEventLoopGroup();
		try{
		    ServerBootstrap serverBootstrap = new ServerBootstrap();
		    serverBootstrap.group(group);
		    serverBootstrap.channel(NioServerSocketChannel.class);
		    InetSocketAddress sAddr = new InetSocketAddress("localhost", this.port);
		    serverBootstrap.localAddress(sAddr);
		    logger.info(String.format("Server is listening at %s%n", sAddr));
		    serverBootstrap.childHandler(new ChildChannelHandler(this));
		    ChannelFuture channelFuture = serverBootstrap.bind().sync();
		    channelFuture.channel().closeFuture().sync();
		} catch(Exception e){
		    e.printStackTrace();
		} finally {
		    try
			{
				group.shutdownGracefully().sync();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		private RPCServer server;
		public ChildChannelHandler(RPCServer server)
		{
			this.server = server;
		}
		@Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new RequestHandler(this.server));
        }
    }
}
	
class RequestHandler extends ChannelInboundHandlerAdapter {
	
	private RPCServer server;
	private static Logger logger = LogManager.getLogger(RequestHandler.class);
	
	public RequestHandler(RPCServer server)
	{
		this.server = server;
	}
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	RPCRequest request = null;
    	try
    	{
    		ByteBuf buf = (ByteBuf) msg;
//    		System.out.println("channelRead buf size=" + buf.capacity());
    		request = new RPCRequest();
    		request.readFromByteBuffer(buf.nioBuffer());
			RPCCallInformation callInformation = new RPCCallInformation(request);
			RPCResponse response = this.server.dispatchCall(callInformation);
			ByteBuffer retBuff = response.writeToByteBuffer();
			ChannelFuture cf = ctx.writeAndFlush(Unpooled.copiedBuffer(retBuff));
			if (!cf.isSuccess()) {
				logger.error("RPCServer dispatchCall Send failed: " + cf.cause());
			}
			ctx.close();
			ReferenceCountUtil.release(buf);
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

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}