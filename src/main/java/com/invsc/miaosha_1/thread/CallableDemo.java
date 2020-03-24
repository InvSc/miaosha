package com.invsc.miaosha_1.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * java多线程中第三种获得多线程的方式
 * 前两种
 *      继承Thread类
 *      实现Runnable接口
 * Callable的出现
 *      设计思想 适配器模式
 *      为了并发异步的考虑，线程调度与优化
 * Callable与Runnable区别
 *      是否有返回值
 *      是否可以抛异常
 *      接口实现的方法不一样 run call
 */
//class runImpl implements Runnable {
//
//    @Override
//    public void run() {
//
//    }
//}
class myThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t 暂停了三秒钟");
        TimeUnit.SECONDS.sleep(3);
        return 1024;
    }
}
public class CallableDemo {

    public static void main(String[] args) throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new myThread());
        new Thread(futureTask, "AA").start();
        // 两个线程使用同样的futureTask只会执行一次
        new Thread(futureTask, "").start();
        while (!futureTask.isDone()) {

        }
        int number1 = 999;
        int number2 = futureTask.get(); // 如果取不到结果（运算没有完成）会导致阻塞，务必在最后执行或添加while (!futureTask.isDone())判断
        System.out.println(Thread.currentThread().getName() + "\t 计算完成，结果为" + (number1 + number2));
    }
}
