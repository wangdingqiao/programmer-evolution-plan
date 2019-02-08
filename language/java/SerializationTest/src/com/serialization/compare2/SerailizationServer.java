package com.serialization.compare2;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SerailizationServer 
{
	public static void main(String[] args)
	{
		System.out.println("server runnig");
		ServerSocket socket = null;
		try
		{
			socket = new ServerSocket(8081);
			while(true)
			{
				Socket client = socket.accept();
				Listener listener = new Listener(client, true);
				new Thread(listener).start();
			}
		} catch (Exception e)
		{
			if(socket != null)
			{
				try
				{
					socket.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
	
	static class Listener implements Runnable
	{
		private Socket client = null;
		private long startTimeStamp = -1;
		private int messageReceived = 0;
		private boolean useBufferdIO = false;

		public Listener(Socket client, boolean useBufferedIO)
		{
			this.client = client;
			this.useBufferdIO = useBufferedIO;
		}
		
		@Override
		public void run()
		{
			try
			{
				DataInputStream dis = null;
				if(this.useBufferdIO)
				{
					dis = new DataInputStream(new BufferedInputStream(client.getInputStream()));
				}
				else
				{
					dis = new DataInputStream(client.getInputStream());
				}
				
				System.out.println(this + " listening for message.");
				while(true)
				{
					Message msg = new Message();
			        msg.type = dis.readInt();
			        msg.active = dis.readBoolean();
			        msg.username = dis.readUTF();
			        msg.userid = dis.readInt();
			        msg.data = dis.readUTF();
			        
					messageReceived++;
					if(startTimeStamp == -1)
					{
						startTimeStamp = System.currentTimeMillis();
					}
					if(msg.type == Message.MESSAGE_TYPE_QUIT)
					{
						break;
					}
				}
				
				long endTimeStamp = System.currentTimeMillis();
				double seconds = ((double)(endTimeStamp - startTimeStamp) / (double) 1000);
				int rate = (int)(messageReceived / seconds);
				System.out.println("Received " + messageReceived + " objects at " + rate + " per seconds.");
				
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			System.out.println(this + " terminating.");
			System.exit(0);
		}
	}
}