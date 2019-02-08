package com.learningjava.thread;

class ThreadExample extends Thread {
	public void run() {
		System.out.println("Inside: " + Thread.currentThread().getName());
	}
}


public class ThreadDemo1 {
	public static void main(String[] args) {
		System.out.println("Inside: " + Thread.currentThread().getName());
		System.out.println("Creating thread...");
		Thread thread = new ThreadExample();
		
		System.out.println("Staring thread...");
		thread.start();
				
	}
}