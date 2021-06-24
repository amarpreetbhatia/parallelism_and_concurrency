package com.amarpreet.util;

public class LoggerUtil {

    public static void mylog(String message){

        System.out.println("[" + Thread.currentThread().getName() +"] - " + message);

    }
}