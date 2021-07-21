package com.amarpreet.thread;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReenteredLockExample {
    public static void main(String[] args) {

        PriceContainer priceContainer = new PriceContainer();
        PriceUpdater priceUpdater = new PriceUpdater(priceContainer);
        Thread displayThread = null;
        if(priceContainer.getLock().tryLock()){
            try {
                displayThread = new Thread(() -> {
                    while (true) {
                        System.out.println("Bitcoin Price " + priceContainer.getBitcoinPrice());
                        System.out.println("Ether Price " + priceContainer.getEtherPrice());
                        System.out.println("Bitcoin CashPrice " + priceContainer.getBitcoinCashPrice());
                        System.out.println("Lite coin Price " + priceContainer.getLitecoinPrice());
                        System.out.println("Ripple Price " + priceContainer.getRipplePrice());
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                  }
                );
            }finally {
                priceContainer.getLock().unlock();
            }
        }
        priceUpdater.start();
        Optional.ofNullable(displayThread).ifPresent(Thread::start);
    }

    public static class PriceContainer {
        private Lock lock = new ReentrantLock();

        private double bitcoinPrice;
        private double etherPrice;
        private double litecoinPrice;
        private double bitcoinCashPrice;
        private double ripplePrice;

        public Lock getLock() {
            return lock;
        }

        public void setLock(Lock lock) {
            this.lock = lock;
        }

        public double getBitcoinPrice() {
            return bitcoinPrice;
        }

        public void setBitcoinPrice(double bitcoinPrice) {
            this.bitcoinPrice = bitcoinPrice;
        }

        public double getEtherPrice() {
            return etherPrice;
        }

        public void setEtherPrice(double etherPrice) {
            this.etherPrice = etherPrice;
        }

        public double getLitecoinPrice() {
            return litecoinPrice;
        }

        public void setLitecoinPrice(double litecoinPrice) {
            this.litecoinPrice = litecoinPrice;
        }

        public double getBitcoinCashPrice() {
            return bitcoinCashPrice;
        }

        public void setBitcoinCashPrice(double bitcoinCashPrice) {
            this.bitcoinCashPrice = bitcoinCashPrice;
        }

        public double getRipplePrice() {
            return ripplePrice;
        }

        public void setRipplePrice(double ripplePrice) {
            this.ripplePrice = ripplePrice;
        }
    }

    public static class PriceUpdater extends Thread {
        private PriceContainer priceContainer;
        private Random random = new Random();
        public PriceUpdater(PriceContainer priceContainer){
            this.priceContainer = priceContainer;
        }

        @Override
        public void run() {
            while (true){
                priceContainer.getLock().lock();
                try{
                    try {

                        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                    priceContainer.setBitcoinPrice(random.nextInt(20000));
                    priceContainer.setEtherPrice(random.nextInt(2000));
                    priceContainer.setLitecoinPrice(random.nextInt(500));
                    priceContainer.setBitcoinCashPrice(random.nextInt(5000));
                    priceContainer.setRipplePrice(random.nextDouble());
                }finally {
                    priceContainer.getLock().unlock();
                }
            }
        }
    }


}
