package com.invsc.miaosha_1.JVM.Reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Queue;

public class ReferenceQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Object                 o1 = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue();
        Reference<Object>      ref = new WeakReference<>(o1, queue); // 记得传入引用队列
        System.out.println(o1);
        System.out.println(ref.get());
        System.out.println(queue.poll());

        System.out.println("=====================");
        o1 = null;
        System.gc();
        Thread.sleep(500);

        System.out.println(o1);
        System.out.println(ref.get());
        System.out.println(queue.poll());
    }
}
