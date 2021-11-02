package com.bo.volatiletest;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName VolatileDemo2
 * @Description 了解volatile保证可见性和不保证原子性的特征
 * @Author huangbo1221
 * @Date 2021/11/2 8:47
 * @Version 1.0
 */
public class VolatileDemo2 {

    public volatile static int num = 0;

    public static int num1 = 0;

    public static int num2 = 0;

    public static AtomicInteger num3 = new AtomicInteger();

    public static Lock lock = new ReentrantLock();

    public static void add() {
        ++num;// 不是一个原子的操作
    }

    public synchronized static void add1() {
        ++num1;
    }

    public static void add2() {
        lock.lock();
        ++num2;
        lock.unlock();
    }

    public static void add3() {
        num3.incrementAndGet();
    }

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();

    }

    public static void test1() {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }
        // 上面20个线程，每个线程执行1000次add，预期num=20000

        // 判断当前线程是否大于两个（至少会有两个：main线程和gc线程），若大于两个，执行yield
        // 即当前线程让出资源，重新让所有线程竞争CPU资源，可能还会是main线程竞争到
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(num);

        // 若你在add方法上加了同步synchronized，如下：
        /**
         * public synchronized static void add() {
         *         ++num;
         *     }
         */
        // 输出为20000，synchronized肯定能保证原子性

        // 若你在add方法上加了同步volatile，如下：
        /**
         * public volatile static int num = 0;
         */
        // 输出为18272，说明volatile不保证原子性！！
    }

    /**
     * synchronized保证了操作的原子性
     */
    public static void test2() {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add1();
                }
            }).start();
        }
        // 上面20个线程，每个线程执行1000次add，预期num=20000

        // 判断当前线程是否大于两个（至少会有两个：main线程和gc线程），若大于两个，执行yield
        // 即当前线程让出资源，重新让所有线程竞争CPU资源，可能还会是main线程竞争到
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(num1);

        // 若你在add方法上加了同步synchronized，如下：
        /**
         * public synchronized static void add() {
         *         ++num;
         *     }
         */
        // 输出为20000，synchronized肯定能保证原子性

        // 若你在add方法上加了同步volatile，如下：
        /**
         * public volatile static int num = 0;
         */
        // 输出为18272，说明volatile不保证原子性！！
    }

    public static void test3() {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add2();
                }
            }).start();
        }
        // 上面20个线程，每个线程执行1000次add，预期num=20000

        // 判断当前线程是否大于两个（至少会有两个：main线程和gc线程），若大于两个，执行yield
        // 即当前线程让出资源，重新让所有线程竞争CPU资源，可能还会是main线程竞争到
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(num2);
    }

    public static void test4() {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add3();
                }
            }).start();
        }
        // 上面20个线程，每个线程执行1000次add，预期num=20000

        // 判断当前线程是否大于两个（至少会有两个：main线程和gc线程），若大于两个，执行yield
        // 即当前线程让出资源，重新让所有线程竞争CPU资源，可能还会是main线程竞争到
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(num3);
    }
}
