package com.invsc.miaosha_1.thread;

import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
class holdLockThread implements Runnable{
    String lockA;
    String lockB;

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 已获得lockA, 想获得lockB");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 已获得lockB, 想获得lockA");
            }
        }
    }
}

public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new holdLockThread(lockA, lockB), "AAA").start();
        new Thread(new holdLockThread(lockB, lockA), "BBB").start();
    }
    /**
     * 如何排查故障
     *      jps -l
     *          找到 14756 com.invsc.miaosha_1.thread.DeadLockDemo
     *      jstack 14756
     *          分析对应进程的堆栈报告，一目了然
     */
}
