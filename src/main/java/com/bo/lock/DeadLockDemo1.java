package com.bo.lock;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName DeadLockDemo1
 * @Description 死锁的例子
 * @Author huangbo1221
 * @Date 2021/11/11 23:13
 * @Version 1.0
 */
public class DeadLockDemo1 {

    private Chair chair;

    private Mirror mirror;

    public DeadLockDemo1(Chair chair, Mirror mirror) {
        this.chair = chair;
        this.mirror = mirror;
    }

    public void useCharFirst() {
        synchronized (chair) {
            System.out.println("线程" + Thread.currentThread().getName() + "获得了椅子");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (mirror) {
                System.out.println("线程" + Thread.currentThread().getName() + "获得了镜子");
            }
        }
    }

    public void useMirrorFirst() {
        synchronized (mirror) {
            System.out.println("线程" + Thread.currentThread().getName() + "获得了镜子");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (chair) {
                System.out.println("线程" + Thread.currentThread().getName() + "获得了椅子");
            }
        }
    }


    public static void main(String[] args) {
        Chair chair = new Chair();
        Mirror mirror = new Mirror();
        DeadLockDemo1 person1 = new DeadLockDemo1(chair, mirror);
        DeadLockDemo1 person2 = new DeadLockDemo1(chair, mirror);
        new Thread(() -> {
            person1.useCharFirst();
        }, "A").start();

        new Thread(() -> {
            person2.useMirrorFirst();
        }, "B").start();

        // 上面的输出为：然后程序卡住，发生了死锁
        /**
         * 线程A获得了椅子
         * 线程B获得了镜子
         */
    }

    static class Chair {
        public synchronized void sit() {
            System.out.println("线程" + Thread.currentThread().getName() + "获得了椅子");
        }
    }

    static class Mirror {
        public synchronized void useMirror() {
            System.out.println("线程" + Thread.currentThread().getName() + "获得了镜子");
        }
    }
}
