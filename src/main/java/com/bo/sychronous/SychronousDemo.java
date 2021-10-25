package com.bo.sychronous;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @ClassName SychronousDemo
 * @Description 了解SynchronousQueue的用法
 * @Author huangbo1221
 * @Date 2021/10/25 8:20
 * @Version 1.0
 */

/**
 * 同步队列
 * 和其他的BlockingQueue不一样，SynchronousQueue不存储元素。
 * put了一个元素之后，必须执行take取出一个元素，否则不能再继续put元素
 */
public class SychronousDemo {
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    public static void test1() {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + "存入" + i);
                    blockingQueue.put(String.valueOf(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {

                    System.out.println(Thread.currentThread().getName() + "取出" + blockingQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 上面的输出如下：
        /**
         * Thread-0存入1
         * Thread-1取出1
         * Thread-0存入2
         * Thread-1取出2
         * Thread-0存入3
         * Thread-1取出3
         */
    }

    public static void test2() {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    blockingQueue.put(String.valueOf(i));
                    System.out.println(Thread.currentThread().getName() + "存入" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // 这里输出语句在put方法后面，执行该test2方法时，会发现没有任何输出，表面在执行put后，线程就
        // 已经进入了阻塞，必须调用take之后，才会有输出！！可以看看test3的例子
    }

    public static void test3() {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    blockingQueue.put(String.valueOf(i));
                    System.out.println(Thread.currentThread().getName() + "存入" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {

                    System.out.println(Thread.currentThread().getName() + "取出" + blockingQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 上面的输出如下：执行顺序为   put-->take-->put中的输出语句
        /**
         * Thread-1取出1
         * Thread-0存入1
         * Thread-0存入2
         * Thread-1取出2
         * Thread-1取出3
         * Thread-0存入3
         */
    }

}
