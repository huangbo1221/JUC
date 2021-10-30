package com.bo.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName FutureDemo1
 * @Description TODO
 * @Author huangbo1221
 * @Date 2021/10/30 8:38
 * @Version 1.0
 */
public class FutureDemo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture completableFuture = test1();
        test2();
        System.out.println("main thread!");
        System.out.println(completableFuture.get());
        // 上面的输出，没啥好解释的，异步回调不影响主线程往下执行
        /**
         * main thread!
         * thread!
         * ForkJoinPool.commonPool-worker-1:runAsync
         * null
         */

        test3();
        // test3的输出如下：
        /**
         * ForkJoinPool.commonPool-worker-1supplyAsync!
         * t:1024
         * u:null
         */

        test4();
        // test4的输出如下:
        /**
         * ForkJoinPool.commonPool-worker-1supplyAsync2!
         * t:null
         * u:java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
         * java.lang.ArithmeticException: / by zero
         */
    }

    public static CompletableFuture test1() {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + ":runAsync");
            }
        });
        return completableFuture;
    }

    public static void test2() {
        new Thread(() -> System.out.println("thread!")).start();
    }

    public static Integer test3() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "supplyAsync!");
            return 1024;
        });
        return integerCompletableFuture.whenComplete((t,u) -> {
            System.out.println("t:" + t);
            System.out.println("u:" + u);
        }).exceptionally((e) ->{
            System.out.println(e.getMessage());
            return 233;
        }).get();
    }

    public static Integer test4() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "supplyAsync2!");
            int res = 10 / 0;
            return 1024;
        });
        return integerCompletableFuture.whenComplete((t,u) -> {
            System.out.println("t:" + t);// 根据test3和test4的输出可知，t表示正常时的返回值
            System.out.println("u:" + u);// u表示错误堆栈信息
        }).exceptionally((e) ->{
            System.out.println(e.getMessage());
            return 233;
        }).get();
    }
}
