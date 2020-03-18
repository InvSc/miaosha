package com.invsc.miaosha_1.thread;

import java.awt.geom.RectangularShape;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 思路：
 *  -由于多个进程同时读取一个资源类没有任何问题，为了满足并发量，
 *  读取共享资源可以同时进行
 *  但是
 *  如果有一个进程正在写共享资源类，就不应该有其他线程对其同时读或者写
 *
 *  总结：
 *      读-读 可共存
 *      读-写 不能共存
 *      写-写 不能共存
 *
 *      写操作：原子 独占
 */

class MyCache {
    // volatile 保证可见性 常见的缓存实现要求
    private volatile Map<String, Object> map = new HashMap<>();
     // private Lock lock = new ReentrantLock();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void get(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取");
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object reslult = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + reslult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
        }
    }
    // clear 未完工
    public void clear() {
        map.clear();
    }
}
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.put(num + "", num + "");
            }, String.valueOf(i)).start();
        }
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.get(num + "");
            }, String.valueOf(i)).start();
        }
    }
}
