package com.amarpreet.thread;

import java.util.concurrent.TimeUnit;

public class OldClassThread {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() ->
                System.out.println("I am in New Thread classs ::\t"
                        + Thread.currentThread().getName()));
        t1.setName("Thread-T1");
        System.out.println(ANSI_RED + "Before the Thread start, program is current in " + Thread.currentThread().getName() + ANSI_RESET);

        System.out.println("After the Thread end, program is current in " + Thread.currentThread().getName());
        Thread.sleep(TimeUnit.SECONDS.toMillis(10));

        Thread t2 = new Thread(() -> {
            throw new RuntimeException("I am not good thread");
        });
        t2.setName("Thread-T2-Misbehaving-Thread");
        t2.setUncaughtExceptionHandler((t, e) -> {
            System.out.printf(ANSI_RED + "Some critical exception occurred in Thread(%s) " +
                    "that needs to get catch, showing message(%s)\n" + ANSI_RESET,t.getName(),e.getMessage());
        });
        t1.start();
        t2.start();
    }
}
