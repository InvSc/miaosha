package com.invsc.miaosha_1.thread;

import lombok.Getter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.invsc.miaosha_1.thread.ThreadEnum.*;

/**
 * 题目： Synchronize和Lock有什么区别？Lock有什么好处？举例说明?
 * 1 原始构成
 *  Synchronize属于关键字属于jvm层面(底层通过monitor对象来完成，其实wait/notify等方法也依赖于monotor对象来完成，只有在同步块或方法中才能被调用)
 *      monitorenter
 *      monitorexit(为什么有两次,第一次是正常退出，第二次是异常退出)
 *  Lock是具体类(java.util.concurrent.locks.Lock),属于api层面的锁
 * 2. 使用方法
 *  Synchronize不需要用法手动释放锁,Synchronize代码执行完成后系统会自动让线程释放对锁的占用
 *  ReentrantLock需要用户手动释放,否则可能出现死锁
 *      需要lock()和unlock()方法配合try/catch语句块使用
 * 3等待是否可中断
 *  synchronized不可中断，除非抛出异常或者正常运行完成
 *  Reentrantlock可中断，1.设置超时方法 tryLock(Long timeout， TimeUnit unit)
 *                     2. LockInteruptibly()放代码块中，调用 interrupt()方法可中断
 * 4加锁是否公平
 *  synchronized公平锁
 *  ReentrantLock两者都可以，默认非公，构造方法可以传人 boolean值，如true为公平锁， false为非公平锁
 * 5锁绑定多个条件Condition
 *  synchronized没有
 *  Reentrantlock用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像synchronized要么随机唤醒一个线程要么唤醒全部线程
 *
 * 题目： 多线程之间按A->B->C的顺序调用
 *      AA打印5次，BB打印10次，CC打印15次
 *      循环10轮
 * TODO: 如何用一个方法完成print
 */
@Getter
enum ThreadEnum {
    ONE(5, "AA"), TWO(10, "BB"), THR(15, "CC");
    private Integer loopTimes;
    private String  threadName;
    ThreadEnum(Integer loopTimes, String threadName) {
        this.loopTimes = loopTimes;
        this.threadName = threadName;
    }
    public static ThreadEnum ForEachThreadEnum(String str) {
        ThreadEnum[] myArray = ThreadEnum.values();
        for (ThreadEnum threadEnum : myArray) {
            if (threadEnum.getThreadName() == str) {
                return threadEnum;
            }
        }
        return null;
    }
}
class SharedResource {
    private int number = 1;
    private Lock lock = new ReentrantLock();
    Condition c1= lock.newCondition();
    Condition c2= lock.newCondition();
    Condition c3= lock.newCondition();
    public void print5() {
        lock.lock();
        try {
            // 1 判断
            while (number != 1) {
                c1.await();
            }
            // 2 干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 3 通知
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print10() {
        lock.lock();
        try {
            // 1 判断
            while (number != 2) {
                c2.await();
            }
            // 2 干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 3 通知
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print15() {
        lock.lock();
        try {
            // 1 判断
            while (number != 3) {
                c3.await();
            }
            // 2 干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 3 通知
            number = 1;
            c1.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
public class SynAndReentrantLockDemo {
    public static void main(String[] args) {
        SharedResource data = new SharedResource();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.print5();
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.print10();
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.print15();
            }
        }, "CC").start();
    }
}
