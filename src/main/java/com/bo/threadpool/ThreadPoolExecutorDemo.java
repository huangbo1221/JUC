package com.bo.threadpool;

import java.util.concurrent.*;

/**
 * @ClassName ThreadPoolExecutorDemo
 * @Description 了解阿里手册推荐的创建线程方式ThreadPoolExecutor
 * @Author huangbo1221
 * @Date 2021/10/26 8:50
 * @Version 1.0
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    public static void test1() {
        // 七大参数的创建方式来办理银行业务实例，如bank.JPG所示
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),//默认的线程工厂，一般不用动
                new ThreadPoolExecutor.AbortPolicy());// 默认的拒绝策略，会抛出异常
        try {
            for (int i = 0; i < 9; i++) {
                // 注意线程池的执行方式！！！
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " excuted!");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 任务执行完需要关闭线程池
            threadPoolExecutor.shutdown();
        }
        // 当for循环中的i<2时，输出如下:
        /**
         * pool-1-thread-1 excuted!
         * pool-1-thread-2 excuted!
         */

        // 当for循环中的i<3时，输出如下:虽然最大线程数为5，但是那是在达到阻塞队列的设定值后才会启用的，
        // 所以第三个线程去了阻塞队列排队，由核心线程pool-1-thread-1和pool-1-thread-2来处理业务
        /**
         * pool-1-thread-1 excuted!
         * pool-1-thread-2 excuted!
         * pool-1-thread-1 excuted!
         */

        // 当for循环中的i<5时，输出如下:虽然最大线程数为5，但是那是在达到阻塞队列的设定值后才会启用的，
        // 所以第三个线程去了阻塞队列排队，由核心线程pool-1-thread-1和pool-1-thread-2来处理业务
        /**
         * pool-1-thread-2 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-2 excuted!
         * pool-1-thread-2 excuted!
         * pool-1-thread-1 excuted!
         */

        // 当for循环中的i<6时，输出如下:最大线程数为5，在达到阻塞队列的设定值后会多启用一个线程pool-1-thread-3
        /**
         * pool-1-thread-2 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-3 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-2 excuted!
         * pool-1-thread-3 excuted!
         */

        // 当for循环中的i<6时，输出如下:最大线程数为5，在达到阻塞队列的设定值后会多启用一个线程pool-1-thread-3和pool-1-thread-4
        /**
         * pool-1-thread-1 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-3 excuted!
         * pool-1-thread-2 excuted!
         * pool-1-thread-3 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-1 excuted!
         */

        // 当for循环中的i<9时，输出如下:最大线程数为5，阻塞队列的值为3，总共最多也只能为8.但是此时达到了9，因此拒绝策略生效
        // 默认的拒绝策略会抛出异常
        /**
         * pool-1-thread-2 excuted!
         * pool-1-thread-5 excuted!
         * pool-1-thread-2 excuted!
         * pool-1-thread-5 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-3 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-2 excuted!
         * java.util.concurrent.RejectedExecutionException: Task com.bo.threadpool.ThreadPoolExecutorDemo$$Lambda$1/1831932724@7699a589 rejected from java.util.concurrent.ThreadPoolExecutor@58372a00[Running, pool size = 5, active threads = 5, queued tasks = 3, completed tasks = 0]
         * 	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2063)
         * 	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:830)
         * 	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1379)
         * 	at com.bo.threadpool.ThreadPoolExecutorDemo.test1(ThreadPoolExecutorDemo.java:30)
         * 	at com.bo.threadpool.ThreadPoolExecutorDemo.main(ThreadPoolExecutorDemo.java:14)
         */
    }

    public static void test2() {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()); // CallerRunsPolicy()表示超出线程池所能承受的最大值的线程由
        // 父线程区处理
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " excuted!");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        // 输出如下：可见，超出的两个线程由main线程去执行了
        /**
         * main excuted!
         * main excuted!
         * pool-1-thread-3 excuted!
         * pool-1-thread-2 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-2 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-3 excuted!
         * pool-1-thread-5 excuted!
         */
    }

    public static void test3() {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()); // DiscardPolicy()表示直接丢弃超出的线程
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " excuted!");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        // 输出如下：可见，超出的两个线程被丢弃了
        /**
         * pool-1-thread-1 excuted!
         * pool-1-thread-5 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-3 excuted!
         * pool-1-thread-2 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-1 excuted!
         * pool-1-thread-5 excuted!
         */
    }

    public static void test4() {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()); // DiscardPolicy()表示把阻塞队列最靠前的任务丢弃，重新尝试
        // 执行当前任务
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " excuted!");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        // 输出如下：表示把阻塞队列最靠前的任务丢弃，重新尝试执行当前任务
        /**
         * pool-1-thread-1 excuted!
         * pool-1-thread-5 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-3 excuted!
         * pool-1-thread-2 excuted!
         * pool-1-thread-4 excuted!
         * pool-1-thread-5 excuted!
         * pool-1-thread-1 excuted!
         */
    }
}
