package com.bo.forkjoin;

/**
 * @ClassName ForkJoinDemo
 * @Description 初识ForkJoin
 * @Author huangbo1221
 * @Date 2021/10/29 8:46
 * @Version 1.0
 */

// 按照ForkJoin概念去理解，大任务怎么拆分成多个小任务？
// 下面的实例是求和计算的实例

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * 求1到10_0000_0000和
 * 1、直接遍历求和
 * 2、分成多个任务求和
 * 3、流式并发计算求和
 *
 * // 这个类主要针对分成多个任务求和
 * // 怎么分成多个任务呢？自然而然地想到分成多段！
 */
public class ForkJoinDemo extends RecursiveTask {
    private Long start;

    private Long end;

    private Long limit =1_0000L;

    public ForkJoinDemo(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (end - start < limit) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }
        return myCompute1();
    }

    public Long myCompute1() {
        // 以下的例子将任务分成两段，然后不断递归分成两段，以50000为间隔。这个间隔可优化。
        long middle = (start + end) / 2;
        ForkJoinDemo forkJoinTask1 = new ForkJoinDemo(start, middle);
        // 指导手册的解释是
        /**
         * 在当前任务正在运行的池中异步执行此任务（如果适用），
         */
        forkJoinTask1.fork();//拆分任务，把任务压入线程队列
        ForkJoinDemo forkJoinTask2 = new ForkJoinDemo(middle + 1, end);
        forkJoinTask2.fork();
        return (Long) forkJoinTask1.join() + (Long) forkJoinTask2.join();
    }

//    public Long myCompute2() {
//        Long totalNums = new Double(Math.ceil(end * 1.0 / limit)).longValue();
//        List<ForkJoinDemo> forkJoinDemos= new ArrayList<>();
//        for (int i = 0; i < totalNums - 1 ; i++) {
//            ForkJoinDemo forkJoinDemo = new ForkJoinDemo(i * limit + 1, limit * (i + 1));
//            forkJoinDemo.fork();
//            forkJoinDemos.add(forkJoinDemo);
//        }
//        ForkJoinDemo forkJoinDemo = new ForkJoinDemo(limit * (totalNums - 1), end);
//        forkJoinDemo.fork();
//        forkJoinDemos.add(forkJoinDemo);
//        Long sum = 0L;
//        for (ForkJoinDemo forkTask:forkJoinDemos) {
//            sum += (Long) forkTask.join();
//        }
//        return sum;
//    }
}
