package com.learningjava.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Counter {
    int count = 0;

    public void increment() {
        count = count + 1;  // Critical Section
    }

    public int getCount() {
        return count;
    }
}

public class RaceConditionExample {

	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);

        Counter counter = new Counter();

        for(int i = 0; i < 1000; i++) {
            executorService.submit(() -> counter.increment());   // 由于Race condition引起结果的不确定 不一定等于1000
        }

        executorService.shutdown();
        try {
			executorService.awaitTermination(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    
        System.out.println("Final count is : " + counter.getCount());
	}

}
