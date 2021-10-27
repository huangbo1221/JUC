package com.bo.function;

import java.util.function.Predicate;

/**
 * @ClassName PredicateDemo
 * @Description 断定型接口，传入一个参数，返回一个boolean值
 * @Author huangbo1221
 * @Date 2021/10/27 23:22
 * @Version 1.0
 */
public class PredicateDemo {
    public static void main(String[] args) {
        test1();
        System.out.println("==========================");
        test2();
        // 上面输出如下
        /**
         * true
         * false
         * ==========================
         * true
         * false
         */
    }

    public static void test1() {
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String str) {
                return str.isEmpty();
            }
        };
        System.out.println(predicate.test(""));
        System.out.println(predicate.test("hb"));
    }

    public static void test2() {
        Predicate<String> predicate = (str) -> {return str.isEmpty();};
        System.out.println(predicate.test(""));
        System.out.println(predicate.test("hb"));
    }
}
