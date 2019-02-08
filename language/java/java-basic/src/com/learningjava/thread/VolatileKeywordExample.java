package com.learningjava.thread;



public class VolatileKeywordExample {
	
	private static volatile boolean sayHello = false; // volatile关键提示编译器停止优化 总是从内存读取数据
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread thread = new Thread(() -> {
           while(!sayHello) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Say Hello..");
        sayHello = true;

        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Say Bye..");
        sayHello = false;
	}

}
