package com.bo.volatiletest;

/**
 * @ClassName VolatileDemo2
 * @Description 了解volatile保证可见性和不保证原子性的特征
 * @Author huangbo1221
 * @Date 2021/11/2 8:47
 * @Version 1.0
 */
public class VolatileDemo2 {

    public volatile static int num = 0;

    public static void add() {
        ++num;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }
        // 上面20个线程，每个线程执行1000次add，预期num=20000

        // 判断当前线程是否大于两个（至少会有两个：main线程和gc线程），若大于两个，执行yield
        // 即当前线程让出资源，重新让所有线程竞争CPU资源，可能还会是main线程竞争到
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(num);

        // 若你在add方法上加了同步synchronized，如下：
        /**
         * public synchronized static void add() {
         *         ++num;
         *     }
         */
        // 输出为20000，synchronized肯定能保证原子性

        // 若你在add方法上加了同步volatile，如下：
        /**
         * public volatile static int num = 0;
         */
        // 输出为18272，说明volatile不保证原子性！！
    }
}
