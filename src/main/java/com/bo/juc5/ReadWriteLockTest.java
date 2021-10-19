package com.bo.juc5;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName ReadWriteLockTest
 * @Description 了解下读写锁的用法
 * @Author huangbo1221
 * @Date 2021/10/19 8:16
 * @Version 1.0
 */

/**
 * 独占锁（写锁） 一次只能被一个线程占有
 * 共享锁（读锁） 一次可以被多个线程占有
 * ReadWriteLock
 * 读-读 可以共存
 * 读-写 不能共存
 * 写-写 不能共存
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
//        MyCache myCache = new MyCache();
//        for (int i = 0; i < 5; i++) {
//            final int tmp = i;
//            new Thread(() -> {
//                myCache.put(String.valueOf(tmp), String.valueOf(tmp));
//            }, String.valueOf(i)).start();
//        }
//
//        for (int i = 0; i < 5; i++) {
//            final int tmp = i;
//            new Thread(() -> {
//                myCache.get(String.valueOf(tmp));
//            }, String.valueOf(i)).start();
//        }
        // 上面的代码输出如下：可以看出来一个问题，写的时候其他线程可以插队，而且可能出现还未写入就开始读取数据！！！
        /**
         * 开始写入： 1
         * 写入OK： 1
         * 开始写入： 2
         * 开始写入： 0
         * 开始写入： 3
         * 写入OK： 3
         * 写入OK： 0
         * 写入OK： 2
         * 开始读： 2
         * 开始写入： 4
         * 读取OK： 2
         * 开始读： 0
         * 读取OK： 0
         * 开始读： 1
         * 读取OK： 1
         * 开始读： 3
         * 读取OK： 3
         * 开始读： 4
         * 读取OK： 4
         * 写入OK： 4
         */

        MyCache2 myCache2 = new MyCache2();
        for (int i = 0; i < 5; i++) {
            final int tmp = i;
            new Thread(() -> {
                myCache2.put(String.valueOf(tmp), String.valueOf(tmp));
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int tmp = i;
            new Thread(() -> {
                myCache2.get(String.valueOf(tmp));
            }, String.valueOf(i)).start();
        }
        // 上面的输出如下：可以看出来，一定是先写，而且写的时候不能插队！读的时候没有顺序！！
        /**
         * 开始写入： 0
         * 写入OK： 0
         * 开始写入： 1
         * 写入OK： 1
         * 开始写入： 2
         * 写入OK： 2
         * 开始写入： 3
         * 写入OK： 3
         * 开始写入： 4
         * 写入OK： 4
         * 开始读： 0
         * 读取OK： 0
         * 开始读： 3
         * 开始读： 2
         * 读取OK： 2
         * 开始读： 4
         * 读取OK： 4
         * 开始读： 1
         * 读取OK： 1
         * 读取OK： 3
         */
    }
}

class MyCache {
    Map<String, Object> cache = new HashMap<>();

    public void put(String key, Object value) {
        System.out.println("开始写入： " + key);
        cache.put(key, value);
        System.out.println("写入OK： " + key);
    }

    public void get(String key) {
        System.out.println("开始读： " + key);
        cache.get(key);
        System.out.println("读取OK： " + key);
    }
}

class MyCache2 {
    private Map<String, Object> cache = new HashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println("开始写入： " + key);
            cache.put(key, value);
            System.out.println("写入OK： " + key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key) {

//        System.out.println("开始读： " + key);
//        cache.get(key);
//        System.out.println("读取OK： " + key);
        // 若像上面三行，读的时候不加读写锁，则输出如下。可以看出来，虽然写的时候没有插队，但是读可能会出现在写入前！
        /**
         * 开始写入： 0
         * 写入OK： 0
         * 开始写入： 1
         * 写入OK： 1
         * 开始写入： 3
         * 写入OK： 3
         * 开始读： 0
         * 开始读： 1
         * 读取OK： 1
         * 开始写入： 2
         * 写入OK： 2
         * 读取OK： 0
         * 开始写入： 4
         * 写入OK： 4
         * 开始读： 2
         * 开始读： 3
         * 读取OK： 3
         * 读取OK： 2
         * 开始读： 4
         * 读取OK： 4
         */
        readWriteLock.readLock().lock();
        try {
            System.out.println("开始读： " + key);
            cache.get(key);
            System.out.println("读取OK： " + key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}