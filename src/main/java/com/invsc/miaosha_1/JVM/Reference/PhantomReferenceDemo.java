package com.invsc.miaosha_1.JVM.Reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * Java提供了4种引用类型，在垃圾回收的时候，都有自己各自的特点
 * ReferenceQueue是用来配合引用工作的，没有 ReferenceQueue一样可以运行。
 * 创建引用的时候可以指定关联的队列，当GC释放对象内存的时候，会将引用加入到引用队列
 * 如果程发现某个虚引用已经放加入到引用从列，那么就可以在所引用的对象的内存被回收之前采取必要的行动
 * 这相当于是一种通知机制,类似于springAOP的后置通知
 * 当关联的引用队列中有数据的时候，意味若引用指向的堆内存中的对象被回收。
 * 通过这种方式，JVM允许我们在对象被销毁后，做一些我们自已想做的事情。
 */

/**
 * PhantomReference 99.99%工作中用不到
 */
public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object         o1    = new Object();
        ReferenceQueue<Object> queue = new ReferenceQueue();
        Reference<Object>      ref   = new PhantomReference<>(o1, queue); // 记得传入引用队列
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
