package com.invsc.miaosha_1.thread;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch
 * 类比 火箭发射倒计时
 */
public class CountDownLatchDemo {
    private static final int studentCount = 6;
    private static final int countryCount = 6;
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(countryCount);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t国灭亡");
                countDownLatch.countDown(); // 调用countDown()的线程不会被阻塞
            }, CountryEnum.getCountryName(i)).start();
        }
        countDownLatch.await(); // 下面main线程被阻塞
        System.out.println(Thread.currentThread().getName() + "\t******秦一统天下******"); // 最后被唤醒
    }

    private static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(studentCount);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t同学放学走人了");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t******班长锁门走人******");
    }
}
