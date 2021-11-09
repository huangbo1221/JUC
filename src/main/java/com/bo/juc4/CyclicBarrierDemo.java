package com.bo.juc4;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @ClassName CyclicBarrierDemo
 * @Description 了解下CyclicBarrier的使用方式
 * @Author huangbo1221
 * @Date 2021/10/14 8:42
 * @Version 1.0
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("all finished!");
        });
        for (int i = 0; i < 7; i++) {
            final int tmp = i;
            new Thread(() -> {
                // lambda expression里直接用外部变量会报错，需要用final修饰
                System.out.println(Thread.currentThread().getName() + "  " +tmp);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("finished");
            }, String.valueOf(i)).start();
        }
        // 上面的输出如下：仔细分析下，CyclicBarrier是java提供的同步辅助类。
        //一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)，
        // 才得以继续执行。阻塞子线程，当阻塞数量到达定义的参与线程数后，才可继续向下执行。
        // 最后一句话很重要！！！当阻塞数量到达定义的参与线程数后，才可继续向下执行。！！！
        // 若new CyclicBarrier(8)，初始值为8，当前只有7个线程，所有线程都会一直阻塞！！！
        /**
         * 1  1
         * 5  5
         * 6  6
         * 0  0
         * 3  3
         * 2  2
         * 4  4
         * all finished!
         * finished
         * finished
         * finished
         * finished
         * finished
         * finished
         * finished
         */
        // 从上面的输出可以知道，所有线程都执行System.out.println(Thread.currentThread().getName() + "  " +tmp);后
        // 会执行cyclicBarrier.await();来阻塞，此时就是公共屏障点！然后输出all finished。最后每个线程再往下执行。
    }
}
