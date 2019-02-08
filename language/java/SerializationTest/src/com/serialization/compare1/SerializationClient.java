package com.serialization.compare1;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SerializationClient
{
	public static void main(String[] args)
	{
		Sender sender = new Sender(true);
		new Thread(sender).start();
	}
	
	static class Sender implements Runnable
	{
		private Boolean useBufferedIO;

		public Sender(Boolean useBufferdIO)
		{
			this.useBufferedIO = useBufferdIO;
		}
		
		@Override
		public void run()
		{
			try
			{
				Socket sender = new Socket("localhost", 8081);
				ObjectOutputStream oos = null;
				if(useBufferedIO)
				{
					oos = new ObjectOutputStream(new BufferedOutputStream(sender.getOutputStream()));
				}
				else
				{
					oos = new ObjectOutputStream(sender.getOutputStream());
				}
				
				System.out.println("Begin sending messages.");
				
				// send testing messages
				int sendCount = 1;
				while(sendCount < 1000000)
				{
					sendCount++;
					Message msg = new Message();
					msg.active = true;
					msg.userid = sendCount;
					msg.username = "User_" + sendCount;
					msg.data = this.toString();
					msg.type = Message.MESSAGE_TYPE_USER;
					oos.writeObject(msg);
					oos.flush();
				}
				
				// send terminate message
				Message msg = new Message();
				msg.type = Message.MESSAGE_TYPE_QUIT;
				oos.writeObject(msg);
				oos.flush();
				System.out.println("Stop sending messages.");
				sender.close();
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
