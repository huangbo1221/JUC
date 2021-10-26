package com.bo.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ExecutorsDemo
 * @Description 初步了解线程池Executors
 * @Author huangbo1221
 * @Date 2021/10/26 8:10
 * @Version 1.0
 */

/**
 * 阿里开发手册不推荐使用Executors的方式直接区开辟线程池！！！
 * 因为Executors方式默认的线程数量会达到亿级别，容易造成OOM
 */
public class ExecutorsDemo {
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    public static void test1() {
        // 顾名思义：单个线程的池子
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            for (int i = 0; i < 10; i++) {
                // 注意线程池的执行方式！！！
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " excuted!");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 任务执行完需要关闭线程池
            executorService.shutdown();
        }
        // 上面的输出如下，可以看出永远只有一个线程在执行该任务
        /**
         * pool-1-thread-1 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-1 excuted!
         */
    }

    public static void test2() {
        // 固定线程数量的方式
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            for (int i = 0; i < 10; i++) {
                // 注意线程池的执行方式！！！
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " excuted!");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 任务执行完需要关闭线程池
            executorService.shutdown();
        }
        // 上面的输出如下，可以看出最多会有五个线程来执行任务
        /**
         * pool-1-thread-2 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-3 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-5 excuted!
         * pool-1-thread-3 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-2 excuted!
         */
    }

    public static void test3() {
        // 线程数量与开辟的任务和系统资源有关的方式
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                // 注意线程池的执行方式！！！
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " excuted!");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 任务执行完需要关闭线程池
            executorService.shutdown();
        }
        // 上面的输出如下，可以看出开辟多少个线程，线程池就会让多少个线程来执行任务（跟系统资源有关）
        /**
         * pool-1-thread-2 excuted!
         * pool-1-thread-3 excuted!
         * pool-1-thread-6 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-5 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-10 excuted!
         * pool-1-thread-8 excuted!
         * pool-1-thread-7 excuted!
         * pool-1-thread-9 excuted!
         */
    }
}



