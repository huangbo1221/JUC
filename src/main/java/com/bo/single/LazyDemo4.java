package com.bo.single;

/**
 * @ClassName LazyDemo4
 * @Description 解决双重检测锁模式下指令重排造成的多线程不安全问题
 * @Author huangbo1221
 * @Date 2021/11/3 22:51
 * @Version 1.0
 */
public class LazyDemo4 {

    private volatile static LazyDemo4 LAZYDEMO4;

    private LazyDemo4() {
        synchronized (LazyDemo4.class) {
            if (LAZYDEMO4 != null) {
                throw new  RuntimeException("不要试图使用反射破坏异常");
            }
        }
    }

    public static LazyDemo4 getInstance() {
        if (LAZYDEMO4 == null) {
            synchronized (LazyDemo4.class) {
                if (LAZYDEMO4 == null) {
                    LAZYDEMO4 = new LazyDemo4(); // 不是一个原子性的操作
                    // 上面new出一个对象，实际程序执行了多步
                    /**
                     * 1、分配内存空间
                     * 2、执行构造方法，初始化对象
                     * 3、把这个对象指向这个空间
                     *
                     * 在指令重排且是多线程并发场景下，可能线程A执行的顺序是3-->2-->1
                     * 已经执行了3，此时线程B判断LAZYDEMO3 == null的时候就为false，
                     * 从而直接执行return LAZYDEMO3;但是这个对象是未经过初始化的，造成安全问题！！！
                     *
                     * 所以要避免指令重排，而volatile刚好可以禁止指令重排。因此将
                     * private static LazyDemo3 LAZYDEMO3;修改为
                     * private volatile static LazyDemo3 LAZYDEMO3;
                     */
                }
            }
        }
        return LAZYDEMO4;
    }

}
