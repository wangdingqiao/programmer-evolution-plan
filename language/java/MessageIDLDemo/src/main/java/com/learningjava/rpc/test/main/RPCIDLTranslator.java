package com.learningjava.rpc.test.main;

import java.io.File;
import java.io.IOException;

import com.google.googlejavaformat.java.FormatterException;
import com.my.rpc.core.IDLFileGenerator.java.RPCMessageTranslator;

public class RPCIDLTranslator
{
	public static void main(String[] args) throws IOException, FormatterException
	{
//		new RPCMessageTranslator("example.idl").showASTTree();
		String outputPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java";
		new RPCMessageTranslator("rpcCommon.idl").generateFile(outputPath);
//		new RPCMessageTranslator("example.idl").generateFile(outputPath);
	}
}
