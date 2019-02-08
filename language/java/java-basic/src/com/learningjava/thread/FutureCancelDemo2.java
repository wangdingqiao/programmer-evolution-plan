package com.learningjava.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureCancelDemo2 {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();

        long startTime = System.nanoTime();
        Future<String> future = executorService.submit(() -> {
            Thread.sleep(2000);
            return "Hello from Callable";
        });

        while(!future.isDone()) {
            System.out.println("Task is still not done...");
            try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            double elapsedTimeInSec = (System.nanoTime() - startTime)/1000000000.0;

            if(elapsedTimeInSec > 1) {
                future.cancel(true);
            }
        }

        System.out.println("Task completed! Retrieving the result");
		try {
			if(future.isCancelled()) {
				System.out.println("Task had been cancelled.");
			}
			else {
				System.out.println(future.get());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        executorService.shutdown();
	}
}
