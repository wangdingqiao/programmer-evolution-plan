package com.my.rpc.runtime.builtInMessage;

import java.io.File;
import com.my.rpc.core.IDLFileGenerator.java.RPCMessageTranslator;
import com.my.rpc.runtime.exception.RPCExceptionBase;

public class RPCIDLTranslator
{
	public static void main(String[] args) throws RPCExceptionBase
	{
		String outputPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java";
		new RPCMessageTranslator("src/main/java/rpcCommon.idl").generateFile(outputPath);
	}
}
