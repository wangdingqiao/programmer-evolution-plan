package com.learningjava.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SynchronizedCounter {
    private int count = 0;

    // Synchronized Method 
    public synchronized void increment() {
        count = count + 1;
    }

    public int getCount() {
        return count;
    }
}


public class SynchronizedMethodExample {

	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);

        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();

        for(int i = 0; i < 1000; i++) {
            executorService.submit(() -> synchronizedCounter.increment());
        }

        executorService.shutdown();
        try {
			executorService.awaitTermination(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("Final count is : " + synchronizedCounter.getCount());

	}

}
