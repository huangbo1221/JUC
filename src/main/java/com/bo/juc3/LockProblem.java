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

//        Phone2 phone = new Phone2();
//        new Thread(() -> phone.sendMsg()).start();
//        new Thread(() -> phone.call()).start();
        // 上面的输出如下：没有加锁的情况下，先输出发短信，再输出打电话！！！
        // 用synchronized锁住了两个方法。不管sendMsg方法里等待多少秒，都是先输出发短信！
        // 原因：synchronized是锁住的调用该方法的对象，对象是这里的phone对象，谁先拿到phone的锁，
        // 谁就先执行！！！
        /**
         * 发短信
         * 打电话
         */

//        Phone3 phone3_1 = new Phone3();
//        Phone3 phone3_2 = new Phone3();
//        new Thread(() -> phone3_1.sendMsg()).start();
//        new Thread(() -> phone3_2.call()).start();
        // 如果不在Phone3类的两个方法上加上static，使其编程静态方法，则输出如下：
        // 原因：synchronized锁的是phone3类的两个实例，对象不同，所以先执行打电话
        /**
         * 打电话
         * 发短信
         */
        // 如果在Phone3类的两个方法上加上了static变量，使其变成静态方法，则输出如下：
        // 原因：加了static方法后，两个方法变成了静态方法，静态方法是当类加载的时候就已经有了
        // 所以，此时synchronized锁的是phone3的模板类，即Phone3.class
        /**
         * 发短信
         * 打电话
         */

        Phone4 phone4 = new Phone4();
        new Thread(() -> phone4.sendMsg(), "A").start();
        new Thread(() -> phone4.call(), "B").start();
        // Phone4对象有一个静态方法和一个普通方法，此时输出顺序如何？
        // 输出如下：两个锁锁的对象不一样，相互不影响
        /**
         * 打电话
         * 发短信
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

class Phone3 {
    public static synchronized void sendMsg() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public static synchronized void call() {
        System.out.println("打电话");
    }
}

class Phone4 {
    public static synchronized void sendMsg() {
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