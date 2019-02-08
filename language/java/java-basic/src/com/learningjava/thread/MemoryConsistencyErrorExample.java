package com.learningjava.thread;

public class MemoryConsistencyErrorExample {
	
	private static boolean sayHello = false;
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Thread thread = new Thread(() -> {
	           while(!sayHello) {                   // 由于内存不一致 导致的错误判断 Memory Consistency Errors
	           }

	           System.out.println("Hello World!");

	           while(sayHello) {
	           }

	           System.out.println("Good Bye!");
	        });

	        thread.start();

	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        System.out.println("Say Hello..");
	        sayHello = true;

	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        System.out.println("Say Bye..");
	        sayHello = false;
	}

}
