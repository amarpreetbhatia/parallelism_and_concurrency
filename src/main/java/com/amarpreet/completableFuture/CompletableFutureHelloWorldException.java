package com.amarpreet.completableFuture;

import com.amarpreet.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorldException {

    HelloWorldService helloWorldService;

    public CompletableFutureHelloWorldException(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public String helloWorld_with_3_async_with_handle_func(){
        long startTime = System.currentTimeMillis();
        // Traditionally we call hello() and world() methods and Concatenate there results h + w
        CompletableFuture<String> hello = CompletableFuture
                .supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.
                supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hi = CompletableFuture
                .supplyAsync(() -> " Hi Completable Future");


        String result = hello
                .handle((res,e) -> {
                    if(e !=null){
                        System.out.println("Exception occur::" + e.getMessage());
                        return ""; // recover value, as handle() is recover
                    }else{
                        return res;
                    }

                })
                .thenCombine(world, (h,w) -> h+w) // where h is the return of hello and w is the return of world
                .handle((res,e) -> {
                    if(e !=null){
                        System.out.println("Exception on world method failed occur::" + e.getMessage());
                        return ""; // recover value, as handle() is recover
                    }else{
                        return res; //handle is called, even for no exception occured
                    }

                })
                .thenCombine(hi,(prev,current) -> prev + current)
                .thenApply(String::toUpperCase)
                .join();

        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken ::\t" + (endTime - startTime));
        return result;
    }

    public String helloWorld_with_3_async_with_exeptionally_func(){
        long startTime = System.currentTimeMillis();
        // Traditionally we call hello() and world() methods and Concatenate there results h + w
        CompletableFuture<String> hello = CompletableFuture
                .supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.
                supplyAsync(() -> helloWorldService.world());
        CompletableFuture<String> hi = CompletableFuture
                .supplyAsync(() -> " Hi Completable Future");


        String result = hello
                .exceptionally((e) -> {
                        System.out.println("Exception occur::" + e.getMessage());
                        return ""; // recover value, as handle() is recover
                })
                .thenCombine(world, (h,w) -> h+w) // where h is the return of hello and w is the return of world
                .exceptionally((e) -> {
                        System.out.println("Exception on world method failed occur::" + e.getMessage());
                        return ""; // recover value, as handle() is recover
                  })
                .thenCombine(hi,(prev,current) -> prev + current)
                .thenApply(String::toUpperCase)
                .join();

        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken ::\t" + (endTime - startTime));
        return result;
    }
}