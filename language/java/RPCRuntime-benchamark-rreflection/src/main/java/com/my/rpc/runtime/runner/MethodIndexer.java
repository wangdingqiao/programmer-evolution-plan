package com.my.rpc.runtime.runner;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class MethodIndexer
{
	private Map<String, Map<Integer, Method>> index2Method;
	private Map<String, Map<String, Integer>> name2Index; // This map only for testing, in real word case will not work because same name method.
	private Map<String, Object> objectMap;
	private static MethodIndexer instance = null;
	
	private MethodIndexer()
	{
		index2Method = new HashMap<>();
		name2Index = new HashMap<>();
		objectMap = new HashMap<>();
	}
	
	public Method getMethod(Class<?> interfaceCls, int methodIndex)
	{
		String interfaceName = interfaceCls.getName();
		if(index2Method.containsKey(interfaceName))
		{
			return index2Method.get(interfaceName).get(methodIndex);
		}
		return null;
	}
	
	public int getMethodIndex(Class<?> interfaceCls, String methodName)
	{
		String interfaceName = interfaceCls.getName();
		if(name2Index.containsKey(interfaceName))
		{
			return name2Index.get(interfaceName).get(methodName);
		}
		return -1;
	}
	
	public void buildMethodIndex(Class<?> interfaceCls)
	{
		Method[] methods = interfaceCls.getDeclaredMethods();
		Arrays.sort(methods, new Comparator<Method>() {
	        @Override
	        public int compare(Method a, Method b) {
	            return a.getName().compareTo(b.getName());
	        }
	    });
		Map<Integer, Method> methodIndexer = new HashMap<>();
		Map<String, Integer> name2Method = new HashMap<>();
		for(int index =0; index < methods.length;++index)
		{
			methodIndexer.put(index, methods[index]);
			name2Method.put(methods[index].getName(), index);
		}
		index2Method.put(interfaceCls.getName(), methodIndexer);
		name2Index.put(interfaceCls.getName(), name2Method);
	}
	
	public void addObject(Class<?> interfaceCls, Object obj)
	{
		this.objectMap.put(interfaceCls.getName(), obj);
		this.buildMethodIndex(interfaceCls);
	}
	
	public Object getObject(Class<?> interfaceCls)
	{
		return this.objectMap.get(interfaceCls.getName());
	}
	
	public static MethodIndexer getInstance()
	{
		if(instance == null)
		{
			instance = new MethodIndexer();
		}
		return instance;
	}
}
