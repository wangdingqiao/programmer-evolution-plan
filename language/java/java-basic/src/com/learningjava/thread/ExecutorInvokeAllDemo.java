package com.learningjava.thread;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorInvokeAllDemo {

	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(5);

        Callable<String> task1 = () -> {
            Thread.sleep(2000);
            return "Result of Task1";
        };

        Callable<String> task2 = () -> {
            Thread.sleep(1000);
            return "Result of Task2";
        };

        Callable<String> task3 = () -> {
            Thread.sleep(5000);
            return "Result of Task3";
        };

        List<Callable<String>> taskList = Arrays.asList(task1, task2, task3);

		try {
			List<Future<String>> futures = executorService.invokeAll(taskList);
			
			 for(Future<String> future: futures) {
		         // The result is printed only after all the futures are complete. (i.e. after 5 seconds)
		         System.out.println(future.get()); // 调用任意一个future.get 将阻塞直到所有任务都完成为止
		     }
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

        executorService.shutdown();
	}

}
