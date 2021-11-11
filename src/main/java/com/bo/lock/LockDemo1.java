package com.bo.lock;

/**
 * @ClassName LockDemo1
 * @Description 了解什么是可重入锁
 * @Author huangbo1221
 * @Date 2021/11/11 8:31
 * @Version 1.0
 */
public class LockDemo1 {
    public static void main(String[] args) {
        LockDemo1 lockDemo1 = new LockDemo1();
        lockDemo1.test1();
        // 上面的输出为：在执行sendMs方法时，就已经获取到了phone实例对象的锁。而在该方法内再调用call方法，又在重复获取
        // phone实例对象的锁，但是此时并没有因为该实例对象的锁被持有而发生死锁等待，而是直接进去执行了call方法。这就是可重入锁！！！
        // 这也说明，synchronized是可重入锁！！！
        /**
         * A send message!
         * A call!
         */
    }

    public void test1() {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendMs();
        },"A").start();
    }

    class Phone {
        public synchronized void sendMs() {
            System.out.println(Thread.currentThread().getName() + " send message!");
            call();
        }

        public synchronized void call() {
            System.out.println(Thread.currentThread().getName() + " call!");
        }
    }


}
