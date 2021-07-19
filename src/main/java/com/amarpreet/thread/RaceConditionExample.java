package com.amarpreet.thread;

public class RaceConditionExample {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter ic = new InventoryCounter();
        IncrementThread it = new IncrementThread(ic);
        DecrementThread dt = new DecrementThread(ic);

        it.start();
//        it.join();
        dt.start();

        it.join();
        dt.join();
        System.out.println("We currently have " + ic.getItems() + " items");
    }

    public static class IncrementThread extends Thread{
        InventoryCounter inventoryCounter;
        IncrementThread(InventoryCounter inventoryCounter){
            this.inventoryCounter = inventoryCounter;
        }
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    public static class DecrementThread extends Thread{
        InventoryCounter inventoryCounter;
        DecrementThread(InventoryCounter inventoryCounter){
            this.inventoryCounter = inventoryCounter;
        }
        @Override
        public void run() {
            for (int i = 10000; i > 0; i--) {
                inventoryCounter.decrement();
            }
        }
    }
    public static class InventoryCounter{
        private int items =0;

        public synchronized void increment() {
            items++;
        }

        public synchronized void decrement() {
            items--;
        }

        public synchronized int getItems() {
            return items;
        }
    }
}
