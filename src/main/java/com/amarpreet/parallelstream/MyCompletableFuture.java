package com.amarpreet.parallelstream;

import java.util.concurrent.CompletableFuture;

public class MyCompletableFuture {

    public static CompletableFuture<Integer> compute(){
        return CompletableFuture.supplyAsync(() -> 2);
    }

    public static void main(String[] args) {
        compute()
                .thenAccept((data) -> System.out.println(data))
                .thenRun(() -> System.out.println("This will return"));

    }
}
