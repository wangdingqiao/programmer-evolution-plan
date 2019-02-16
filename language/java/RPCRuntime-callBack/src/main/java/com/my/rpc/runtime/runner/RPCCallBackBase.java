package com.my.rpc.runtime.runner;

import java.util.concurrent.CountDownLatch;
import com.my.rpc.runtime.protocol.IRPCMessage;

public abstract class RPCCallBackBase
{
	private IRPCMessage message;
	private CountDownLatch countDown;
	public RPCCallBackBase(IRPCMessage message, CountDownLatch countDown)
	{
		this.message = message;
		this.setCountDown(countDown);
	}
	/**
	 * @return the message
	 */
	public IRPCMessage getMessage()
	{
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(IRPCMessage message)
	{
		this.message = message;
	}
	public abstract void onSuccess();
	public abstract void onFailed(Throwable exc);
	public CountDownLatch getCountDown()
	{
		return countDown;
	}
	public void setCountDown(CountDownLatch countDown)
	{
		this.countDown = countDown;
	}
}
