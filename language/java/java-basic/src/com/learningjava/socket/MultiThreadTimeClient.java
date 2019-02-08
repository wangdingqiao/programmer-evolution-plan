package com.learningjava.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MultiThreadTimeClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try
	        { 
	            Scanner scn = new Scanner(System.in); 
	              
	            // getting localhost ip 
	            InetAddress ip = InetAddress.getByName("localhost"); 
	      
	            // establish the connection with server port 5056 
	            // // Creates a stream socket and connects it to the specified port number at the specified IP address.
	            Socket s = new Socket(ip, 5056); 
	      
	            // obtaining input and out streams 
	            DataInputStream dis = new DataInputStream(s.getInputStream()); 
	            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
	      
	            // the following loop performs the exchange of 
	            // information between client and client handler 
	            while (true)  
	            { 
	                System.out.println(dis.readUTF()); 
	                String tosend = scn.nextLine(); 
	                dos.writeUTF(tosend); 
	                  
	                // If client sends exit,close this connection  
	                // and then break from the while loop 
	                if(tosend.equals("Exit")) 
	                { 
	                    System.out.println("Closing this connection : " + s); 
	                    s.close(); 
	                    System.out.println("Connection closed"); 
	                    break; 
	                } 
	                  
	                // printing date or time as requested by client 
	                String received = dis.readUTF(); 
	                System.out.println(received); 
	            } 
	              
	            // closing resources 
	            scn.close(); 
	            dis.close(); 
	            dos.close(); 
	        }catch(Exception e){ 
	            e.printStackTrace(); 
	        } 
	}
}
