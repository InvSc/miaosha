package com.invsc.miaosha_1.JVM.Reference;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
    public static void main(String[] args) {
        myHashMap();
        System.out.println("====================");
        myWeakHashMap();
    }

    private static void myHashMap() {
        Map<Integer, String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "hashMap";
        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.runFinalization(); // 强制调用已经失去引用的对象的finalize方法
        System.out.println(map + "\t" + map.size());
    }
    private static void myWeakHashMap() {
        Map<Integer, String> map = new WeakHashMap<>();
        /**
         * 下面的key一定不能这样定义：
         *      Integer key = 2;
         * 这是因为对于可在编译阶段确定的字符串，系统的字符串常量池会直接记录它，
         * 自动保留对它的强引用，此情况下，GC无法从WeakHashMap中删除这个Key
         * 我们这样用new Integer会在运行时生成于堆中，才能被回收
         */
        Integer key = new Integer(2);
        String value = "weakHashMap"; // weak在于key而非value
        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);


//        System.gc();
        System.out.println(map + "\t" + map.size());
    }


}
