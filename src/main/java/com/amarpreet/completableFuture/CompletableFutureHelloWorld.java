package com.amarpreet.completableFuture;

import com.amarpreet.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorld {

    public static void main(String[] args) throws InterruptedException {
        HelloWorldService helloWorldService = new HelloWorldService();

        CompletableFuture
                .supplyAsync(() -> helloWorldService.helloWorld())
                .thenAccept((result) -> {
                    System.out.println("Result is :::\t" + result);
                });
//        .join();
        System.out.println("Done!!");
//        Thread.sleep(2000);
    }
}
