package com.bo.aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @ClassName ABADemo1
 * @Description 了解什么是ABA问题以及怎么解决ABA问题
 * @Author huangbo1221
 * @Date 2021/11/9 23:18
 * @Version 1.0
 */
public class ABADemo1 {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 说明下什么是ABA问题
     */
    public static void test1() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        new Thread(() -> {
            System.out.println(atomicInteger.compareAndSet(1, 2));
            System.out.println(atomicInteger.compareAndSet(2, 1));
        }, "A").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicInteger.compareAndSet(1, 2));
        }, "B").start();
        // 上面的输出如下：三个都修改成功，在线程B看来，就好像线程A没有修改过atomicInteger一样。但是实际上线程A
        // 已经修改过atomicInteger的值了，只是线程A又给改了回来。
        /**
         * true
         * true
         * true
         */
    }

    /**
     * 可以利用原子引用类来解决多线程并发下的ABA问题。原子引用类引进了时间戳的概念，
     * 当对值进行修改后，时间戳可以自动加1，其他线程在修改时，不仅会判断值是否是期望的，
     * 还会判断时间戳是否被改了。若被改了，则不进行值的更新！
     */
    public static void test2() {
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 2, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("A1 value=" + atomicStampedReference.getReference() + "  " + "A1 stamp=" + atomicStampedReference.getStamp());
            System.out.println(atomicStampedReference.compareAndSet(2, 1, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("A2 value=" + atomicStampedReference.getReference() + "  " + "A2 stamp=" + atomicStampedReference.getStamp());
        },"A").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("B1 value=" + atomicStampedReference.getReference() + "  " + "B1 stamp=" + stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 2, stamp, stamp + 1));
            System.out.println("B2 value=" + atomicStampedReference.getReference() + "  " + "B2 stamp=" + atomicStampedReference.getStamp());
        }, "B").start();
        // 上面的输出结果如下：由结果可知，B2实际是更新失败的，虽然期望的值确实是1，但是stamp却变成了3，期待的stamp是1.因此无法更新值。
        // 这实际符合”乐观锁“的概念！！！
        /**
         * B1 value=1  B1 stamp=1
         * true
         * A1 value=2  A1 stamp=2
         * true
         * A2 value=1  A2 stamp=3
         * false
         * B2 value=1  B2 stamp=3
         */
    }
}
