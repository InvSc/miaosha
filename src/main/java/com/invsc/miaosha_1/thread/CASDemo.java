package com.invsc.miaosha_1.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 1 CAS是什么 ===> Compare And Swap
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data: " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current data: " + atomicInteger.get());
        atomicInteger.getAndIncrement();
    }
}
