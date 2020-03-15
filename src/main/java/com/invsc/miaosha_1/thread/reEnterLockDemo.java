package com.invsc.miaosha_1.thread;

import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable{
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t" + "invoked sendSMS");
        sendEmail();
    }
    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t" + "invoked sendEmail");
    }
    Lock lock = new ReentrantLock();

    @Override
    public void run(){
        lock.lock();
        try{
            get();
        } finally {
            lock.unlock();
        }
    }
    public void get(){
        lock.lock();
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t" + "invoked get");
            set();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }
    public void set(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t" + "invoked set");
        } finally {
            lock.unlock();
        }
    }
}



public class reEnterLockDemo{
    public static void main(String[] args) throws Exception{
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, new String("t1")).start();
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, new String("t2")).start();

        TimeUnit.SECONDS.sleep(1);

        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        t3.start();
        t4.start();

    }

}
