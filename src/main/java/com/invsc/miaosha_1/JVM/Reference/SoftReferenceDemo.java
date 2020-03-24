package com.invsc.miaosha_1.JVM.Reference;

import java.lang.ref.SoftReference;

public class SoftReferenceDemo {
    /**
     * -Xms30m -Xmx30m -XX:+PrintGCDetails
     */
    public static void softRefMemoNotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
//        System.out.println(o1);
//        System.out.println(softReference.get());
        o1 = null; // 删除了强引用，只保留一个软引用
        // 充满内存，不需要调用System.gc()即可被回收
        try {
            byte[] arr = new byte[30 * 1024 * 1024];
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println(o1);
            System.out.println(softReference.get());
        }
    }

    public static void main(String[] args) {
        softRefMemoNotEnough();
    }
}
