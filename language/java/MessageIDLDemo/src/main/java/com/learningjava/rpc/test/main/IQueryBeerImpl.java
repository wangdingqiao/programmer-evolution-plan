package com.learningjava.rpc.test.main;

import com.my.rpc.runtime.builtInMessage.RPCCommonMessage.MapStrInt;
import com.my.rpc.runtime.builtInMessage.RPCCommonMessage.SeqInt;
import com.my.rpc.runtime.builtInMessage.RPCCommonMessage.SeqString;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCBinary;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCFloat;
import com.my.rpc.runtime.builtInMessage.RPCPrimitiveType.RPCString;
import com.my.rpc.runtime.exception.RPCExceptionBase;
import com.my.rpc.test.message.IBeerQueryServerStub;
import com.my.rpc.test.message.MessageBeerArray.BeerArray;

public class IQueryBeerImpl extends IBeerQueryServerStub
{

	@Override
	public BeerArray queryBeers(RPCFloat param1, RPCString param2, SeqString param3, MapStrInt param4, RPCBinary param5,
			SeqInt param6) throws RPCExceptionBase
	{
		
		return null;
	}
}
