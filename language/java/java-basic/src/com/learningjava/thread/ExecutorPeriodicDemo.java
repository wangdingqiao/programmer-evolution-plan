package com.learningjava.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorPeriodicDemo {
	public static void main(String[] args) {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
          System.out.println("Executing Task At " + System.nanoTime());
        };
        
        System.out.println("scheduling task to be executed every 2 seconds with an initial delay of 0 seconds");
        scheduledExecutorService.scheduleAtFixedRate(task, 0,2, TimeUnit.SECONDS);  // 周期性的执行
	}
}
