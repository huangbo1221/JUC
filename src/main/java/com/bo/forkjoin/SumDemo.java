package com.bo.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * @ClassName ForkJoinSumDemo
 * @Description 了解ForkJoinPool的使用和并行流
 * @Author huangbo1221
 * @Date 2021/10/29 20:11
 * @Version 1.0
 */
public class SumDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
        test2();
        test3();
        // 上面三行的输出如下：
        /**
         * sum: 500000000500000000 time: 337
         * sum: 500000000500000000 time: 249
         * sum: 500000000500000000 time: 149
         */
    }

    public static void test1() {
        long start = System.currentTimeMillis();
        long sum = 0;
        for (long i = 0; i <= 10_0000_0000; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("sum: " + sum + " time: " + (end - start));
    }

    public static void test2() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinDemo(0, 10_0000_0000);
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);
        final Long sum = submit.get();
        long end = System.currentTimeMillis();
        System.out.println("sum: " + sum + " time: " + (end - start));
    }

    public static void test3() {
        long start = System.currentTimeMillis();
        long sum = LongStream.rangeClosed(0, 10_0000_0000).parallel().reduce(0, Long::sum);
        // 疑问：上面reduce函数中的identity为0，到底表示什么呢？从例子中看出含义！！！
        LongStream.rangeClosed(0, 5).parallel().reduce(1, Long::sum);
        // 输出 21
        LongStream.rangeClosed(1, 5).parallel().reduce(1, Long::sum);
        // 输出 20
        LongStream.rangeClosed(1, 5).parallel().reduce(0, Long::sum);
        // 输出15
        LongStream.rangeClosed(0, 5).parallel().reduce(0, Long::sum);
        // 输出15
        // 从上面的例子看出，就好像rangeClosed(0, 5)中的每一个数跟identity相加！！！
        long end = System.currentTimeMillis();
        System.out.println("sum: " + sum + " time: " + (end - start));
    }
}
