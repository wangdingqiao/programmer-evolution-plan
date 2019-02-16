package nioExamples;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.Future;

/*
 * Example from: http://www.java2s.com/Tutorials/Java/Java_Network/0080__Java_Network_Asynchronous_Socket_Channels.htm
 */

public class AsynchronousSocketChannelExample2 {
  public static void main(String[] args) throws Exception 
  {
    
	  Thread serverThread = new Thread(new Runnable() {
	      @Override
	      public void run() {
	        try
			{
				serverStart();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	    });
	    serverThread.start();
	    Thread clientThread = new Thread(new Runnable() {
	      @Override
	      public void run() {
	        clientStart();
	      }
	    });
	    clientThread.start();
  }

private static void clientStart() {
    try {
      InetSocketAddress  hostAddress = new InetSocketAddress(InetAddress.getByName("127.0.0.1"),8989);
      AsynchronousSocketChannel clientSocketChannel = AsynchronousSocketChannel
          .open();
      Future<Void> connectFuture = clientSocketChannel.connect(hostAddress);
      connectFuture.get(); // Wait until connection is done.
      for (int i = 0; i < 5; i++) {
        String msg = "Look at me " + i;
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        clientSocketChannel.write(buffer);
        System.out.println("client send msg=" + msg);
        Thread.sleep(1000);
      }
      String msg = "EOF";
      ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
      clientSocketChannel.write(buffer);
      clientSocketChannel.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

private static void serverStart() throws IOException
{
	AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel
	        .open();
    String host = "localhost";
    int port = 8989;
    InetSocketAddress sAddr = new InetSocketAddress(host, port);
    server.bind(sAddr);
    System.out.format("Server is listening at %s%n", sAddr);
    Attachment attach = new Attachment();
    attach.server = server;
    server.accept(attach, new ConnectionHandler());
}
}

class Attachment {
	  AsynchronousServerSocketChannel server;
	  AsynchronousSocketChannel client;
	  ByteBuffer buffer;
	  SocketAddress clientAddr;
	  boolean isRead;
}

class ConnectionHandler implements
    CompletionHandler<AsynchronousSocketChannel, Attachment> {
  @Override
  public void completed(AsynchronousSocketChannel client, Attachment attach) {
    try {
      SocketAddress clientAddr = client.getRemoteAddress();
      System.out.format("Accepted a  connection from  %s%n", clientAddr);
      attach.server.accept(attach, this); //这里是递归使服务器保持监听状态
      ReadWriteHandler rwHandler = new ReadWriteHandler();
      Attachment newAttach = new Attachment();
      newAttach.server = attach.server;
      newAttach.client = client;
      newAttach.buffer = ByteBuffer.allocate(2048);
      newAttach.isRead = true;
      newAttach.clientAddr = clientAddr;
      client.read(newAttach.buffer, newAttach, rwHandler);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void failed(Throwable e, Attachment attach) {
    System.out.println("Failed to accept a  connection.");
    e.printStackTrace();
  }
}

class ReadWriteHandler implements CompletionHandler<Integer, Attachment> {
  @Override
  public void completed(Integer result, Attachment attach) {
    if (result == -1) {
      try {
        attach.client.close();
        System.out.format("Stopped   listening to the   client %s%n",
            attach.clientAddr);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      return;
    }
    if (attach.isRead) 
	{
      attach.buffer.flip();
      int limits = attach.buffer.limit();
      byte bytes[] = new byte[limits];
      attach.buffer.get(bytes, 0, limits);
      Charset cs = Charset.forName("UTF-8");
      String msg = new String(bytes, cs);
      System.out.format("Client at  %s  says: %s%n", attach.clientAddr,
          msg);
      attach.isRead = false; // It is a write
      attach.buffer.rewind();
      if(msg.equals("EOF"))
      {
    	  try {
    	        attach.client.close();
    	        System.out.format("Stopped   listening to the   client %s%n",
    	            attach.clientAddr);
    	      } catch (IOException ex) {
    	        ex.printStackTrace();
    	      }
    	  return;
      }
      attach.isRead = true;
      attach.buffer.clear();
      attach.client.read(attach.buffer, attach, this);
	} 
	else 
	{
      // Write to the client
      attach.client.write(attach.buffer, attach, this);
      attach.isRead = true;
      attach.buffer.clear();
      attach.client.read(attach.buffer, attach, this);
	}
  }

  @Override
  public void failed(Throwable e, Attachment attach) {
    e.printStackTrace();
  }
}