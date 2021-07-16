package com.amarpreet.thread;

public class StrangeThreadIntrupt {
    public static void main(String[] args) {
        Thread thread = new Thread(new SleepingThread());
        thread.start();
        thread.interrupt();
    }

    private static class SleepingThread implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(100000);
                }catch (InterruptedException e){
                    System.out.println("I am not closing thread, even Thread.interrupt raised");
                    // return;  This return is important to closed the Interrupted Thread
                    System.exit(0); // return or System.exit(0)
                }
            }
        }
    }
}
