package com.invsc.miaosha_1.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ArrayBlockingQueue 基于数组结构的有界阻塞队列
 * LinkedBlockingQueue 基于链表的阻塞队列 吞吐量通常高于ArrayBlockingQueue
 * SynchronousQueue 一个不存储元素的阻塞队列 每个插入操作在线程调用移除操作之前都是阻塞的 吞吐量较高
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws Exception {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("x", 2L, TimeUnit.SECONDS));
    }
}
