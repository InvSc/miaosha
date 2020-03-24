package com.invsc.miaosha_1.JVM.Reference;

import java.lang.ref.WeakReference;

public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> ref = new WeakReference<>(o1);

        System.out.println(o1);
        System.out.println(ref.get());

        o1 = null;
        System.out.println("====================");

        System.gc(); // 一旦gc 就被回收

        System.out.println(o1);
        System.out.println(ref.get());
    }
}
