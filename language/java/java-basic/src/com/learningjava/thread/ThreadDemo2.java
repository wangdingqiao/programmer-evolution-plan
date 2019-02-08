package com.learningjava.thread;

public class ThreadDemo2 implements Runnable {

    public static void main(String[] args) {
        System.out.println("Inside : " + Thread.currentThread().getName());

        System.out.println("Creating Runnable...");
        Runnable runnable = new ThreadDemo2();

        System.out.println("Creating Thread...");
        Thread thread = new Thread(runnable);  // Thread(Runnable target) 

        System.out.println("Starting Thread...");
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Inside : " + Thread.currentThread().getName());
    }
}
