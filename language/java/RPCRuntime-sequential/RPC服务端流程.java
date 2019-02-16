public class RPCServer
{
	public static void main(String[] args) throws Exception
	{
		HelloService service = new HelloServiceImpl();
		RpcFramework.export(service, 1234);
	}
}

public class RpcFramework 
{
	 /*
	 * 导出服务接口
	 */
	 public static void export(final Object service, int port) throws Exception 
	 {
		// Step1: 建立socket 等待连接
		ServerSocket serverSocket = new ServerSocket(port);
		// Step2: 接收客户端连接
		final Socket socket = serverSocket.accept();
		// Step3: 读取客户端输入的方法名字，参数类型，参数值等信息
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream()); // an input stream for reading bytes from this socket.
		String methodName = input.readUTF();
		Class<?>[] paramTypes = (Class<?>[])input.readObject();
		Object[] arguments = (Object[])input.readObject();
		// Step4: 使用反射机制 找到对应方法
		Method method = service.getClass().getMethod(methodName, paramTypes);
		// Step5: 触发具体的方法
		Object result = method.invoke(service, arguments);
		// Step6: 写回方法执行后的返回值
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  // an output stream for writing bytes to this socket.
		output.writeObject(result);
	}
}