package com.amarpreet.thread;

import java.util.concurrent.Semaphore;

public class SemaphoreExample1 {
    public static void main(String[] args) {

        StringBuilder sharedValue= new StringBuilder();

        Semaphore full = new Semaphore(1);
        Semaphore empty = new Semaphore(1);

        Thread producerThread = new Thread(() -> {
            while (true) {
                try {
                    empty.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sharedValue.append(" Ping ");
                full.release();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumerThread = new Thread(() -> {
            while (true) {
                try {
                    full.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sharedValue.append(" Pong ");
                empty.release();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producerThread.start();
        consumerThread.start();
        while(true){
            System.out.println(sharedValue.toString());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
