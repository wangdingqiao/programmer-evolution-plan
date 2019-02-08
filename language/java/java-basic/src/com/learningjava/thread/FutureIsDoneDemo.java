package com.learningjava.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureIsDoneDemo {

	public static void main(String[] args) {
		 ExecutorService executorService = Executors.newSingleThreadExecutor();

	        Future<String> future = executorService.submit(() -> {
	            Thread.sleep(2000);
	            return "Hello from Callable";
	        });

	        while(!future.isDone()) {
	            System.out.println("Task is still not done...");
	            try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }

	        System.out.println("Task completed! Retrieving the result");
	        String result = null;
			try {
				result = future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
	        System.out.println(result);

	        executorService.shutdown();

	}

}
