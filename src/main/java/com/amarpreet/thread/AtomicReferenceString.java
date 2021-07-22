package com.amarpreet.thread;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceString {
    public static void main(String[] args) {
        String OLD_VALUE = "old_value";
        String NEW_VALUE = "new_value";

        AtomicReference atomicReference = new AtomicReference(OLD_VALUE);
        AtomicReference atomicReference2 = new AtomicReference(OLD_VALUE);
        if(atomicReference.compareAndSet(OLD_VALUE, NEW_VALUE)){
            System.out.println("Updated value::\t" + atomicReference.get());
        }

        atomicReference2.set("Unexpected Updated");
        if(atomicReference2.compareAndSet(OLD_VALUE, NEW_VALUE)){
            System.out.println("Updated value::\t" + atomicReference2.get());
        }else {
            System.out.println("Unexpected Value " + atomicReference2.get());
        }
    }
}
