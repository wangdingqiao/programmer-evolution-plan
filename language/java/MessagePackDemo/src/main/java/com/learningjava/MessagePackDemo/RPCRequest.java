package com.learningjava.MessagePackDemo;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;

public class RPCRequest
{
	public long requestId;
	public Class<?> clazz;
	public String method;
	public Type[] parameterTypes;
	public Object[] params;
	public long requestTime;
	@Override
	public String toString()
	{
		return "RPCRequest [requestId=" + requestId + ", clazz=" + clazz + ", method=" + method + ", parameterTypes="
				+ Arrays.toString(parameterTypes) + ", params=" + paramToString(params) + ", requestTime="
				+ requestTime + "]";
	}
	
	
	private String paramToString(Object[] params)
	{
		StringBuilder buidler = new StringBuilder();
		for(Object object: params)
		{
			if(object.getClass().isArray())
			{
			  Object[] array2 = new Object[Array.getLength(object)];
			    for(int i=0;i<array2.length;i++)
			    {
			    	array2[i] = Array.get(object, i);
			    }
				buidler.append(Arrays.toString(array2));
			}
			else
			{
				buidler.append(object.toString());
			}
		}
		return buidler.toString();
	}
}
