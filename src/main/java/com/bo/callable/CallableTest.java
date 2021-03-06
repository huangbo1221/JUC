package com.bo.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName CallableTest
 * @Description 了解下实现多线程的第三种方式callable
 * @Author huangbo1221
 * @Date 2021/10/12 8:17
 * @Version 1.0
 */

/**
 * 与runnable不同点：
 * 1、能抛出异常
 * 2、有返回值
 * 3、方法不同（runnable/run     callable/call）
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread myThread = new MyThread();
        // Thread的构造方法的target为Runnable，要与Callable扯上关系，得借助FutureTask
        // 因为FutureTask是Runnable的一个实现，而FutureTask的构造方法里可以有Callable参数
        FutureTask futureTask = new FutureTask(myThread);
//        new Thread(futureTask).start();
//        String res = (String) futureTask.get();
//        System.out.println(res);
        // 上面输出：hahah



        // 改成如下，call方法会调用几次呢？
        new Thread(futureTask, "A").start();
        new Thread(futureTask, "B").start();
        String res = (String) futureTask.get();
        System.out.println(res);
        // 输出：hahah
        // 原因：call方法只被调用了一次，可以仔细看看FutureTask的run方法
        // run方法里会判断当前futureTask的state是不是NEW状态，若不是，则直接return
        // 当线程A执行后，state就已经不是NEW状态了
    }
}

class MyThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "hahah";
    }
}
