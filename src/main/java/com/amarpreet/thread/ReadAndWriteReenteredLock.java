package com.amarpreet.thread;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import static java.util.concurrent.locks.ReentrantReadWriteLock.*;

public class ReadAndWriteReenteredLock {
    public static final int HIGHEST_PRICE = 1000;
    public static void main(String[] args) throws InterruptedException {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
        }

        Thread writer = new Thread(() -> {
            while (true) {
                inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
                inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ie){
                    ie.printStackTrace();
                }
            }
        });
        writer.setDaemon(true);
        writer.start();

        int numberOfReaderThreads = 7;
        List<Thread> readers = new ArrayList<>();

        for (int readerIndex = 0; readerIndex < numberOfReaderThreads; readerIndex++) {
            Thread reader = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    int upperBoundPrice = random.nextInt(HIGHEST_PRICE);
                    int lowerBoundPrice = upperBoundPrice > 0 ? random.nextInt(HIGHEST_PRICE) : 0;
                    inventoryDatabase.getNumberOfItemsInPriceRange(lowerBoundPrice, upperBoundPrice);
                }
            });

            reader.setDaemon(true);
            readers.add(reader);

        }

        long startTime = System.currentTimeMillis();
        for (Thread reader: readers
             ) {
            reader.start();
        }

        for (Thread reader: readers
        ) {
            reader.join();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken totally\t" + (endTime - startTime));
    }

    public static class InventoryDatabase {
        private TreeMap<Integer,Integer> priceToContMap = new TreeMap<>();
//        private ReentrantLock lock = new ReentrantLock();
        private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private WriteLock writeLock = reentrantReadWriteLock.writeLock();
        private ReadLock readLock = reentrantReadWriteLock.readLock();
        public int getNumberOfItemsInPriceRange(int lowerBound,int upperBound){
            readLock.lock();
            try {
            Integer fromKey = priceToContMap.ceilingKey(lowerBound);
            Integer toKey = priceToContMap.floorKey(upperBound);
            if(fromKey == null || toKey == null){
                return 0;
            }

            NavigableMap<Integer, Integer> rangeOfPrice = priceToContMap
                    .subMap(fromKey,true, toKey,true);
            int sum = 0;
            for (int numberOfItemsFromPrice:
                    rangeOfPrice.values()
                 ) {
                sum++;
            }
            return sum;
            }finally {
                readLock.unlock();
            }
        }
        public void addItem(int price) {
            writeLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToContMap.get(price);
                if(numberOfItemsForPrice == null){
                    priceToContMap.put(price, 1);
                } else {
                    priceToContMap.put(price, numberOfItemsForPrice + 1);
                }
            }finally {
                writeLock.unlock();
            }
        }

        public void removeItem(int price) {
            writeLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToContMap.get(price);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    priceToContMap.remove(price);
                } else {
                    priceToContMap.put(price, numberOfItemsForPrice - 1);
                }
            }finally {
                writeLock.unlock();
            }
        }
    }
}
