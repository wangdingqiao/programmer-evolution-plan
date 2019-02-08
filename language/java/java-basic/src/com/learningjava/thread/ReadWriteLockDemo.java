package com.learningjava.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReadWriteCounter {
    ReadWriteLock lock = new ReentrantReadWriteLock();

    private int count = 0;

    public int incrementAndGetCount() {
        lock.writeLock().lock();
        try {
            count = count + 1;
            return count;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getCount() {
        lock.readLock().lock();
        try {
            return count;
        } finally {
            lock.readLock().unlock();
        }
    }
}


public class ReadWriteLockDemo {

	public static void main(String[] args) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(5);

		ReadWriteCounter counter = new ReadWriteCounter();

        executorService.submit(() -> {
           System.out.println(Thread.currentThread().getName() + " " + 
        		   counter.incrementAndGetCount() + "\n");
        });
        
        Runnable runnable = () -> {
        	 System.out.println(Thread.currentThread().getName() + " " + 
          		   counter.getCount() + "\n");
        };
        
        executorService.submit(runnable);

        executorService.shutdown();
	}

}
