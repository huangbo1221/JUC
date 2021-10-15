package com.bo.juc4;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SemaphoreDemo
 * @Description 初步了解Semaphore的使用方式
 * @Author huangbo1221
 * @Date 2021/10/15 8:22
 * @Version 1.0
 */

/**
 * semaphore.acquire(); 将信号量+1，直到达到初始化的值为止，开始阻塞等待
 * semaphore.release();将信号量-1，然后唤醒等待的线程
 *
 * 作用：1、多个共享资源互斥的使用
 *      2、并发限流、控制最大线程数
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 初始的3表示允许的线程数量为3
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i < 7; i++) {
            new Thread(() -> {
                try {
                    // 获取资源
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "获得资源");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + "释放资源");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放资源
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
        // 最终输出如下
        /**
         * 1获得资源
         * 2获得资源
         * 3获得资源
         * 1释放资源
         * 3释放资源
         * 2释放资源
         * 4获得资源
         * 5获得资源
         * 6获得资源
         * 5释放资源
         * 6释放资源
         * 4释放资源
         */
    }
}
