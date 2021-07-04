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
}