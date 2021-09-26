package com.bo.juc3;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName LockProblem
 * @Description 了解锁的想关问题
 * @Author huangbo1221
 * @Date 2021/9/26 8:30
 * @Version 1.0
 */
public class LockProblem {
    public static void main(String[] args) {
//        Phone1 phone = new Phone1();
//        new Thread(() -> phone.sendMsg()).start();
//        new Thread(() -> phone.call()).start();
        // 上面的输出如下：没有加锁的情况下，先输出打电话，再输出发信息！！！
        /**
         * 打电话
         * 发短信
         */

        Phone2 phone = new Phone2();
        new Thread(() -> phone.sendMsg()).start();
        new Thread(() -> phone.call()).start();
        // 上面的输出如下：没有加锁的情况下，先输出发短信，再输出打电话！！！
        // 用synchronized锁住了两个方法。不管sendMsg方法里等待多少秒，都是先输出发短信！
        // 原因：synchronized是锁住的调用该方法的对象，对象是这里的phone对象，谁先拿到phone的锁，
        // 谁就先执行！！！
        /**
         * 发短信
         * 打电话
         */
    }
}

class Phone1 {
    public void sendMsg() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public void call() {
        System.out.println("打电话");
    }
}

class Phone2 {
    public synchronized void sendMsg() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public synchronized void call() {
        System.out.println("打电话");
    }
}