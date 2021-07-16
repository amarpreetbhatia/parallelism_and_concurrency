package com.amarpreet.thread;

import java.io.IOException;

public class WaitingForUserInput {
    public static void main(String[] args) {
        Thread thread = new Thread(new WaitingForUserInputInner());
        thread.setName("InputWaitingThread");
//        thread.setDaemon(true);
        thread.start();
    }

    private static class WaitingForUserInputInner implements Runnable {

        @Override
        public void run() {
            try {
                while (true){
                    char input = (char)System.in.read();
                    if(input == 'q'){
                        return;
                    }
                }
            }catch (IOException e){
                System.out.println("An exception was caught " + e);
            }
        }
    }
}
