package com.bo.function;

import java.util.function.Consumer;

/**
 * @ClassName ConsumerDemo
 * @Description 消费型的函数式接口
 * @Author huangbo1221
 * @Date 2021/10/28 8:08
 * @Version 1.0
 */
public class ConsumerDemo {
    public static void main(String[] args) {
        test1();
        test2();
        // 输出如下
        /**
         * huangbo1221
         * liubo1221
         */
    }

    public static void test1() {
        Consumer<String> consumer = new Consumer<String>() {

            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer.accept("huangbo1221");
    }

    public static void test2() {
        Consumer<String> consumer = (Str) -> {
            System.out.println(Str);
        };
        consumer.accept("liubo1221");
    }
}
