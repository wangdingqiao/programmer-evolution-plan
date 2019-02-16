package com.my.rpc.core.IDLFileGenerator.java;

import java.util.ArrayList;
import org.apache.commons.lang3.tuple.Pair;
import com.my.rpc.core.IDLFileGeneratorBase.RPCServerStubFileBuilderBase;

public class RPCServerStubFileBuilder extends RPCServerStubFileBuilderBase
{
	private ArrayList<Pair<Integer, String>> methods;
	private StringBuilder packageStatBuilder;

	RPCServerStubFileBuilder()
	{
		methods = new ArrayList<Pair<Integer, String>>();
		packageStatBuilder = new StringBuilder();
		addBuildInImport();
	}
	
	private void addBuildInImport()
	{
		addImportStat("import com.my.rpc.runtime.exception.RPCExceptionBase;");
		addImportStat("import com.my.rpc.runtime.serializer.IDeserializer;");
		addImportStat("import com.my.rpc.runtime.runner.RPCServerStub;");
		addImportStat("import com.my.rpc.runtime.protocol.RPCCallInformation;");
		addImportStat("import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.*;");
		addImportStat("import com.my.rpc.runtime.builtInMessage.RPCCommonMessage;");
		addImportStat("import com.my.rpc.runtime.exception.RPCExceptionFactory;");
		addImportStat("import com.my.rpc.runtime.protocol.RPCResponse;");
	}
	
	public void addMethod(String name, String methodIndex, java.util.ArrayList<Pair<String, String>> inputParams, String responseType)
	{
		
		methods.add(Pair.of(Integer.valueOf(methodIndex), DeserializePartBuilder.getDeserMethodText(name, responseType, inputParams)));
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
		builder.append("public abstract class " + this.getInterfaceName() + "ServerStub extends RPCServerStub implements " + this.getInterfaceName() + "\n{\n");
		builder.append("public " + this.getInterfaceName() +"ServerStub()\n{");
		builder.append("super(" + this.getInterfaceName() + ".class);\n}\n");
		builder.append("public RPCResponse dispatchCall(RPCCallInformation callInfo) throws RPCExceptionBase\r\n" + 
				"	{\r\n" + 
				"		IDeserializer deser = callInfo.getDeserializer();\r\n" + 
				"		int methodIndexer = callInfo.getMethodId();\r\n" + 
				"		switch(methodIndexer)\r\n" + 
				"			{");
		for(Pair<Integer, String> pair: methods)
		{
			builder.append(" case " + pair.getKey() + ":\n{");
			builder.append(pair.getValue()+"\n}");
		}
		builder.append("default:\n throw RPCExceptionFactory.createException(\r\n" + 
				"						RPCExceptionFactory.RPC_MethodNotFound_Exception, \r\n" + 
				"						\" method not found.\");\n}\n}");
		builder.append("\n}");
		return builder.toString();
	}
	
	public void addPackageStat(String text)
	{
		packageStatBuilder.append("package " +text + ";\n");
	};
}
