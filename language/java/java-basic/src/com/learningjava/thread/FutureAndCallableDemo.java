package com.learningjava.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureAndCallableDemo {

	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> {
            // Perform some computation
            System.out.println("Entered Callable");
            Thread.sleep(2000);
            return "Hello from Callable";
        };

        System.out.println("Submitting Callable");
        Future<String> future = executorService.submit(callable);

        // This line executes immediately
        System.out.println("Do something else while callable is getting executed");

        System.out.println("Retrieve the result of the future");
        // Future.get() blocks until the result is available
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
