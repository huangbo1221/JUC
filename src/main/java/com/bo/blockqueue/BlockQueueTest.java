package com.bo.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName BlockQueueTest
 * @Description 了解三大方法
 * @Author huangbo1221
 * @Date 2021/10/21 8:20
 * @Version 1.0
 */
public class BlockQueueTest {
    public static void main(String[] args) throws InterruptedException {
        test4();
    }

    /**
     * 演示下会抛出异常的阻塞队列操作方法
     */
    public static void test1() {
        // 初始化队列为3
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add(3));
        // 上面输出如下：
        /**
         * true
         * true
         * true
         */
//        System.out.println(blockingQueue.add(2));
        // 一旦再add一个，就会抛出异常，因为超过初始的大小了！
        /**
         * Exception in thread "main" java.lang.IllegalStateException: Queue full
         * 	at java.util.AbstractQueue.add(AbstractQueue.java:98)
         * 	at java.util.concurrent.ArrayBlockingQueue.add(ArrayBlockingQueue.java:312)
         * 	at com.bo.blockqueue.BlockQueueTest.test1(BlockQueueTest.java:32)
         * 	at com.bo.blockqueue.BlockQueueTest.main(BlockQueueTest.java:14)
         */
        System.out.println("=============================");

        // 判断队列首元素，不改变队列！！只是判断队列而已
        System.out.println(blockingQueue.element());
        //上面一行输出如下:
        /**
         * a
         */
        System.out.println("=============================");
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        // 加了三行输出队列，则控制台输出如下
        /**
         * a
         * b
         * 3
         */
        System.out.println(blockingQueue.element());
        // 前面的代码中的remove操作已经取出了队列的全部元素，此时再去判断队列的首部元素会抛出异常，上面一行输出如下：
        /**
         * Exception in thread "main" java.util.NoSuchElementException
         * 	at java.util.AbstractQueue.element(AbstractQueue.java:136)
         * 	at com.bo.blockqueue.BlockQueueTest.test1(BlockQueueTest.java:59)
         * 	at com.bo.blockqueue.BlockQueueTest.main(BlockQueueTest.java:14)
         */

//        System.out.println(blockingQueue.remove());
        // 一旦多输出一行，则会抛出异常
        /**
         * Exception in thread "main" java.util.NoSuchElementException
         * 	at java.util.AbstractQueue.remove(AbstractQueue.java:117)
         * 	at com.bo.blockqueue.BlockQueueTest.test1(BlockQueueTest.java:52)
         * 	at com.bo.blockqueue.BlockQueueTest.main(BlockQueueTest.java:14)
         */
    }

    /**
     * 演示在阻塞队列中增加或删除元素，但是不抛出异常的API
     */
    public static void test2() {
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer(2));
        // 上面三行也可以向队列中增加元素，但是超过大小后不会抛出异常
        /**
         * true
         * true
         * true
         */

        System.out.println("=====================");
        System.out.println(blockingQueue.peek());
        // 上面一行的peek也可判断队列的首部元素，同样不会改变队列！同时也不会抛出异常！！
        // 输出如下：
        /**
         * a
         */
        System.out.println("=====================");


        System.out.println(blockingQueue.offer("3"));
        // 上面一行的输出如下，可看出超过队列大小后，会返回false，而不是抛出异常
        /**
         * false
         */

        System.out.println("====================================");
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        // 上面三行表示从队列中取出元素，但是不会抛出异常，输出如下：
        /**
         * a
         * b
         * 2
         */
        System.out.println(blockingQueue.poll());
        // 上面一行的输出如下，可看出队列中没有元素了，会输出null，但是不会抛出异常
        /**
         * null
         */

        System.out.println("=====================");
        System.out.println(blockingQueue.peek());
        // 上面的所有poll操作已经取出了队列中的所有元素
        // 上面一行的peek也可判断队列的首部元素，同样不会改变队列！同时也不会抛出异常！！
        // 输出如下：
        /**
         * null
         */
        System.out.println("=====================");

    }

    /**
     * 演示存取队列会一直阻塞等待的API！！
     * @throws InterruptedException
     */
    public static void test3() throws InterruptedException {
        ArrayBlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(3);
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put(3);
//        blockingQueue.put(2);
        // 上面会往队列添加四个元素，但是队列大小只有3.因此程序会永远阻塞等待！！！不会退出。

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        // 上面三行输出如下：
        /**
         * a
         * b
         * 3
         */
        System.out.println(blockingQueue.take());
        // 再加一行take后，输出a\b\3三个值后，程序一直阻塞等待！！！不会退出。
    }

    /**
     * 演示下操作阻塞队列时，可以进行超时等待的API（超过一定时间就不等待了）
     */
    public static void test4() throws InterruptedException {
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer(2));
        System.out.println(blockingQueue.offer(3, 2, TimeUnit.SECONDS));
        // 如test2函数里面演示的，offer有个多参数的重载方法，可实现超时等待！
        // 上面一行表示，往队列里添加 3 时，等待两秒，若插入队列成功，则返回true，否则返回false
        // 最终的输出如下：
        /**
         * true
         * true
         * true
         * false
         */
        System.out.println("===================================");
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        // 同理，上面的poll操作输出如下：
        /**
         * a
         * b
         * 2
         * null
         */
    }
}
