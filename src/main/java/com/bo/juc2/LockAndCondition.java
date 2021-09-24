package com.bo.juc2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName LockAndCondition
 * @Description TODO
 * @Author huangbo1221
 * @Date 2021/9/24 8:11
 * @Version 1.0
 */
public class LockAndCondition {
}

class Shop {
    private int data = 0;

    private Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();

    public static void main(String[] args) {
        Shop shop = new Shop();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shop.increase();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shop.decrease();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shop.increase();
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shop.decrease();
            }
        }, "D").start();
        // 输出如下：从下面结果中可以看出来，没有办法让A\B\C\D按顺序执行
        /**
         * A:  1
         * B:  0
         * A:  1
         * B:  0
         * A:  1
         * B:  0
         * A:  1
         * B:  0
         * A:  1
         * B:  0
         * A:  1
         * B:  0
         * A:  1
         * B:  0
         * A:  1
         * B:  0
         * A:  1
         * B:  0
         * A:  1
         * B:  0
         * C:  1
         * D:  0
         * C:  1
         * D:  0
         * C:  1
         * D:  0
         * C:  1
         * D:  0
         * C:  1
         * D:  0
         * C:  1
         * D:  0
         * C:  1
         * D:  0
         * C:  1
         * D:  0
         * C:  1
         * D:  0
         * C:  1
         * D:  0
         */
    }

    public void increase() {
        lock.lock();
        try {
            while (data != 0) {
                condition.await();
            }
            data++;
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + ":  " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrease() {
        lock.lock();
        try {
            while (data == 0) {
                condition.await();
            }
            data--;
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + ":  " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
