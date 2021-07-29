package com.amarpreet.generics;

public class MyGeneralClass<T> {

    T object;

    public MyGeneralClass(T object) {
        this.object = object;
    }

    void showType(){
        System.out.println(object.getClass().getTypeName());
    }

    public static void main(String[] args) {
        MyGeneralClass<Integer> g1 = new MyGeneralClass<>(10);
        MyGeneralClass<String> s1 = new MyGeneralClass<>("general");
        g1.showType();
        s1.showType();
    }
}
