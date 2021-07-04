package com.amarpreet.completableFuture;

import com.amarpreet.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorld {
    HelloWorldService helloWorldService;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public CompletableFuture<String> helloStringToUpperCase() {
        return CompletableFuture
                .supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> helloWorld_withSize(){
        return CompletableFuture
                .supplyAsync(helloWorldService::helloWorld)
                .thenApply((data) -> {
                    return String.format("%d - %s", data.length(), data);
                });
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFutureHelloWorld completableFutureHelloWorld
                = new CompletableFutureHelloWorld(helloWorldService);
        completableFutureHelloWorld
                .helloStringToUpperCase()
                .thenAccept((result) -> {
                    System.out.println("Result is :::\t" + result);
                })
        .join();
        System.out.println("Done!!");
//        Thread.sleep(2000);
    }


}
