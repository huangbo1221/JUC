package com.bo.lock;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName MyLockDemo1
 * @Description TODO
 * @Author huangbo1221
 * @Date 2021/11/11 22:28
 * @Version 1.0
 */
public class MyLockDemo1 {
    public static void main(String[] args) {
//        test1();
        // 上面的输出为：可见，线程B直到线程A释放锁后，采去获得锁！！！
        // 仔细分析LockDemo3的实现方式，与ReentrantLock相比，自己实现的并不是一个可重入锁。
        // 当在线程A执行的代码段里再获取锁时，也会出现循环等待！如test2的方法

        /**
         * myLcok:当前线程为----> A
         * myLcok:当前线程为----> B
         * myUnLcok:当前线程为----> A
         * myUnLcok:当前线程为----> B
         */

//        test2();
        // 上面的额输出为：然后线程不会推出，因为一直在等待锁的释放。
        // 这个问题可以采用一个计数器来解决，如test3方法
        /**
         * myLcok:当前线程为----> A
         * 获取到锁的线程---->A
         * myLcok:当前线程为----> A
         * myLcok:当前线程为----> B
         */

        test3();
        // 上面的输出为:程序能够顺利执行完成
        /**
         * myLcok:当前线程为----> A
         * 获取到锁的线程---->A
         * myLcok:当前线程为----> A
         * 再次获取到锁的线程---->A
         * myLcok:当前线程为----> B
         * myUnLock:当前线程为----> A
         * myUnLock:当前线程为----> A
         * myUnLock:当前线程为----> B
         */
    }

    public static void test1() {
        LockDemo3 lockDemo3 = new LockDemo3();
        new Thread(() -> {
            lockDemo3.myLcok();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lockDemo3.myUnLock();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                lockDemo3.myLcok();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lockDemo3.myUnLock();
            }
        }, "B").start();
    }

    public static void test2() {
        LockDemo3 lockDemo3 = new LockDemo3();
        new Thread(() -> {
            lockDemo3.myLcok();
            try {
                System.out.println("获取到锁的线程---->" + Thread.currentThread().getName());
                lockDemo3.myLcok();
                System.out.println("再次获取到锁的线程---->" + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lockDemo3.myUnLock();
                lockDemo3.myUnLock();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                lockDemo3.myLcok();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lockDemo3.myUnLock();
            }
        }, "B").start();
    }

    public static void test3() {
        LockDemo4 lockDemo4 = new LockDemo4();
        new Thread(() -> {
            lockDemo4.myLcok();
            try {
                System.out.println("获取到锁的线程---->" + Thread.currentThread().getName());
                lockDemo4.myLcok();
                System.out.println("再次获取到锁的线程---->" + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lockDemo4.myUnLock();
                lockDemo4.myUnLock();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                lockDemo4.myLcok();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lockDemo4.myUnLock();
            }
        }, "B").start();
    }
}
