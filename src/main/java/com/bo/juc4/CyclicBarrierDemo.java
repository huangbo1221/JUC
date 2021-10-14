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
                // 若将cyclicBarrier.await();放到循环里面，则输出如下：
                /**
                 * 0  0
                 * 3  3
                 * 4  4
                 * 1  1
                 * finished
                 * 5  5
                 * 2  2
                 * 6  6
                 * all finished!
                 * finished
                 * finished
                 * finished
                 * finished
                 * finished
                 * finished
                 * finished
                 */
            }, String.valueOf(i)).start();
        }
        // 若将cyclicBarrier.await();放到循环外，则输出如下：
        /**
         * 0  0
         * 3  3
         * 5  5
         * 6  6
         * 2  2
         * 1  1
         * 4  4
         */
//        try {
//            cyclicBarrier.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (BrokenBarrierException e) {
//            e.printStackTrace();
//        }
        System.out.println("finished");
    }
}
