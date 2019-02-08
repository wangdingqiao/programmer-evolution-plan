package com.learningjava.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// Atomic classes internally use compare-and-swap 
// instructions supported by modern CPUs to achieve synchronization.
class AtomicCounter {
    private AtomicInteger count = new AtomicInteger(0);

    public int incrementAndGet() {
        return count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}

public class AtomicIntegerExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService executorService = Executors.newFixedThreadPool(2);

        AtomicCounter atomicCounter = new AtomicCounter();

        for(int i = 0; i < 1000; i++) {
            executorService.submit(() -> atomicCounter.incrementAndGet());
        }

        executorService.shutdown();
        try {
			executorService.awaitTermination(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        System.out.println("Final Count is : " + atomicCounter.getCount());
	}

}
