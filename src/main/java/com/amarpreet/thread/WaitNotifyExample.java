package com.amarpreet.thread;

public class WaitNotifyExample {
    boolean isCompleted = false;

    // Executed by thread1
    public synchronized void declareSuccess() throws InterruptedException {
        while (!isCompleted) {
            wait();
        }

        System.out.println("Success!!");
    }

    // Executed by thread2
    public synchronized void finishWork() {
        isCompleted = true;
        notify();
    }
}