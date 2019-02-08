package com.learningjava.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface ServiceOne {
	String sayHello();
}

class ServiceOneBean implements ServiceOne {

	@Override
	public String sayHello() {
		System.out.println("Executing method sayHello.");
		return "Hello";
	}
}

class LogExecutionTimeProxy implements InvocationHandler
{
	private Object invocationTarget;
	
	public LogExecutionTimeProxy(Object invocationTarget) {
		this.invocationTarget = invocationTarget;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		long startTime = System.nanoTime();
		Object result = method.invoke(invocationTarget, args);
		
		System.out.println("Executed method " + method.getName() 
			+ " in " + (System.nanoTime() - startTime) + " nanoseconds");
		
		return result;
	}
}



public class JdkDynamicProxyDemo {
	
	public static void main(String[] args) {
		
		ServiceOne serviceOne = new ServiceOneBean();
		
		// 创建代理 需要强制类型转换
		ServiceOne proxy = (ServiceOne) Proxy.newProxyInstance(ServiceOne.class.getClassLoader(), 
				new Class[] { ServiceOne.class }, new LogExecutionTimeProxy(serviceOne));
		
		// 触发方法
		String result = proxy.sayHello();
		
		System.out.println("Result:" + result);
	}
}



