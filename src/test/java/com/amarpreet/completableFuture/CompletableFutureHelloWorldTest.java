package com.amarpreet.completableFuture;

import com.amarpreet.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    @Test
    void helloStringToUpperCase() {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFutureHelloWorld completableFutureHelloWorld
                = new CompletableFutureHelloWorld(helloWorldService);
        completableFutureHelloWorld.helloStringToUpperCase()
                .thenAccept(result -> {
                    assertEquals("HELLO WORLD",result);
                }).join();
    }

    @Test
    void helloWorld_withSize() {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFutureHelloWorld completableFutureHelloWorld
                = new CompletableFutureHelloWorld(helloWorldService);
        completableFutureHelloWorld.helloWorld_withSize()
                .thenAccept(result -> {
                    assertEquals("11 - hello world",result);
                }).join();
    }

    @Test
    void helloWorld_with_multiple_async() {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFutureHelloWorld completableFutureHelloWorld
                = new CompletableFutureHelloWorld(helloWorldService);
        String result = completableFutureHelloWorld.helloWorld_with_multiple_async();
        assertEquals("HELLO WORLD!", result);
    }

    @Test
    void helloWorld_with_3_async() {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFutureHelloWorld completableFutureHelloWorld
                = new CompletableFutureHelloWorld(helloWorldService);
        String result = completableFutureHelloWorld.helloWorld_with_3_async();
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE", result);
    }

    @Test
    void helloWorld_with_4_async() {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFutureHelloWorld completableFutureHelloWorld
                = new CompletableFutureHelloWorld(helloWorldService);
        String result = completableFutureHelloWorld.helloWorld_with_4_async();
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE BYE!", result);
    }

    @Test
    void helloString_with_ThenCompose() {
        long startTime = System.currentTimeMillis();
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFutureHelloWorld completableFutureHelloWorld
                = new CompletableFutureHelloWorld(helloWorldService);
        completableFutureHelloWorld.helloString_with_ThenCompose()
                .thenAccept(result -> {
                    assertEquals("HELLO WORLD!",result);
                }).join();
        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken::\t" + (endTime - startTime));
    }
}