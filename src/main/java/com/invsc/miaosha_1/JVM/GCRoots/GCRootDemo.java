package com.invsc.miaosha_1.JVM.GCRoots;

import java.security.PublicKey;

/**
 * 有java中，可作为 GC Roots的象有
 * 1.虚拟机栈（栈帧的本地变量表）中引用的对象 t1
 * 2.方法区中的类静态属性引用的对象 t2
 * 3.方法区中常量引用的对象 t3
 * 4.本地方法栈中JNI（即一般说的native方法）中引用的对象 new Thread().start();
 */
public class GCRootDemo {
    private byte[] byteArray = new byte[10 * 1024 * 1024];

//    private static GCRootDemo2 t2;
//    private static final GCRootDemo3 t3 = new GCRootDemo3(8);

    private static void m1() {
        GCRootDemo t1 = new GCRootDemo();
        System.gc();
        System.out.println("第一次GC完成");
    }

    public static void main(String[] args) {
        m1();
    }
}
