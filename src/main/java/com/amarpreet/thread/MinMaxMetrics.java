package com.amarpreet.thread;

import java.util.*;

public class MinMaxMetrics {

    public static void main(String[] args) {
        MinMaxMetrics minMaxMetrics = new MinMaxMetrics();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> minMaxMetrics
                    .addSample(System.currentTimeMillis()));
            t.start();
        }
        System.out.println("Total Sample collected ::\t"
                + minMaxMetrics.objList.size());
        System.out.println("Max sample collected\t" + minMaxMetrics.getMax());
        System.out.println("Min sample collected\t" + minMaxMetrics.getMin());
    }

    List<Long> objList = Collections.synchronizedList(new ArrayList<Long>());

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics() {
        // Add code here
    }

    /**
     * Adds a new sample to our metrics.
     */
    public void addSample(long newSample) {
        objList.add(newSample);
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        return Collections.min(objList);
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        return Collections.max(objList);
    }
}
