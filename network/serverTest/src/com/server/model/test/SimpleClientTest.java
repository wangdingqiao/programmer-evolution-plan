package com.server.model.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SimpleClientTest 
{
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException
	{
		long t_before = System.currentTimeMillis();
		ArrayList<Thread> thread_collections = new ArrayList<>();
		for(int i = 0; i < 3;++i)
		{
			Thread t = new Thread(new SendThead("conn"+i, "localhost", 9090));
			thread_collections.add(t);
			t.start();
		}
		// wait all thread to complete
		for(Thread t: thread_collections)
		{
			t.join();
		}
		long t_after = System.currentTimeMillis();
		System.out.println("Elapsed time=" + (t_after - t_before));
	}
}

class ReadThead implements Runnable
{
	private String name;
	private Socket socket;
	
	public ReadThead(String name, Socket s)
	{
		this.name = name;
		this.socket = s;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				InputStream in = this.socket.getInputStream();
				byte[] buf = new byte[8 * 1024];
				in.read(buf);
				String line = new String(buf);
				System.out.println(String.format("%s received %s",this.name, line));
				if(line.contains("1111"))
				{
					break;
				}
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class SendThead implements Runnable
{
	private String name;
	private Socket socket;
	private String address;
	private int port;
	
	public SendThead(String name, String address, int port) throws UnknownHostException, IOException
	{
		this.name = name;
		this.socket = new Socket(); 
		this.address = address;
		this.port = port;
	}
	
	@Override
	public void run() 
	{
		try
		{

			// TODO Auto-generated method stub
			SocketAddress address = new InetSocketAddress(this.address, this.port);
			this.socket.connect(address);
			InputStream is = this.socket.getInputStream();
			byte[] bytes = new byte[1];
			is.read(bytes);
			if(!new String(bytes).equals("*"))
			{
				System.err.print("Something is wrong! Did not receive *");
			}
			System.out.println(String.format("%s connected...", this.name));

		    Thread rthread = new Thread(new ReadThead(name, this.socket));
		    rthread.start();

		    String s = "^abc$de^abte$f";
		    System.out.println(String.format("%s sending %s", this.name, s));
		    PrintWriter pw =  new PrintWriter(this.socket.getOutputStream());
		    pw.write(s);
		    pw.flush();
		    Thread.sleep(1000);

		    s = "xyz^123";
		    System.out.println(String.format("%s sending %s", this.name, s));
		    pw.write(s);
		    pw.flush();
		    Thread.sleep(1000);

//		    The 0000 sent to the server here will result in an echo of 1111, which is
//		    sign for the reading thread to terminate.
//		    Add WXY after 0000 to enable kill-switch in some servers.
		    s = "25$^ab0000$abab";
		    System.out.println(String.format("%s sending %s", this.name, s));
		    pw.write(s);
		    pw.flush();
		    Thread.sleep(200);

		    rthread.join();
		    pw.close();
		    System.out.println(String.format("%s disconnecting", this.name));
		    this.socket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
