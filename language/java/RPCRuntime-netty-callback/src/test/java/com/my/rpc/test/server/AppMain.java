package com.my.rpc.test.server;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import com.my.rpc.runtime.runner.RPCServer;

public class AppMain
{
   public static void main(String[] args)
   {
	   LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
	   File file = new File("log4j2.xml");
	   // this will force a reconfiguration
	   context.setConfigLocation(file.toURI());
	   RPCServer server = new RPCServer(2048);
	   if(server.registStub(new IQueryBeerImpl()))
	   {
		   server.run();
	   }
   }
}
