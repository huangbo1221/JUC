package com.bo.juc4;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName CountDownLatchDemo
 * @Description 了解CountDownLatch的作用
 * @Author huangbo1221
 * @Date 2021/10/14 8:27
 * @Version 1.0
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "started!");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        // 不加下面的countDownLatch.await();时，则输出可能如下：
        // 即主线程可能会在所有子线程运行完之前，先输出。不会等待子线程运行完！
        /**
         * 1started!
         * 4started!
         * 2started!
         * 3started!
         * 0started!
         * finished
         * 5started!
         */

        System.out.println("hhh");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 加了上面的countDownLatch.await();后，输出如下：
        // countDownLatch.await();表示会等待countDownLatch初始时的数值变为0.然后才往下执行!!!
        /**
         * 0started!
         * 4started!
         * 3started!
         * 2started!
         * 1started!
         * 5started!
         * finished
         */
        System.out.println("finished");
    }
}
