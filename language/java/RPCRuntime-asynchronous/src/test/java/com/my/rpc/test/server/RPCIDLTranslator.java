package com.my.rpc.test.server;

import java.io.File;
import java.io.IOException;

import com.my.rpc.core.IDLFileGenerator.java.RPCMessageTranslator;
import com.my.rpc.runtime.exception.RPCExceptionBase;

public class RPCIDLTranslator
{
	public static void main(String[] args) throws RPCExceptionBase, IOException
	{
//		new RPCMessageTranslator("src/test/example.idl").showASTTree();
		String outputPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java";
		new RPCMessageTranslator("src/test/example.idl").generateFile(outputPath);
	}
}
