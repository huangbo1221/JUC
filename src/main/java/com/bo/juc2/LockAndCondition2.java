package com.bo.juc2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName LockAndCondition2
 * @Description 怎么按顺序唤醒？
 * @Author huangbo1221
 * @Date 2021/9/24 8:42
 * @Version 1.0
 */
public class LockAndCondition2 {

    public static void main(String[] args) {
        Shop2 shop2 = new Shop2();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shop2.printA();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shop2.printB();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shop2.printC();
            }
        }, "C").start();
        System.out.println("finished!");

        // 输出如下：思考，若只是通过data值来唤醒指定线程，然后采用signalAll方法，是达不到目的的。
        // 因为data!=某个值，其他另外两个线程都会来改变data值！
        /**
         * A=======> BBBBBBBBBB
         * B=======> CCCCCCCCCC
         * finished!
         * C=======> AAAAAAAAAAA
         * A=======> BBBBBBBBBB
         * B=======> CCCCCCCCCC
         * C=======> AAAAAAAAAAA
         * A=======> BBBBBBBBBB
         * B=======> CCCCCCCCCC
         * C=======> AAAAAAAAAAA
         * A=======> BBBBBBBBBB
         * B=======> CCCCCCCCCC
         * C=======> AAAAAAAAAAA
         * A=======> BBBBBBBBBB
         * B=======> CCCCCCCCCC
         * C=======> AAAAAAAAAAA
         * A=======> BBBBBBBBBB
         * B=======> CCCCCCCCCC
         * C=======> AAAAAAAAAAA
         * A=======> BBBBBBBBBB
         * B=======> CCCCCCCCCC
         * C=======> AAAAAAAAAAA
         * A=======> BBBBBBBBBB
         * B=======> CCCCCCCCCC
         * C=======> AAAAAAAAAAA
         * A=======> BBBBBBBBBB
         * B=======> CCCCCCCCCC
         * C=======> AAAAAAAAAAA
         * A=======> BBBBBBBBBB
         * B=======> CCCCCCCCCC
         * C=======> AAAAAAAAAAA
         */
    }
}

class Shop2 {
    private int data = 1;

    Lock lock = new ReentrantLock();

    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void printA() {
        lock.lock();
        try {
            while (data != 1) {
                condition1.await();
            }
            data = 2;
            System.out.println(Thread.currentThread().getName() + "=======> BBBBBBBBBB");
            // 用条件来判断唤醒哪个线程执行哪个方法
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (data != 2) {
                condition2.await();
            }
            data = 3;
            System.out.println(Thread.currentThread().getName() + "=======> CCCCCCCCCC");
            // 用条件来判断唤醒哪个线程执行哪个方法
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            while (data != 3) {
                condition3.await();
            }
            data = 1;
            System.out.println(Thread.currentThread().getName() + "=======> AAAAAAAAAAA");
            // 用条件来判断唤醒哪个线程执行哪个方法
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
