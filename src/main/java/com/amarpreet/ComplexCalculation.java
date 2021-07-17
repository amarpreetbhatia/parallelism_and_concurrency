package com.amarpreet;

import java.math.BigInteger;

public class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        BigInteger result;
        PowerCalculatingThread calculatingThread1 = new PowerCalculatingThread(base1,power1);
        PowerCalculatingThread calculatingThread2 = new PowerCalculatingThread(base2,power2);
        calculatingThread1.start();
        calculatingThread2.start();

        try {
            calculatingThread1.join();
            calculatingThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return calculatingThread1.getResult().add(calculatingThread2.getResult());
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
           result = BigInteger.ONE;
           for(BigInteger i = BigInteger.ZERO;
               i.compareTo(power) != 0;
               i = i.add(BigInteger.ONE)
           ){
               result = result.multiply(base);
           }
        }

        public BigInteger getResult() { return result; }
    }

    public static void main(String[] args) {
        ComplexCalculation cc = new ComplexCalculation();
        System.out.println(cc.calculateResult(BigInteger.TWO,
                BigInteger.TWO,
                BigInteger.TEN,
                BigInteger.TEN).toString());

    }

}
