import java.io.IOException;
import java.net.InetAddress;
import org.acplt.oncrpc.OncRpcException;
import org.acplt.oncrpc.OncRpcProtocols;

public class ClientTest
{
	public static void main(String[] args)
	{
		try
		{
			msg_msgserv_Client cl = new msg_msgserv_Client(InetAddress.getLocalHost(), 2023, OncRpcProtocols.ONCRPC_TCP);
			String msg = "hello world\n";
			System.out.println("sending.. ");
			cl.getClient().setTimeout(5 * 1000);
			for(int i=0; i<5; i++)
			{                                
			  String reply = cl.sendmsg_1(msg);
		  	  System.out.println("got " + reply +"\n");   
		    }
		} catch (OncRpcException | IOException e)
		{
			e.printStackTrace();
		}
	}
}
