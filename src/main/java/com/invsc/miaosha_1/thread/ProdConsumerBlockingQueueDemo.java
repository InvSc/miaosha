package com.invsc.miaosha_1.thread;

import org.apache.naming.factory.ResourceLinkFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile/CAS/AtomicInteger/AtomicReference/BlockingQueue/线程交互
 *
 * 两种注入
 *      设值注入 Setter
 *      构造注入 Constructor
 */
class MyReSource {
    private volatile boolean FLAG = true; // 默认开启生产+消费，volatile保持可见性
    private AtomicInteger number = new AtomicInteger();
    private BlockingQueue<String> queue = null;
    public MyReSource(BlockingQueue<String> queue) {
        this.queue = queue;
        System.out.println(queue.getClass().getName());
    }
    public void MyProd() {
        String data;
        boolean retValue;
        while (FLAG) {
            try {
                data = number.incrementAndGet() + "";
                retValue = queue.offer(data, 2L, TimeUnit.SECONDS);
                if (retValue) {
                    System.out.println(Thread.currentThread().getName() + "\t 插入数据" + data + "成功");
                } else {
                    System.out.println(Thread.currentThread().getName() + "\t 插入数据" + data + "失败");
                }
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t 生产结束");
    }
    public void MyConsumer() {
        String result;
        while (FLAG) {
            try {
                result = queue.poll(2L, TimeUnit.SECONDS);
                if (result == null) {
                    FLAG = false; // 我觉得没有必要，这是一种自我阻断
                    System.out.println(Thread.currentThread().getName() + "\t 2s内取出数据失败，消费结束");
                    return;
                }
                System.out.println(Thread.currentThread().getName() + "\t 取出数据" + result + "成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void MyStop() {
        FLAG = false;
    }
}
public class ProdConsumerBlockingQueueDemo {
    public static void main(String[] args) {
        // 固化思维方式 传参永远传接口
        MyReSource reSource = new MyReSource(new ArrayBlockingQueue<String>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "开始生产工作");
            reSource.MyProd();
        }, "MyProd").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "开始消费工作");
            reSource.MyConsumer();
        }, "MyConsumer").start();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "*****全面停工*****");
        reSource.MyStop();
        // 由于线程取值读值的无序性，我们的打印信息里有可能先消费后生产
    }
}

