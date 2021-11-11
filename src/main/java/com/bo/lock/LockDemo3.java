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
public class LockDemo3 {
    // 默认的Thread是null
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLcok() {
        Thread thread = Thread.currentThread();
        System.out.println("myLcok:当前线程为----> " + thread.getName());
        // 自旋锁。当该锁被其他线程获取到时，atomicReference的值(atomicReference.get())不为null
        // 就会一直循环等待！采用的是CAS的机制
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public
    void myUnLock() {
        Thread thread = Thread.currentThread();
        System.out.println("myLcok:当前线程为----> " + thread.getName());
        atomicReference.compareAndSet(thread, null);
    }
}
