package com.my.rpc.core.IDLFileGeneratorBase;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import com.google.googlejavaformat.java.FormatterException;

public abstract class RPCMessageFileBuilderBase
{
	protected StringBuilder msgBodyBuilder;
	protected StringBuilder packageStatBuilder;
	protected StringBuilder outerClassBuilder;
	protected String outerClassName;
	protected String packageName;
	private String inputFilePath;
	protected Set<String> importStatments;
	
	public RPCMessageFileBuilderBase(String inputFilePath)
	{
		this.inputFilePath = inputFilePath;
		msgBodyBuilder = new StringBuilder();
		packageStatBuilder = new StringBuilder();
		outerClassBuilder = new StringBuilder();
		importStatments = new HashSet<>();
		addBuildInImport();
	}

	/**
	 * @return the inputFileName
	 */
	public String getInputFileName()
	{
		return inputFilePath;
	}

	/**
	 * @param inputFileName the inputFileName to set
	 */
	public void setInputFilePath(String inputFileName)
	{
		this.inputFilePath = inputFileName;
	}
	
	public String getDescriptionText()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("/***\n");
		builder.append("\tThis file is generated from " + this.inputFilePath+" by RPC message translator.\nRecompile the source file if modification is needed.\nDo not manually modify this file.\n");
		builder.append("The RPC Message Translator is a MIT License software authored by Wangdingqiao(393524628@qq.com).");
		builder.append("*/\n\n");
		return builder.toString();
	}
	public void addBuildInImport()
	{
		
	}
	public abstract void endMessage(IFileImportManager importHelper, String outputPath) throws FileNotFoundException, FormatterException;
	public abstract void setOuterClassName(String className);
	public abstract String getPackageLocation();
	public abstract void addImportStat(String text);
	public abstract void addPackageStat(String text);
	public abstract void addMessage(String name, java.util.ArrayList<Pair<String, String>> fields);
	public abstract void addEnum(String name,  java.util.ArrayList<Pair<String, Integer>> fields);
}
