package com.amarpreet.completableFuture;

import com.amarpreet.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.amarpreet.util.CommonUtil.delay;

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

    public String helloWorld_with_multiple_async(){
       long startTime = System.currentTimeMillis();
        // Traditionally we call hello() and world() methods and Concatenate there results h + w
       CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
       CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
       String result = hello.thenCombine(world, (h,w) -> h+w) // where h is the return of hello and w is the return of world
               .thenApply(String::toUpperCase)
               .join();
        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken ::\t" + (endTime - startTime));
       return result;
    }

    public String helloWorld_with_3_async(){
        long startTime = System.currentTimeMillis();
        // Traditionally we call hello() and world() methods and Concatenate there results h + w
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> " Hi Completable Future");
        String result = hello.thenCombine(world, (h,w) -> h+w) // where h is the return of hello and w is the return of world
                .thenCombine(hi,(prev,current) -> prev + current)
                .thenApply(String::toUpperCase)
                .join();
        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken ::\t" + (endTime - startTime));
        return result;
    }


    public String helloWorld_with_4_async(){
        long startTime = System.currentTimeMillis();
        // Traditionally we call hello() and world() methods and Concatenate there results h + w
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hi = CompletableFuture.supplyAsync(() -> {
            delay(1000); return " Hi Completable Future";
        });
        CompletableFuture<String> bye = CompletableFuture.supplyAsync(() -> {
            delay(1000); return " Bye!";
        });


        String result = hello.thenCombine(world, (h,w) -> h+w) // where h is the return of hello and w is the return of world
                .thenCombine(hi,(prev,current) -> prev + current)
                .thenCombine(bye,(prevHi,currentBye) -> prevHi + currentBye)
                .thenApply(String::toUpperCase)
                .join();


        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken ::\t" + (endTime - startTime));
        return result;
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
