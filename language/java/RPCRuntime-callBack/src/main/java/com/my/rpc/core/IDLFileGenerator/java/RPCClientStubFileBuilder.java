package com.my.rpc.core.IDLFileGenerator.java;

import java.util.ArrayList;
import org.apache.commons.lang3.tuple.Pair;

import com.my.rpc.core.IDLFileGeneratorBase.RPCClientStubFileBuilderBase;

public class RPCClientStubFileBuilder extends RPCClientStubFileBuilderBase
{
	private ArrayList<Pair<Integer, String>> methods;
	private StringBuilder packageStatBuilder;

	public RPCClientStubFileBuilder()
	{
		methods = new ArrayList<Pair<Integer, String>>();
		packageStatBuilder = new StringBuilder();
		addBuildInImport();
	}
	
	private void addBuildInImport()
	{
		addImportStat("import com.my.rpc.runtime.exception.RPCExceptionBase;");
		addImportStat("import com.my.rpc.runtime.runner.RPCClientStub;");
		addImportStat("import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.*;");
		addImportStat("import com.my.rpc.runtime.builtInMessage.RPCCommonMessage;");
		addImportStat("import java.net.InetAddress;");
		addImportStat("import com.my.rpc.runtime.protocol.RPCRequest;");
		addImportStat("import com.my.rpc.runtime.protocol.RPCResponse;");
		addImportStat("import com.my.rpc.runtime.serializer.ISerializer;");
		addImportStat("import com.my.rpc.runtime.serializer.SerializeFactory;");
		addImportStat("import com.my.rpc.runtime.runner.RPCCallBackBase;");
	}
	
	public void addMethod(String name, String methodIndex, java.util.ArrayList<Pair<String, String>> inputParams, String responseType)
	{
		methods.add(Pair.of(Integer.valueOf(methodIndex), SerializePartBuilder.getSerMethodText(this.getInterfaceName(), name, methodIndex, responseType, inputParams)));
	}
	
	public String getText()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(packageStatBuilder.toString());
		for(String importText: this.getImportStatments())
		{
			if(!importText.isEmpty())
			{
				builder.append(importText + "\n");
			}
		}
		builder.append("public class " + this.getInterfaceName() + "ClientStub extends RPCClientStub implements " + this.getInterfaceName() + "C\n{\n");
		builder.append("public " + this.getInterfaceName() +"ClientStub(InetAddress host, int port)\n{");
		builder.append("super(" + this.getInterfaceName()+ ".class, host, port);\n}\n");
		for(Pair<Integer, String> pair: methods)
		{
			builder.append(pair.getValue());
		}
		builder.append("\n}");
		return builder.toString();
	}
	
	public void addPackageStat(String text)
	{
		packageStatBuilder.append("package " +text + ";\n");
	};
}
