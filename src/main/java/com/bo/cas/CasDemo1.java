package com.bo.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName CasDemo1
 * @Description 了解什么是CAS
 * @Author huangbo1221
 * @Date 2021/11/5 8:41
 * @Version 1.0
 */
public class CasDemo1 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2021);
        System.out.println(atomicInteger.compareAndSet(2021, 2022));
        System.out.println(atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(2021, 2022));
        System.out.println(atomicInteger.get());
        // 上面的输出为:
        /**
         * true
         * 2022
         * false
         * 2022
         */

        atomicInteger.getAndIncrement();

    }
}
