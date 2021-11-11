package com.bo.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName LockDemo3
 * @Description 了解什么是自旋锁
 * @Author huangbo1221
 * @Date 2021/11/11 22:15
 * @Version 1.0
 */

// 实现一把自己的锁
public class LockDemo4 {
    // 默认的Thread是null
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    private int count = 0;

    public void myLcok() {
        Thread thread = Thread.currentThread();
        System.out.println("myLcok:当前线程为----> " + thread.getName());

        // 若当前线程已经获得了锁，计数器加一，同时不要再循环等待，直接返回即可
        if (thread == atomicReference.get()) {
            count++;
            return;
        }

        // 自旋锁。当该锁被其他线程获取到时，atomicReference的值(atomicReference.get())不为null
        // 就会一直循环等待！采用的是CAS的机制
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void myUnLock() {
        Thread thread = Thread.currentThread();
        System.out.println("myUnLock:当前线程为----> " + thread.getName());

        // 释放锁时，若获取锁的线程是当前线程。当计数器大于1时，不能直接执行atomicReference.compareAndSet(thread, null);
        // 因为若置为了null，则线程B可能会抢到锁，但是线程A重入了，必须得等到A完全释放锁才行！
        if (thread == atomicReference.get()) {
            if (count > 0) {
                count--;
                return;
            }
        }
        atomicReference.compareAndSet(thread, null);
    }
}
