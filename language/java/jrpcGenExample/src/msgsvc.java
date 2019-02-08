import java.io.IOException;
import java.net.InetAddress;

import org.acplt.oncrpc.OncRpcException;

public class msgsvc extends msg_msgserv_ServerStub 
{

	public msgsvc(InetAddress inetAddress, int port) throws OncRpcException, IOException
	{
		super(inetAddress, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String sendmsg_1(String msg)
	{
		// TODO Auto-generated method stub
		  System.out.println("got msg from client "+ msg);
	      return msg;
	}
	
	public static void main(String[] args) throws OncRpcException, IOException
	{
		new msgsvc(InetAddress.getLocalHost(), 2023).run(); 
	}
}
       
