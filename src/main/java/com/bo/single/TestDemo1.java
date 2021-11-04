package com.bo.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

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
//        test8();
        System.out.println("====================test9==================");
//        test9();
        System.out.println("====================test10==================");
        test10();
        System.out.println("====================test11==================");
        test11();
        System.out.println("====================test12==================");
//        test12();
        System.out.println("====================test13==================");
        test13();
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
        // 上面new出来的两个实例是没有经过LazyDemo4的getInstance方法的。而是反射构造的实例，LAZYDEMO4
        // 一直为null
        /**
         * com.bo.single.LazyDemo4@7f31245a
         * com.bo.single.LazyDemo4@6d6f6e28
         */
    }

    /**
     * 上面test8中，通过两个反射来破坏单例模式也是可以被解决的，在构造器里加一个标志位，而不是通过
     * 静态类变量来判断
     * @throws Exception
     */
    public static void test9() throws Exception{
        // 反射来获取实例
        Constructor<LazyDemo5> declaredConstructor = LazyDemo5.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyDemo5 lazyDemo1 = declaredConstructor.newInstance(null);
        System.out.println(lazyDemo1);
        LazyDemo5 lazyDemo2 = declaredConstructor.newInstance(null);
        System.out.println(lazyDemo2);
        // 上面的输出如下：
        /**
         * com.bo.single.LazyDemo5@7f31245a
         * Exception in thread "main" java.lang.reflect.InvocationTargetException
         * 	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
         * 	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
         * 	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
         * 	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
         * 	at com.bo.single.TestDemo1.test9(TestDemo1.java:225)
         * 	at com.bo.single.TestDemo1.main(TestDemo1.java:31)
         * Caused by: java.lang.RuntimeException: 不要试图使用反射破坏异常
         * 	at com.bo.single.LazyDemo5.<init>(LazyDemo5.java:21)
         * 	... 6 more
         */
    }

    /**
     * 上面的test9其实也是有问题的额，仍可通过反射改变标志位点额值，再破坏单例模式
     * @throws Exception
     */
    public static void test10() throws Exception{
        Constructor<LazyDemo5> declaredConstructor = LazyDemo5.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazyDemo5 lazyDemo5 = declaredConstructor.newInstance(null);
        System.out.println(lazyDemo5);

        Field flag = LazyDemo5.class.getDeclaredField("flag");
        flag.setAccessible(true);
        flag.set(lazyDemo5, false);
        LazyDemo5 lazyDemo51 = declaredConstructor.newInstance();
        System.out.println(lazyDemo51);
        // 上面的输出如下：
        /**
         * com.bo.single.LazyDemo5@7f31245a
         * com.bo.single.LazyDemo5@45ee12a7
         */
    }

    /**
     * 从上面的例子中可以看出，不管是懒汉式单例模式怎么变，还是会有安全问题。那么有没有更好的单例模式呢。
     * 答案是肯定的，可以利用枚举类来设计单例模式
     */
    public static void test11() {
        EnumDemo1 instance = EnumDemo1.INSTANCE;
        System.out.println(instance);
        System.out.println(EnumDemo1.getInstance());
        // 上面的输出如下：
        /**
         * INSTANCE
         * INSTANCE
         */

        System.out.println(instance.hashCode());
        System.out.println(EnumDemo1.getInstance().hashCode());
        // 上面的输出如下：
        /**
         * 856419764
         * 856419764
         */
    }

    /**
     * 枚举类的单例模式是否可以被破坏呢？可以采用反射来尝试一下
     */
    public static void test12() throws Exception{
        EnumDemo1 instance = EnumDemo1.INSTANCE;
        System.out.println(instance);
        // 利用反射来生成实例必须得知道它的构造函数，先来看看idea的target中生成的反编译文件，可以看到img.png的内容
        // 只有一个无参构造，我们可以尝试用一下无参构造
        Constructor<EnumDemo1> declaredConstructor = EnumDemo1.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        EnumDemo1 enumDemo1 = declaredConstructor.newInstance(null);
        System.out.println(enumDemo1);
        // 上面的输出如下：可见，利用无参构造来进行反射是不可能的，说明无参构造不存在！
        /**
         * INSTANCE
         * Exception in thread "main" java.lang.NoSuchMethodException: com.bo.single.EnumDemo1.<init>()
         * 	at java.lang.Class.getConstructor0(Class.java:3082)
         * 	at java.lang.Class.getDeclaredConstructor(Class.java:2178)
         * 	at com.bo.single.TestDemo1.test12(TestDemo1.java:303)
         * 	at com.bo.single.TestDemo1.main(TestDemo1.java:38)
         */

        /**
         * 即使用了jdk的反编译工具javap指令，反编译出来的代码如img_1.png所示，仍仍然是无参构造，说明反编译出来的也不对！
         */
    }

    /**
     * 针对test12，可以使用更加专业的反编译工具jad，反编译生成如
     * img_2.png和img_3.png所示，可以EnumDemo1实际是有参构造函数
     */
    public static void test13() throws Exception{

        EnumDemo1 instance = EnumDemo1.INSTANCE;
        System.out.println(instance);

        Constructor<EnumDemo1> declaredConstructor = EnumDemo1.class.getDeclaredConstructor(String.class, int.class);
        declaredConstructor.setAccessible(true);
        EnumDemo1 enumDemo1 = declaredConstructor.newInstance();
        System.out.println(enumDemo1);
        // 上面的输出如下：可见，枚举确实不能破坏单例模式！！！
        /**
         * INSTANCE
         * Exception in thread "main" java.lang.IllegalArgumentException: Cannot reflectively create enum objects
         * 	at java.lang.reflect.Constructor.newInstance(Constructor.java:417)
         * 	at com.bo.single.TestDemo1.test13(TestDemo1.java:335)
         * 	at com.bo.single.TestDemo1.main(TestDemo1.java:40)
         */
    }

}
