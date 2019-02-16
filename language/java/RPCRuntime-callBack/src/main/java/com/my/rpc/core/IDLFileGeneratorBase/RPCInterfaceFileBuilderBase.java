package com.my.rpc.core.IDLFileGeneratorBase;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import com.google.googlejavaformat.java.FormatterException;
import com.my.rpc.core.IDLFileGenerator.java.RPCClientStubFileBuilder;
import com.my.rpc.core.IDLFileGenerator.java.RPCServerStubFileBuilder;

public abstract class RPCInterfaceFileBuilderBase
{
	protected StringBuilder packageStatBuilder;
	protected StringBuilder interfaceServerBuilder;
	protected StringBuilder interfaceClientBuilder;
	protected String packageName;
	protected String interfaceName;
	protected String inputFilePath;
	protected Set<String> importStatments;
	protected RPCServerStubFileBuilderBase serverStubBuilder;
	protected RPCClientStubFileBuilderBase clientStubBuilder;
	
	public RPCInterfaceFileBuilderBase(String inputFilePath, RPCServerStubFileBuilder serverStub,
			RPCClientStubFileBuilder clientStub)
	{
		this.inputFilePath = inputFilePath;
		packageStatBuilder = new StringBuilder();
		interfaceServerBuilder = new StringBuilder();
		interfaceClientBuilder = new StringBuilder();
		importStatments = new HashSet<>();
		serverStubBuilder = serverStub;
		clientStubBuilder = clientStub;
		addBuildInImport();
	}
	
	/**
	 * @return the inputFilePath
	 */
	public String getInputFilePath()
	{
		return inputFilePath;
	}

	/**
	 * @param inputFilePath the inputFilePath to set
	 */
	public void setInputFilePath(String inputFilePath)
	{
		this.inputFilePath = inputFilePath;
	}



	public abstract void addBuildInImport();
	public String getDescriptionText()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("/***\n");
		builder.append("\tThis file is generated from " + this.inputFilePath+" by RPC message translator.\nRecompile the source file if modification is needed.\nDo not manually modify this file.\n");
		builder.append("The RPC Message Translator is a MIT License software authored by Wangdingqiao(393524628@qq.com).");
		builder.append("*/\n\n");
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
	public abstract void endInterface(IFileImportManager importHelper, String outputPath) throws FileNotFoundException, FormatterException;
	public abstract String getText(boolean client);
	public abstract void addPackageStat(String text);
	public abstract void addMethod(String name, String methodIndex, java.util.ArrayList<Pair<String, String>> inputParams, String responseType);
}
