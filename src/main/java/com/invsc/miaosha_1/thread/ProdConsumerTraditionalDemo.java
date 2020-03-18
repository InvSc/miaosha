package com.invsc.miaosha_1.thread;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：初值为0变量，两个线程对其交替加一减一来五轮
 * 口诀（高内聚 低耦合）；
 *  线程操纵资源类
 *  判断 干活 唤醒通知
 *  严防多线程并发状态下的虚假唤醒 要明白为什么用while而不是if
 * 八个知识点：
 *
 */
class SharedData {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void increment() {
        // lock不能写在try语句内部，防止lock失败依然执行unlock方法导致异常
        // 见https://blog.csdn.net/u013568373/article/details/98480603
        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void decrement() {
        try {
            lock.lock();
            while (number == 0) {
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
public class ProdConsumerTraditionalDemo {
    public static void main(String[] args) {
        SharedData data = new SharedData();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                data.increment();
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                data.decrement();
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                data.increment();
            }
        }, "CC").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                data.decrement();
            }
        }, "DD").start();
    }
}
