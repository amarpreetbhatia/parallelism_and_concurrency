package com.amarpreet.thread;

public class PingPong implements Runnable{
    int delay;
    String word;

    public PingPong(String word,int delay ) {
        this.delay = delay;
        this.word = word;
    }

    @Override
    public void run() {
        int i = 0;
//        System.out.println("I am in RUN " + Thread.currentThread());
        try {
            while (i<10){
                System.out.println(word + " " + Thread.currentThread());

                    Thread.sleep(delay);
                    i++;
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       Thread ping = new Thread(new PingPong("Ping", 100), "T1");
        Thread pong = new Thread(new PingPong("PONG", 100), "T2");
        ping.start();
        pong.start();
    }
}
