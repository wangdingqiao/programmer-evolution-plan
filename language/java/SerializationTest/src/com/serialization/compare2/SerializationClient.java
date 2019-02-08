package com.serialization.compare2;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
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
				DataOutputStream dos = null;
				if(useBufferedIO)
				{
					dos = new DataOutputStream(new BufferedOutputStream(sender.getOutputStream()));
				}
				else
				{
					dos = new DataOutputStream(sender.getOutputStream());
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
					
					// write data field using data type
					dos.writeInt(msg.type);
		            dos.writeBoolean(msg.active);
		            dos.writeUTF(msg.username);
		            dos.writeInt(msg.userid);
		            dos.writeUTF(msg.data);
		            dos.flush();
				}
				
				// send terminate message
				Message msg = new Message(); // 注意这里Message需要默认构造函数 否则会写入null时引起错误
				msg.type = Message.MESSAGE_TYPE_QUIT;
				dos.writeInt(msg.type);
	            dos.writeBoolean(msg.active);
	            dos.writeUTF(msg.username);
	            dos.writeInt(msg.userid);
	            dos.writeUTF(msg.data);
	            dos.flush();
	            dos.close();
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
