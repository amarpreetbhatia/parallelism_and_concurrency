package com.amarpreet.service;
import java.util.concurrent.CompletableFuture;

import static com.amarpreet.util.CommonUtil.delay;
import static com.amarpreet.util.LoggerUtil.mylog;


public class HelloWorldService {

    public  String helloWorld() {
        delay(1000);
        mylog("inside helloWorld");
        return "hello world";
    }

    public  String hello() {
        delay(1000);
        mylog("inside hello");
        return "hello";
    }

    public  String world() {
        delay(1000);
        mylog("inside world");
        return " world!";
    }

    public CompletableFuture<String> worldFuture(String input) {
        return CompletableFuture.supplyAsync(()->{
            delay(1000);
            return input+" world!";
        });
    }

}
