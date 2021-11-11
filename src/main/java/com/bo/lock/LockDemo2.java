package com.bo.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName LockDemo1
 * @Description 了解什么是可重入锁
 * @Author huangbo1221
 * @Date 2021/11/11 8:31
 * @Version 1.0
 */
public class LockDemo2 {
    public static void main(String[] args) {
        LockDemo2 lockDemo2 = new LockDemo2();
        lockDemo2.test1();
        // 上面的输出为：在执行sendMs方法时，就已经获取到了phone实例对象的锁。而在该方法内再调用call方法，又在重复获取
        // phone实例对象的锁，但是此时并没有因为该实例对象的锁被持有而发生死锁等待，而是直接进去执行了call方法。这就是可重入锁！！！
        // 这也说明，ReentrantLock是可重入锁！！！
        /**
         * A send message!
         * A call!
         */
        System.out.println("==============================");
        // 一定要注意。有多少个lock.lock();，就一定要有同样数量的lock.unlock();
        // 否则将导致死锁！！！
    }

    public void test1() {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendMs();
        },"A").start();
    }

    class Phone {
        private Lock lock = new ReentrantLock();

        public void sendMs() {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " send message!");
            call();
            lock.unlock();
        }

        public void call() {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " call!");
            lock.unlock();
        }
    }


}
