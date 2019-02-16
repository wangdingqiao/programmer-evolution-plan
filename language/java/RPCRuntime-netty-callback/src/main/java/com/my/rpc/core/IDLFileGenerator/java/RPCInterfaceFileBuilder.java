package com.my.rpc.core.IDLFileGenerator.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import org.apache.commons.lang3.tuple.Pair;
import com.google.googlejavaformat.java.FormatterException;
import com.my.rpc.core.IDLFileGeneratorBase.IFileImportManager;
import com.my.rpc.core.IDLFileGeneratorBase.RPCInterfaceFileBuilderBase;

public class RPCInterfaceFileBuilder extends RPCInterfaceFileBuilderBase
{
	
	public RPCInterfaceFileBuilder(String inputFilePath)
	{
		super(inputFilePath, new RPCServerStubFileBuilder(),new RPCClientStubFileBuilder());
		addBuildInImport();
	}
	
	public void addBuildInImport()
	{
		importStatments.add("import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.*;");
		importStatments.add("import com.my.rpc.runtime.builtInMessage.RPCCommonMessage;");
		importStatments.add("import com.my.rpc.runtime.exception.RPCExceptionBase;");
		importStatments.add("import com.my.rpc.runtime.runner.RPCCallBackBase;");
	}
	
	public String getDescriptionText()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("/***\n");
		builder.append("\tThis file is generated from " + this.getInputFilePath()+" by RPC message translator.\nRecompile the source file if modification is needed.\nDo not manually modify this file.\n");
		builder.append("The RPC Message Translator is a MIT License software authored by Wangdingqiao(393524628@qq.com).");
		builder.append("*/\n\n");
		return builder.toString();
	}
	
	public void endInterface(IFileImportManager importHelper, String outputPath) throws FileNotFoundException, FormatterException
	{
		if(this.interfaceName == null || this.interfaceName.isEmpty())
		{
			return;
		}
		String filePath = packageName;
		filePath = outputPath + File.separator  +filePath.replaceAll("\\.", Matcher.quoteReplacement(File.separator));
		File directory = new File(filePath);
		if (! directory.exists())
		{
			directory.mkdir();
		}
		HashMap<String, String> filePath2Source = new HashMap<>();
		String interfaceServerFilePath = filePath + File.separator + interfaceName+"S.java";
		filePath2Source.put(interfaceServerFilePath, this.getText(false));
		String interfaceClientFilePath = filePath + File.separator + interfaceName+"C.java";
		filePath2Source.put(interfaceClientFilePath, this.getText(true));
		String serverStubFilePath = filePath + File.separator + interfaceName+"ServerStub.java";
		filePath2Source.put(serverStubFilePath, serverStubBuilder.getText());
		String clientStubFilePath = filePath + File.separator + interfaceName+"ClientStub.java";
		filePath2Source.put(clientStubFilePath, clientStubBuilder.getText());
		for(Map.Entry<String, String> entry: filePath2Source.entrySet())
		{
			try
			{
				String formattedSource = new com.google.googlejavaformat.java.Formatter().formatSource(entry.getValue());
				PrintWriter fromatedPW = new PrintWriter(new FileOutputStream(new File(entry.getKey())));
				fromatedPW.write(formattedSource);
				fromatedPW.close();
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println(entry.getValue());
			}
			System.out.println("RPCInterfaceFileBuilder  finish filePath= " + entry.getKey());
		}
	}
	
	public String getText(boolean client)
	{
		if(this.interfaceName == null || this.interfaceName.isEmpty())
		{
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append(this.getDescriptionText());
		builder.append(packageStatBuilder.toString());
		for(String importText: this.importStatments)
		{
			if(!importText.isEmpty())
			{
				builder.append(importText + "\n");
			}
		}
		if(client)
		{
			builder.append("public interface " + interfaceName + "C\n{\n");
			builder.append(interfaceClientBuilder.toString());
		}
		else
		{
			builder.append("public interface " + interfaceName + "S\n{\n");
			builder.append(interfaceServerBuilder.toString());
		}
		
		builder.append("}\n");
		return builder.toString();
	}
	
	public void setInterfaceName(String interfaceName)
	{
		this.interfaceName = interfaceName;
		serverStubBuilder.setInterfaceName(interfaceName);
		clientStubBuilder.setInterfaceName(interfaceName);
	}
	
	public void addImportStat(String text)
	{
		if(text != null)
		{
			importStatments.add(text);
			serverStubBuilder.addImportStat(text);
			clientStubBuilder.addImportStat(text);
		}
	};
	
	public void addPackageStat(String text)
	{
		packageStatBuilder.append("package " +text + ";\n");
		packageName = text;
		serverStubBuilder.addPackageStat(text);
		clientStubBuilder.addPackageStat(text);
	};
	
	public void addMethod(String name, String methodIndex, java.util.ArrayList<Pair<String, String>> inputParams, String responseType)
	{
		responseType = TypeMapper.instance().getMappedType(responseType);
		interfaceServerBuilder.append("  public " + responseType + " " +name + "(");
		int inParamSize = inputParams.size();
		int index = 0;
		for(Pair<String, String> entry: inputParams)
		{
			index++;
			String typeStr = TypeMapper.instance().getMappedType(entry.getKey());
			interfaceServerBuilder.append("" + typeStr + " " + entry.getValue() + (index != inParamSize ? "," : ""));
		}
		interfaceServerBuilder.append(") throws RPCExceptionBase ;\n");
		
		interfaceClientBuilder.append("  public " + responseType + " " +name + "(");
		for(Pair<String, String> entry: inputParams)
		{
			index++;
			String typeStr = TypeMapper.instance().getMappedType(entry.getKey());
			interfaceClientBuilder.append("" + typeStr + " " + entry.getValue() + ",");
		}
		interfaceClientBuilder.append(" RPCCallBackBase callBack");
		interfaceClientBuilder.append(") throws RPCExceptionBase ;\n");
		
		serverStubBuilder.addMethod(name, methodIndex, inputParams, responseType);
		clientStubBuilder.addMethod(name, methodIndex, inputParams, responseType);
	}
}
