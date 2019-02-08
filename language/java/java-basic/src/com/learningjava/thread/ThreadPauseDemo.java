package com.learningjava.thread;

public class ThreadPauseDemo {

	public static void main(String[] args) {
		System.out.println("Inside : " + Thread.currentThread().getName());

        String[] messages = {"If I can stop one heart from breaking,",
                "I shall not live in vain.",
                "If I can ease one life the aching,",
                "Or cool one pain,",
                "Or help one fainting robin",
                "Unto his nest again,",
                "I shall not live in vain"};

        Runnable runnable = () -> {
            System.out.println("Inside : " + Thread.currentThread().getName());

            for(String message: messages) {
                System.out.println(message);
                try {
                    Thread.sleep(2000);    // 等待一段时间  sleep() method throws InterruptedException
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
        };

        Thread thread = new Thread(runnable);

        thread.start();

	}

}
