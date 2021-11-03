package com.bo.single;

/**
 * @ClassName LazyDemo1
 * @Description 懒汉式单例模式
 * @Author huangbo1221
 * @Date 2021/11/3 22:21
 * @Version 1.0
 */
public class LazyDemo1 {

    private static LazyDemo1 LAZYDEMO1;

    private LazyDemo1() {
//        System.out.println(Thread.currentThread().getName());
    }

    public static LazyDemo1 getInstance() {
        // 使用的时候再new一个对象出来！
        if (LAZYDEMO1 == null) {
            LAZYDEMO1 = new LazyDemo1();
        }
        return LAZYDEMO1;
    }



}
