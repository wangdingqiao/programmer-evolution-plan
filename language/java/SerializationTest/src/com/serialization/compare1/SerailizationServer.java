package com.serialization.compare1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
				ObjectInputStream ois = null;
				if(this.useBufferdIO)
				{
					ois = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
				}
				else
				{
					ois = new ObjectInputStream(client.getInputStream());
				}
				
				System.out.println(this + " listening for message.");
				while(true)
				{
					Message message = (Message)ois.readObject();
					messageReceived++;
					if(startTimeStamp == -1)
					{
						startTimeStamp = System.currentTimeMillis();
					}
					if(message.type == Message.MESSAGE_TYPE_QUIT)
					{
						break;
					}
				}
				
				long endTimeStamp = System.currentTimeMillis();
				double seconds = ((double)(endTimeStamp - startTimeStamp) / (double) 1000);
				int rate = (int)(messageReceived / seconds);
				System.out.println("Received " + messageReceived + " objects at " + rate + " per seconds.");
				
			} catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			System.out.println(this + " terminating.");
			System.exit(0);
		}
	}
}