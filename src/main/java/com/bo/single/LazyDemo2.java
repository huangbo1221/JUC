package com.bo.single;

/**
 * @ClassName LazyDemo2
 * @Description 双重检测锁的懒汉式单例模式
 * @Author huangbo1221
 * @Date 2021/11/3 22:29
 * @Version 1.0
 */
public class LazyDemo2 {

    private static LazyDemo2 LAZYDEMO2;

    private LazyDemo2() {

    }

    public static LazyDemo2 getInstance() {
        if (LAZYDEMO2 == null) {
            synchronized (LazyDemo2.class) {
                if (LAZYDEMO2 == null) {
                    LAZYDEMO2 = new LazyDemo2();
                }
            }
        }
        return LAZYDEMO2;
    }

}
