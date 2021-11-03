package com.bo.single;

import java.lang.reflect.Constructor;

/**
 * @ClassName TestDemo1
 * @Description 测试类
 * @Author huangbo1221
 * @Date 2021/11/3 22:18
 * @Version 1.0
 */
public class TestDemo1 {
    public static void main(String[] args) throws Exception {
        System.out.println("====================test1==================");
        test1();
        System.out.println("====================test2==================");
        test2();
        System.out.println("====================test3==================");
//        test3();
        System.out.println("====================test4==================");
//        test4();
        System.out.println("====================test5==================");
//        test5();
        System.out.println("====================test6==================");
        test6();
        System.out.println("====================test7==================");
//        test7();
        System.out.println("====================test8==================");
        test8();
    }

    /**
     * 饿汉式单例模式的缺点：比较耗费内存，启动就开始加载该类！
     */
    public static void test1() {
        HungryDemo1 instance = HungryDemo1.getInstance();
        System.out.println(instance);
        System.out.println(HungryDemo1.getInstance());
        // 上面的方法输出如下，可见，两个实例实际是同一个
        /**
         * com.bo.single.HungryDemo1@74a14482
         * com.bo.single.HungryDemo1@74a14482
         */
    }

    /**
     * 懒汉式单例模式也可以达到同样的目的，用的时候再new一个对象出来，但是LazyDemo2的写法
     * 在多线程下不安全，见test3方法
     */
    public static void test2() {
        LazyDemo1 instance = LazyDemo1.getInstance();
        System.out.println(instance);
        System.out.println(LazyDemo1.getInstance());
        // 上面的方法输出如下，可见，两个实例实际是同一个
        /**
         * com.bo.single.LazyDemo1@1540e19d
         * com.bo.single.LazyDemo1@1540e19d
         */
    }

    /**
     * 懒汉式单例模式多线程并发问题
     */
    public static void test3() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(LazyDemo1.getInstance());
            }).start();
        }
        // 上面的输出可能的情况为：可以看到10个线程中，有的实例地址是不一样的，这违反了单例模式！
        // 原因：多线程场景下判断LAZYDEMO1==null的时候，可能多个线程同时拿到的LAZYDEMO1为null，
        // 则都回去new一个实例出来！
        /**
         * com.bo.single.LazyDemo1@6f10ed20
         * com.bo.single.LazyDemo1@42be98f3
         * com.bo.single.LazyDemo1@42be98f3
         * com.bo.single.LazyDemo1@42be98f3
         * com.bo.single.LazyDemo1@3a047100
         * com.bo.single.LazyDemo1@42be98f3
         * com.bo.single.LazyDemo1@42be98f3
         * com.bo.single.LazyDemo1@42be98f3
         * com.bo.single.LazyDemo1@3dabeea
         * com.bo.single.LazyDemo1@42be98f3
         */
    }

    /**
     * test4采用了双重检测锁的懒汉式单例模式，可以有效解决多线程并发时懒汉式单例模式的
     * 不安全问题。
     * 缺点：
     * 1、每一个线程都得等待锁，造成效率低下的问题
     * 2、指令重排场景下，仍会有不安全问题。请看LazyDemo3的例子
     */
    public static void test4() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(LazyDemo2.getInstance());
            }).start();
        }
        // 上面的输出如下：只有一个实例！！
        /**
         * com.bo.single.LazyDemo2@8cd564f
         * com.bo.single.LazyDemo2@8cd564f
         * com.bo.single.LazyDemo2@8cd564f
         * com.bo.single.LazyDemo2@8cd564f
         * com.bo.single.LazyDemo2@8cd564f
         * com.bo.single.LazyDemo2@8cd564f
         * com.bo.single.LazyDemo2@8cd564f
         * com.bo.single.LazyDemo2@8cd564f
         * com.bo.single.LazyDemo2@8cd564f
         * com.bo.single.LazyDemo2@8cd564f
         */
    }

    /**
     * 静态内部类实现的单例模式，但是仍然为不安全的
     */
    public static void test5() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(SingleInterClass.getInstance());
            }).start();
        }
        // 上面的输出为：
        /**
         * com.bo.single.SingleInterClass@30600685
         * com.bo.single.SingleInterClass@30600685
         * com.bo.single.SingleInterClass@30600685
         * com.bo.single.SingleInterClass@30600685
         * com.bo.single.SingleInterClass@30600685
         * com.bo.single.SingleInterClass@30600685
         * com.bo.single.SingleInterClass@30600685
         * com.bo.single.SingleInterClass@30600685
         * com.bo.single.SingleInterClass@30600685
         * com.bo.single.SingleInterClass@30600685
         */
    }

    /**
     * LazyDemo3的双重检测锁模式的单例模式（加了volatile）就一定是安全的吗？
     *
     * 答案是否定的，因为可以通过反射来破坏这种单例模式
     */
    public static void test6() throws Exception {
        LazyDemo3 instance = LazyDemo3.getInstance();
        System.out.println(instance);

        // 反射来获取实例
        Constructor<LazyDemo3> declaredConstructor = LazyDemo3.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyDemo3 lazyDemo3 = declaredConstructor.newInstance(null);
        System.out.println(lazyDemo3);
        // 上面的输出如下：两个实例的hashcode不一样，为不同实例，破坏了单例模式
        /**
         * com.bo.single.LazyDemo3@677327b6
         * com.bo.single.LazyDemo3@14ae5a5
         */
    }

    /**
     * LazyDemo3的双重检测锁模式的单例模式（加了volatile）是可以通过反射来破坏的，那么有没有什么
     * 办法可以解决呢？
     *
     * 答案是肯定的，见LazyDemo4，在构造方法里再加一层判断
     */
    public static void test7() throws Exception {
        LazyDemo4 instance = LazyDemo4.getInstance();
        System.out.println(instance);

        // 反射来获取实例
        Constructor<LazyDemo4> declaredConstructor = LazyDemo4.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyDemo4 lazyDemo4 = declaredConstructor.newInstance(null);
        System.out.println(lazyDemo4);
        // 上面的输出如下：
        /**
         * com.bo.single.LazyDemo4@7f31245a
         * Exception in thread "main" java.lang.reflect.InvocationTargetException
         * 	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
         * 	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
         * 	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
         * 	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
         * 	at com.bo.single.TestDemo1.test7(TestDemo1.java:171)
         * 	at com.bo.single.TestDemo1.main(TestDemo1.java:27)
         * Caused by: java.lang.RuntimeException: 不要试图使用反射破坏异常
         * 	at com.bo.single.LazyDemo4.<init>(LazyDemo4.java:17)
         * 	... 6 more
         */
    }

    /**
     * test7的解决方案仍然是由问题的，因为最初是直接new出来的对象，然后再执行的反射
     * 如果多次用反射呢？？？
     */
    public static void test8() throws Exception {
        // 反射来获取实例
        Constructor<LazyDemo4> declaredConstructor = LazyDemo4.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyDemo4 lazyDemo1 = declaredConstructor.newInstance(null);
        System.out.println(lazyDemo1);
        LazyDemo4 lazyDemo2 = declaredConstructor.newInstance(null);
        System.out.println(lazyDemo2);
        // 上面的输出如下：可见，出现了两个不同的实例，破坏了单例模式
        // 上面new出来的两个实例是没有经过LazyDemo4的getInstance方法的。而是反射构造的实例
        /**
         * com.bo.single.LazyDemo4@7f31245a
         * com.bo.single.LazyDemo4@6d6f6e28
         */
    }

}
