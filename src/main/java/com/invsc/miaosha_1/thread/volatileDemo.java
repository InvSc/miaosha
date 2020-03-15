package com.invsc.miaosha_1.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Data {
    volatile int number = 0;
    public void numTo60(){
        this.number = 60;
    }
    public void addPlus() {
        this.number++;
    }
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic() {
        atomicInteger.getAndIncrement();
    }
}

public class volatileDemo {
    public static void main(String[] args) {
        Data myData = new Data();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlus();
                    myData.addMyAtomic();
                }
            }, String.valueOf(i)).start();
        }
        // 为什么是2，因为默认有main和GC线程
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t int type: updated number value " + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t AtomicInteger type: updated number value " + myData.atomicInteger);
    }

    public static void seeOkByVolatile() {
        Data myData = new Data();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.numTo60();
            System.out.println(Thread.currentThread().getName() + "\t updated number value " + myData.number);
        }, "AAA").start();

        while (myData.number == 0) {

        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over " + myData.number);
    }

}


