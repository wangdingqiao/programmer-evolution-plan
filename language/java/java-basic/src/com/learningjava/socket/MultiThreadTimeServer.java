package com.learningjava.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MultiThreadTimeServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket ss = new ServerSocket(5056); // Creates a server socket, bound to the specified port.
		
		while(true)
		{
			Socket s = null;
			try
			{
				s = ss.accept();
				System.out.println("A new client is connected: " + s);
				
				// 获取输入输出了流
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				System.out.println("Assigning new thread for this client.");
				
				Thread thread = new ClientHandler(s, dis, dos);
				
				thread.start();
			}
			catch (Exception e) {
				s.close();
				e.printStackTrace();
			}
		}
	}

}

class ClientHandler extends Thread {
	
	DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
      
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 
  
    @Override
    public void run()  
    { 
        String received; 
        String toreturn; 
        while (true)  
        { 
            try { 
  
                // Ask user what he wants 
                dos.writeUTF("What do you want?[Date | Time]..\n"+ 
                            "Type Exit to terminate connection."); 
                  
                // receive the answer from client 
                received = dis.readUTF();   // using a modified UTF-8 format
                  
                if(received.equals("Exit")) 
                {  
                    System.out.println("Client " + this.s + " sends exit..."); 
                    System.out.println("Closing this connection."); 
                    this.s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
                  
                // creating Date object 
                Date date = new Date(); 
                  
                // write on output stream based on the 
                // answer from the client 
                switch (received) { 
                  
                    case "Date" : 
                        toreturn = fordate.format(date); 
                        dos.writeUTF(toreturn); 
                        break; 
                          
                    case "Time" : 
                        toreturn = fortime.format(date); 
                        dos.writeUTF(toreturn); 
                        break; 
                          
                    default: 
                        dos.writeUTF("Invalid input"); 
                        break; 
                } 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
          
        try
        { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
}

