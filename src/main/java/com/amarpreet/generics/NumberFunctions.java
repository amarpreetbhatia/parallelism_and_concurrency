package com.amarpreet.generics;

public class NumberFunctions<T extends Number> {
    T object;

    public NumberFunctions(T object) {
        this.object = object;
    }

    double square() {
        return object.doubleValue() * object.doubleValue();
    }

    public static void main(String[] args) {
        NumberFunctions<Double> n1 = new NumberFunctions<>(20.0);
        System.out.println(n1.square());
    }
}
