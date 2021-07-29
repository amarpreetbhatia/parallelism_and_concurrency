package com.amarpreet.generics;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UtilClass {
    public <T> void printValues(List<T> o){
        for (T t : o) {
            System.out.println(t);
        }
    }

    public <T extends Number> Double multiplies(T t1, T t2){
        return t1.doubleValue() * t2.doubleValue();
    }
}

class SampleUtilConsumer {
    public static void main(String[] args) {
        UtilClass.printValues(List.of("one","two","three"));
        UtilClass.printValues(List.of(1,1.2,50));
        System.out.println(UtilClass.multiplies(10,60));
        System.out.println(UtilClass.multiplies(6.7,1.6));
    }
}
