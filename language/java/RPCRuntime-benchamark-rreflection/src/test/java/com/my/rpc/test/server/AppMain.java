package com.my.rpc.test.server;

import com.my.rpc.runtime.runner.RPCServer;
import com.my.rpc.testMessage.IBeerQuery;

public class AppMain
{
   public static void main(String[] args)
   {
	   RPCServer server = new RPCServer(2048);
	   server.export(IBeerQuery.class, new IQueryBeerImpl());
	   server.run();
   }
}
