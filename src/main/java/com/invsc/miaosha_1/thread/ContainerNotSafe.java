package com.invsc.miaosha_1.thread;

import java.util.*;

/**
 * 容器不安全问题
 * ArrayList
 */
public class ContainerNotSafe {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
//        List<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0 ,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
        // java.util.ConcurrentModificationException
    }
    /**
     * 1 故障现象
     *  java.util.ConcurrentModificationException
     * 2 导致原因
     *  使用ArrayList在输出过程中toString会使用迭代器遍历List容器，遍历过程中会有其它线程对List修改，导致modCount != expectedCount引发异常
     * 3 解决方案
     *  3-0 给toString加synchronized锁 最low的办法
     *
     *  3-1 new Vector<>();
     *  3-2 Collections.synchronizedList(new ArrayList<>())
     *      synchronizedMap、synchronizedSet 同理
     * 4 优化建议
     */
}
