package com.amarpreet.thread;

public class MinMaxMetrics2 {

    public static void main(String[] args) {
        MinMaxMetrics2 minMaxMetrics = new MinMaxMetrics2();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> minMaxMetrics
                    .addSample(System.currentTimeMillis()));
            t.start();
        }

        System.out.println("Max sample collected\t" + minMaxMetrics.getMax());
        System.out.println("Min sample collected\t" + minMaxMetrics.getMin());
    }

    private volatile long minValue;
    private volatile long maxValue;

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics2() {
        this.maxValue = Long.MIN_VALUE;
        this.minValue = Long.MAX_VALUE;
    }

    /**
     * Adds a new sample to our metrics.
     */
    public void addSample(long newSample) {
        synchronized (this) {
            this.minValue = Math.min(newSample, this.minValue);
            this.maxValue = Math.max(newSample, this.maxValue);
        }
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        return this.minValue;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        return this.maxValue;
    }
}

