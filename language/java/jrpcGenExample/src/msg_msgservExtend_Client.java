/*
 * Automatically generated by jrpcgen 1.0.7 on 19-1-6 ����10:33
 * jrpcgen is part of the "Remote Tea" ONC/RPC package for Java
 * See http://remotetea.sourceforge.net for details
 */
import org.acplt.oncrpc.*;
import java.io.IOException;

import java.net.InetAddress;

/**
 * The class <code>msg_msgservExtend_Client</code> implements the client stub proxy
 * for the msgservExtend remote program. It provides method stubs
 * which, when called, in turn call the appropriate remote method (procedure).
 */
public class msg_msgservExtend_Client extends OncRpcClientStub {

    /**
     * Constructs a <code>msg_msgservExtend_Client</code> client stub proxy object
     * from which the msgservExtend remote program can be accessed.
     * @param host Internet address of host where to contact the remote program.
     * @param protocol {@link org.acplt.oncrpc.OncRpcProtocols Protocol} to be
     *   used for ONC/RPC calls.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public msg_msgservExtend_Client(InetAddress host, int protocol)
           throws OncRpcException, IOException {
        super(host, msg.msgservExtend, 2, 0, protocol);
    }

    /**
     * Constructs a <code>msg_msgservExtend_Client</code> client stub proxy object
     * from which the msgservExtend remote program can be accessed.
     * @param host Internet address of host where to contact the remote program.
     * @param port Port number at host where the remote program can be reached.
     * @param protocol {@link org.acplt.oncrpc.OncRpcProtocols Protocol} to be
     *   used for ONC/RPC calls.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public msg_msgservExtend_Client(InetAddress host, int port, int protocol)
           throws OncRpcException, IOException {
        super(host, msg.msgservExtend, 2, port, protocol);
    }

    /**
     * Constructs a <code>msg_msgservExtend_Client</code> client stub proxy object
     * from which the msgservExtend remote program can be accessed.
     * @param client ONC/RPC client connection object implementing a particular
     *   protocol.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public msg_msgservExtend_Client(OncRpcClient client)
           throws OncRpcException, IOException {
        super(client);
    }

    /**
     * Constructs a <code>msg_msgservExtend_Client</code> client stub proxy object
     * from which the msgservExtend remote program can be accessed.
     * @param host Internet address of host where to contact the remote program.
     * @param program Remote program number.
     * @param version Remote program version number.
     * @param protocol {@link org.acplt.oncrpc.OncRpcProtocols Protocol} to be
     *   used for ONC/RPC calls.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public msg_msgservExtend_Client(InetAddress host, int program, int version, int protocol)
           throws OncRpcException, IOException {
        super(host, program, version, 0, protocol);
    }

    /**
     * Constructs a <code>msg_msgservExtend_Client</code> client stub proxy object
     * from which the msgservExtend remote program can be accessed.
     * @param host Internet address of host where to contact the remote program.
     * @param program Remote program number.
     * @param version Remote program version number.
     * @param port Port number at host where the remote program can be reached.
     * @param protocol {@link org.acplt.oncrpc.OncRpcProtocols Protocol} to be
     *   used for ONC/RPC calls.
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public msg_msgservExtend_Client(InetAddress host, int program, int version, int port, int protocol)
           throws OncRpcException, IOException {
        super(host, program, version, port, protocol);
    }

    /**
     * Call remote procedure sendmsg_2.
     * @param arg1 parameter (of type String) to the remote procedure call.
     * @return Result from remote procedure call (of type String).
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public String sendmsg_2(String arg1)
           throws OncRpcException, IOException {
        XdrString args$ = new XdrString(arg1);
        XdrString result$ = new XdrString();
        client.call(msg.sendmsg_2, msg.MSGSERV_V2, args$, result$);
        return result$.stringValue();
    }

    /**
     * Call remote procedure sendmsgEx_2.
     * @param arg1 parameter (of type String) to the remote procedure call.
     * @return Result from remote procedure call (of type String).
     * @throws OncRpcException if an ONC/RPC error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public String sendmsgEx_2(String arg1)
           throws OncRpcException, IOException {
        XdrString args$ = new XdrString(arg1);
        XdrString result$ = new XdrString();
        client.call(msg.sendmsgEx_2, msg.MSGSERV_V2, args$, result$);
        return result$.stringValue();
    }

}
// End of msg_msgservExtend_Client.java