package com.bo.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CompleTableDemo
 * @Description 初步了解CompleTableFuture的异步调用
 * @Author huangbo1221
 * @Date 2021/11/1 8:14
 * @Version 1.0
 */
public class CompleTableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main task started!");
        System.out.println(test1());
        System.out.println("main task!!!");
        // 输出为：从上面的例子中可以看出来，若在子线程里采用异步调用，且在子线程里面执行了get方法
        // 那么子线程会运行完，才会回到主线程，执行get方法后实际上是阻塞了。
        // 应该在主线程执行子线程的get方法。
        // 若永远不执行get方法，则主线程执行完后退出时，不管子线程执行到哪一步，都会退出！
        /**
         * main task started!
         * supplyAsync task!
         * child thread started sleep
         * child thread finished!
         * u: 200
         * e: null
         * 200
         * main task!!!
         */
    }

    public static Integer test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> uCompletableFuture = CompletableFuture.supplyAsync(
            () -> {
                System.out.println("supplyAsync task!");
                try {
                    System.out.println("child thread started sleep");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("child thread finished!");
                return 200;
            });
       return uCompletableFuture.whenComplete((u, e) -> {
            System.out.println("u: " + u);
            System.out.println("e: " + e);
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return 502;
        }).get();
    }
}
