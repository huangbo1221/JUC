package com.bo.volatiletest;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName VolatileDemo
 * @Description TODO
 * @Author huangbo1221
 * @Date 2021/11/1 8:49
 * @Version 1.0
 */
public class VolatileDemo {
    public static int num = 0;

    public volatile static int num1 = 0;

    public static void main(String[] args) {
//        test1();
        // 上面执行完后，程序会退出

//        test2();
        // 上面执行完后程序永远不会退出，对比下两个函数的差异：while循环一个有语句，一个无语句
        // 子线程的num是从其工作内存取，while语句调用太频繁意味着访问num太频繁，
        // 导致主内存不会及时刷新工作内存的num(详细可了解JMM(Java memory Model))，
        // 所以一直会访问到num为0，当加入System.out.println()后，访问num就会有明显间隔，
        // 主内存就会有空去刷新到子线程的工作内存，这样才能正常退出while循环。

        test3();
        // test3输出1后立刻退出，volatile可以保证可见性
    }

    public static void test1() {
        new Thread(() -> {
            while (num == 0) {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num = 1;
        System.out.println(num);
    }

    public static void test2() {
        new Thread(() -> {
            while (num == 0) {
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num = 1;
        System.out.println(num);
    }

    public static void test3() {
        new Thread(() -> {
            while (num1 == 0) {
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num1 = 1;
        System.out.println(num1);
    }
}
