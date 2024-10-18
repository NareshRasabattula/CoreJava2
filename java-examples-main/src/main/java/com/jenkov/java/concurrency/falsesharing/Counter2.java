package com.jenkov.java.concurrency.falsesharing;


public class Counter2 {

    @jdk.internal.vm.annotation.Contended
    public volatile long count1 = 0;
    //padding bytes
    public volatile long count2 = 0;

}
