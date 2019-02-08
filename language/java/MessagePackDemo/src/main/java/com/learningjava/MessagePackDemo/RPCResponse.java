package com.learningjava.MessagePackDemo;

import java.lang.reflect.Type;

public class RPCResponse
{
	public long requestId;
	public Type responseParamType;
	public Object response;
	@Override
	public String toString()
	{
		return "RPCResponse [requestId=" + requestId + ", responseParamType=" + responseParamType + ", response="
				+ response + "]";
	}
}
